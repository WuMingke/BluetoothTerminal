/*
package com.whosssmade.bluetoothterminal.ui.services;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.whosssmade.bluetoothterminal.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BtnThread extends Thread {

    private BluetoothSocket mSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private Handler handler;
    private String tag;
    private Bundle bundle;

    @Override
    public void run() {
        super.run();
        if (mSocket != null) {
            try {
                while (true) {
                    if (mInputStream != null) {
                        byte[] bytes = Utils.readInputStream(mInputStream);
                        //Log.i("wmk", "----------" + Utils.bytesToHexString(byt
                        final String s = Utils.bytesToHexString(bytes);
                        final String[] s1 = s.split(" ");
                        if (s1.length > 4) {
                            Message msg = handler.obtainMessage();
                            msg.obj = tag;
                            handler.sendMessage(msg);

                        } else {
                            Message msg = handler.obtainMessage();
                            msg.obj = "返回数据有误";
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public BtnThread(BluetoothSocket socket,Object tagObj,byte[] commandBytes,Handler handler) {
        this.mSocket = socket;
        tag = (String) tagObj;
        try {
            mInputStream = mSocket.getInputStream();
            mOutputStream = mSocket.getOutputStream();
            setCommandBytes(Utils.calcCrc16(commandBytes));
            this.handler = handler;
            bundle = new Bundle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCommandBytes(byte[] commandBytes) {
        try {
            if (mOutputStream != null) {
                mOutputStream.write(commandBytes);
                mOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void makeStop() {
        try {
            if (mInputStream != null) {
                mInputStream.close();
            }
            if (mOutputStream != null) {
                mOutputStream.close();
            }
            if (mSocket != null) {
                mSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
