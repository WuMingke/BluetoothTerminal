package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseActivity;
import com.whosssmade.bluetoothterminal.business.contract.MainContract;
import com.whosssmade.bluetoothterminal.business.presenter.MainPresenter;
import com.whosssmade.bluetoothterminal.business.presenter.SearchPresenter;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.OnClick;

import static java.lang.Thread.sleep;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vertical)
    TextView vertical;

    @BindView(R.id.horizontal)
    TextView horizontal;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private BluetoothDevice deviceBean;
    private BluetoothSocket socket;
    private byte[] open = new byte[]{0x55, 0x01, 0x01, 0x02, 0x00, 0x00, 0x00, (byte) 0x59};
    private byte[] close = new byte[]{0x55, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0x58};

    private byte[] handOpen = new byte[]{0x01, 0x05, 0x00, 0x38, (byte) 0xFF, 0x00, 0x0D, (byte) 0xF7};
    private byte[] handClose = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00, 0x5C, 0x37};

    private OutputStream outputStream = null;
    private InputStream inputStream;

    private boolean read = true;

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

        deviceBean = getIntent().getParcelableExtra(Constants.DEVICE_BEAN);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = deviceBean.createInsecureRfcommSocketToServiceRecord(UUID.fromString(Constants.UUID));
                    while (!socket.isConnected()) {
                        socket.connect();
                    }
                    if (socket != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("已建立蓝牙连接");
                            }
                        });

                        outputStream = socket.getOutputStream();
                        inputStream = socket.getInputStream();
                    }
                    while (read) {
                        byte[] bytes = readInputStream(inputStream);
                        Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                        final String s = Utils.bytesToHexString(bytes);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //  order.append(s + "\n");
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();





    }


    @OnClick({R.id.back, R.id.verticalBtn, R.id.horizontalBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verticalBtn://上下回原点
                break;
            case R.id.horizontalBtn://水平回原点
                break;

            case R.id.back:
                finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        read = false;
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
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] readInputStream(InputStream inputStream) throws IOException {
        int len = 0;
        while (len == 0) {
            if (inputStream.available() <= 0) {
                continue;
            } else {
                try {
                    sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            len = inputStream.available();
        }
        byte[] bytes = new byte[len];
        inputStream.read(bytes);
        return bytes;
    }
}
