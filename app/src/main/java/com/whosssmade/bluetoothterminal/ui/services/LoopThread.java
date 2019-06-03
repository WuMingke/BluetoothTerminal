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


public class LoopThread extends Thread {

    private BluetoothSocket mSocket;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private Handler handler;
    private String tag;
    private Bundle bundle;
    private boolean needRead;
    private byte[] commandBytes;

    public void setNeedRead(boolean needRead) {
        this.needRead = needRead;
    }


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
                commandBytes = b;
                mOutputStream.write(b);
                mOutputStream.flush();
                Log.i("wmk", "---最终发送的指令---" + Arrays.toString(b));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public LoopThread(BluetoothSocket socket, Object tag, Handler handler, boolean needRead) {
        this.mSocket = socket;
        this.needRead = needRead;
        try {
            mInputStream = mSocket.getInputStream();
            mOutputStream = mSocket.getOutputStream();
            this.tag = (String) tag;
            // setCommandBytes(commandBytes);
            this.handler = handler;
            bundle = new Bundle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        if (mSocket != null) {
            try {
                while (needRead) {
                    if (mInputStream != null) {
                        byte[] bytes = Utils.readInputStream(mInputStream);
                        if (bytes.equals(commandBytes)) continue;
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

        }
    }
}
*/
