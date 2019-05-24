package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseActivity;
import com.whosssmade.bluetoothterminal.business.contract.MainContract;
import com.whosssmade.bluetoothterminal.business.presenter.MainPresenter;
import com.whosssmade.bluetoothterminal.business.presenter.SearchPresenter;
import com.whosssmade.bluetoothterminal.model.bean.DeviceBean;
import com.whosssmade.bluetoothterminal.model.constant.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private DeviceBean deviceBean;
    private BluetoothSocket socket;
    private byte[] open = new byte[]{0x55, 0x01, 0x01, 0x02, 0x00, 0x00, 0x00, (byte) 0x59};
    private byte[] close = new byte[]{0x55, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0x58};

    private OutputStream outputStream = null;

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

        deviceBean = (DeviceBean) getIntent().getBundleExtra(Constants.DEVICE_BEAN).getParcelable(Constants.DEVICE_BEAN);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice remoteDevice = defaultAdapter.getRemoteDevice(deviceBean.getMac());
        defaultAdapter.cancelDiscovery();
        try {
            socket = remoteDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString(Constants.UUID));
            socket.connect();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.open, R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
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
