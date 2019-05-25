package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseActivity;
import com.whosssmade.bluetoothterminal.business.contract.MainContract;
import com.whosssmade.bluetoothterminal.business.presenter.MainPresenter;
import com.whosssmade.bluetoothterminal.business.presenter.SearchPresenter;
import com.whosssmade.bluetoothterminal.model.bean.DeviceBean;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.order)
    TextView order;

    @BindView(R.id.test)
    TextView test;

    private DeviceBean deviceBean;
    private BluetoothSocket socket;
    private byte[] open = new byte[]{0x55, 0x01, 0x01, 0x02, 0x00, 0x00, 0x00, (byte) 0x59};
    private byte[] close = new byte[]{0x55, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0x58};

    private byte[] handOpen = new byte[]{0x01, 0x05, 0x00, 0x38, (byte) 0xFF, 0x00, 0x0D, (byte) 0xF7};
    private byte[] handClose = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00, 0x5C, 0x37};

    private OutputStream outputStream = null;
    private InputStream inputStream;

    private int readBufferPosition;
    private boolean stopWorker;
    private byte[] readBuffer;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject(Bundle bundle) {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {

        byte[] handOpen = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00};

        byte[] bytes = Utils.calcCrc16(handOpen);

        String s = Utils.bytesToHexString(bytes);
        test.setText(s);


        deviceBean = (DeviceBean) getIntent().getBundleExtra(Constants.DEVICE_BEAN).getParcelable(Constants.DEVICE_BEAN);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(deviceBean.getMac());
        defaultAdapter.cancelDiscovery();
        try {
            socket = remoteDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(Constants.UUID));
            socket.connect();
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            beginListenForData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character
        stopWorker = false;
        readBuffer = new byte[1024];
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = inputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            order.append(data+"\n");
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        stopWorker = true;
                    }
                }
            }
        }).start();

    }

    @OnClick({R.id.back, R.id.open, R.id.close, R.id.handOpen, R.id.handClose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.handOpen:
                try {
                    if (outputStream != null) {
                        outputStream.write(handOpen);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.handClose:
                try {
                    if (outputStream != null) {
                        outputStream.write(handClose);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.open:
                try {
                    if (outputStream != null) {
                        outputStream.write(open);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.close:
                try {
                    if (outputStream != null) {
                        outputStream.write(close);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopWorker = true;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*    public void openFirst(View view) {
        try {
            outputStream.write(new byte[]{(byte) 0XA1});
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFirst(View view) {
        try {
            outputStream.write(new byte[]{(byte) 0XB1});
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSecond(View view) {
        try {
            outputStream.write(new byte[]{(byte) 0XA2});
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSecond(View view) {
        try {
            outputStream.write(new byte[]{(byte) 0XB2});
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
