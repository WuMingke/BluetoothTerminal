/*
package com.whosssmade.bluetoothterminal.ui.services;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.whosssmade.bluetoothterminal.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class BtnThread extends Thread {

    private BluetoothSocket mSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;


    public void makeStop() {
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

    public void setCommandBytes(byte[] commandBytes) {
        try {
            if (mOutputStream != null) {
                byte[] b = Utils.calcCrc16(commandBytes);
                mOutputStream.write(b);
                mOutputStream.flush();
                Log.i("wmk", "--BtnThread最终发送的指令---" + Arrays.toString(b));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BtnThread(BluetoothSocket socket) {
        this.mSocket = socket;
        try {
            mInputStream = mSocket.getInputStream();
            mOutputStream = mSocket.getOutputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
       */
/* if (mSocket != null) {
            try {
                Log.i("wmk", Thread.currentThread().getName()+"--needRead--" + needRead);
                while (needRead) {
                    if (mInputStream != null) {
                        byte[] bytes = Utils.readInputStream(mInputStream);
                        //Log.i("wmk", "----------" + Utils.bytesToHexString(byt
                        //final String s = Utils.bytesToHexString(bytes);
                        String string = Arrays.toString(bytes);
                        final String[] s1 = string.split(",");
                        if (s1.length > 4) {
                            Message msg = handler.obtainMessage();
                            msg.obj = tag;
                            msg.what = 1;
                            bundle.putString("data", s1[3]);
                            Log.i("wmk", "----thread-all-data----" + string);
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                            //sendCommand(Utils.calcCrc16(d5002s));//循环

                        } else {
                            Message msg = handler.obtainMessage();
                            msg.obj = "返回数据有误";
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*//*

    }
}
*/
