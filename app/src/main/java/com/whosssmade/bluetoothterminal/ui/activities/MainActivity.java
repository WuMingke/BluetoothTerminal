package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.LoaderTestCase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseActivity;
import com.whosssmade.bluetoothterminal.business.contract.MainContract;
import com.whosssmade.bluetoothterminal.business.presenter.MainPresenter;
import com.whosssmade.bluetoothterminal.business.presenter.SearchPresenter;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;
import com.whosssmade.bluetoothterminal.ui.adapters.ItemFragmentPagerAdapter;
import com.whosssmade.bluetoothterminal.ui.dialogs.LoadingDialog;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment1;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment2;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment3;
import com.whosssmade.bluetoothterminal.ui.services.LoopThread;
import com.whosssmade.bluetoothterminal.utils.Utils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static java.lang.Thread.sleep;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, ViewPager.OnPageChangeListener {

    @BindView(R.id.vertical)
    TextView vertical;

    @BindView(R.id.horizontal)
    TextView horizontal;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.item1)
    TextView item1;

    @BindView(R.id.item2)
    TextView item2;

    @BindView(R.id.item3)
    TextView item3;

    @BindView(R.id.verticalBtn)
    Button verticalBtn;

    @BindView(R.id.horizontalBtn)
    Button horizontalBtn;

    private BluetoothDevice deviceBean;
    private BluetoothSocket socket;
    private byte[] open = new byte[]{0x55, 0x01, 0x01, 0x02, 0x00, 0x00, 0x00, 0x59};
    private byte[] close = new byte[]{0x55, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0x58};

    private byte[] handOpen = new byte[]{0x01, 0x05, 0x00, 0x38, (byte) 0xFF, 0x00, 0x0D, (byte) 0xF7};
    private byte[] handClose = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00, 0x5C, 0x37};

    private OutputStream outputStream, outputStream1, outputStream2, outputStream3;
    private InputStream inputStream, inputStream1, inputStream2, inputStream3;
    private static final byte[] readPreByte = new byte[]{0x01, 0x01};
    private static final byte[] readBackByte = new byte[]{0x00, 0x01};
    private static final byte[] readBackByte2 = new byte[]{0x00, (byte) 0x98};
    private static final byte[] writePreByte = new byte[]{0x01, 0x05};
    private static final byte[] writeBackByte = new byte[]{(byte) 0xFF, 0x00};
    private byte[] verticalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8a, 0x00, 0x01};//上下脉冲
    private byte[] horizontalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8c, 0x00, 0x01};//横向脉冲

    private static final byte[] readRegisterPreByte = new byte[]{0x01, 0x03};
    //private static final byte[] readRegisterBackByte = new byte[]{0x00, 0x01};
    //private static final byte[] readRegisterBackByte2 = new byte[]{0x00, 0x14};

    private ItemFragmentPagerAdapter pagerAdapter;


    private LoopThread loopThread;//上下脉冲量
    private LoopThread loopThread2;//横向脉冲量
    private LoopThread loopThread1;


    private MyHandler myHandler;
    // private BtnHandler btnHandler;

    private byte[] d5004s;
    private byte[] d5002s;
    private byte[] m100s1;
    private byte[] m101s;
    private LoopThread loopThread3;
    private byte[] m50s;
    private LoopThread loopThread4;
    private LoadingDialog loadingDialog;

   /* private class BtnHandler extends Handler {
        private final WeakReference<MainActivity> mainActivityWeakReference;

        public BtnHandler(MainActivity activity) {
            mainActivityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            MainActivity mainActivity = mainActivityWeakReference.get();
            if (mainActivity != null && msg.what != 0) {
                switch (msg.what) {
                    case 1:
                        switch ((String)msg.obj){
                            case "":
                        }
                        Utils.setTextClickDown(textView);
                        break;
                    case 2:
                        Utils.setTextClickUp(textView);
                        break;
                    case 3:
                        showToast((String) msg.obj);
                        break;
                }
            }
        }
    }*/

    private ItemFragment1 itemFragment1;
    private ItemFragment2 itemFragment2;
    private ItemFragment3 itemFragment3;
    private byte[] m51s;
    private LoopThread loopThread5;
    private byte[] m52s;
    private LoopThread loopThread6;
    private byte[] m53s;
    private LoopThread loopThread7;
    private byte[] m54s;
    private LoopThread loopThread8;
    private byte[] m55s;
    private LoopThread loopThread9;
    private byte[] m56s;
    private LoopThread loopThread10;
    private byte[] m57s;
    private LoopThread loopThread11;
    private byte[] m100s2;
    private LoopThread loopThread12;

    private class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            MainActivity mainActivity = mActivity.get();
            if (mainActivity != null) {
                switch (msg.what) {
                    case 1://成功返回数据
                        String s = (String) msg.obj;
                        Log.i("wmk", "--1--activity-s----" + s);
                        String data = msg.getData().getString("data");
                        Log.i("wmk", "--1--activity-data----" + data);
                        switch (s) {
                            case "D5002":
                                vertical.setText(data);
                                loopThread.setCommandBytes(Utils.calcCrc16(d5002s));
                                break;
                            case "D5004":
                                horizontal.setText(data);
                                loopThread2.setCommandBytes(Utils.calcCrc16(d5004s));
                                break;
                            case "M100":
                                Utils.setBtnState(data, verticalBtn);
                                break;
                            case "M101":
                                Utils.setBtnState(data, horizontalBtn);
                                break;
                            case "M50":
                                Utils.setBtnState(data, itemFragment1.getUp());
                                Log.i("wmk", "--2--activity-data----" + data);
                                break;
                            case "M51":
                                Utils.setBtnState(data, itemFragment1.getDown());
                                break;
                            case "M52":
                                Utils.setBtnState(data, itemFragment1.getForward());
                                break;
                            case "M53":
                                Utils.setBtnState(data, itemFragment1.getBack());
                                break;
                            case "M54":
                                Utils.setBtnState(data, itemFragment1.getRotation1());
                                break;
                            case "M55":
                                Utils.setBtnState(data, itemFragment1.getRotation2());
                                break;
                            case "M56":
                                Utils.setBtnState(data, itemFragment1.getOpen());
                                break;
                            case "M57":
                                Utils.setBtnState(data, itemFragment1.getClose());
                                break;
                        }
                        break;
                    case 2://返回数据失败
                        showToast((String) msg.obj);
                        break;
                }
            }
        }
    }


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

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        List<Fragment> fragments = new ArrayList<>();
        itemFragment1 = new ItemFragment1();
        fragments.add(itemFragment1);
        itemFragment2 = new ItemFragment2();
        fragments.add(itemFragment2);
        itemFragment3 = new ItemFragment3();
        fragments.add(itemFragment3);
        pagerAdapter = new ItemFragmentPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        viewpager.setOnPageChangeListener(this);
        viewpager.setCurrentItem(0);
        item1.setTextColor(Color.WHITE);
        item1.setBackgroundColor(getResources().getColor(R.color.tips_blue));
        item2.setTextColor(Color.BLACK);
        item2.setBackgroundColor(Color.WHITE);
        item3.setTextColor(Color.BLACK);
        item3.setBackgroundColor(Color.WHITE);

        myHandler = new MyHandler(this);
        // btnHandler = new BtnHandler(this);

        deviceBean = getIntent().getParcelableExtra(Constants.DEVICE_BEAN);

        /**
         * 5个线程
         * <p>
         * 1个线程用来处理socket连接
         * 1个线程用来循环读上下/横向脉冲
         * 2个线程用来读一次相应页面的数据
         * 1个线程用来处理按钮
         */
        new Thread(new Runnable() {//处理socket连接
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
                                loadingDialog.dismiss();
                                showToast("已建立蓝牙连接");
                            }
                        });
                        //循环读上下/横向脉冲
                        d5002s = Utils.getCommandBytes("D5002", readRegisterPreByte, readBackByte);
                        loopThread = new LoopThread(socket, vertical.getTag(), d5002s, myHandler);
                        loopThread.setNeedRead(true);
                        loopThread.start();

                        //横向脉冲量
                        d5004s = Utils.getCommandBytes("D5004", readRegisterPreByte, readBackByte);
                        loopThread2 = new LoopThread(socket, horizontal.getTag(), d5004s, myHandler);
                        loopThread2.setNeedRead(true);
                        loopThread2.start();

                        //查询按钮状态
                        //上下回原点
                        m100s1 = Utils.getCommandBytes("M100", readPreByte, readBackByte);
                        loopThread1 = new LoopThread(socket, verticalBtn.getTag(), m100s1, myHandler);
                        loopThread1.setNeedRead(true);
                        loopThread1.start();
                        //横向回原点
                        m101s = Utils.getCommandBytes("M101", readPreByte, readBackByte);
                        loopThread3 = new LoopThread(socket, horizontalBtn.getTag(), m101s, myHandler);
                        loopThread3.setNeedRead(true);
                        loopThread3.start();

                        /**
                         * 上移M50
                         * 下移M51
                         * 前进M52
                         * 后退M53
                         * 出口面M54
                         * 打印机面M55
                         * 张开M56
                         * 夹紧M57
                         */

                        //上移
                        m50s = Utils.getCommandBytes("M50", readPreByte, readBackByte);
                        loopThread4 = new LoopThread(socket, itemFragment1.getUp().getTag(), m50s, myHandler);
                        loopThread4.setNeedRead(true);
                        loopThread4.start();
                        //下移
                        m51s = Utils.getCommandBytes("M51", readPreByte, readBackByte);
                        loopThread5 = new LoopThread(socket, itemFragment1.getDown().getTag(), m51s, myHandler);
                        loopThread5.setNeedRead(true);
                        loopThread5.start();
                        //前进
                        m52s = Utils.getCommandBytes("M52", readPreByte, readBackByte);
                        loopThread6 = new LoopThread(socket, itemFragment1.getForward().getTag(), m52s, myHandler);
                        loopThread6.setNeedRead(true);
                        loopThread6.start();
                        //后退
                        m53s = Utils.getCommandBytes("M53", readPreByte, readBackByte);
                        loopThread7 = new LoopThread(socket, itemFragment1.getBack().getTag(), m53s, myHandler);
                        loopThread7.setNeedRead(true);
                        loopThread7.start();
                        //出口面
                        m54s = Utils.getCommandBytes("M54", readPreByte, readBackByte);
                        loopThread8 = new LoopThread(socket, itemFragment1.getRotation1().getTag(), m54s, myHandler);
                        loopThread8.setNeedRead(true);
                        loopThread8.start();
                        //打印机面
                        m55s = Utils.getCommandBytes("M55", readPreByte, readBackByte);
                        loopThread9 = new LoopThread(socket, itemFragment1.getRotation2().getTag(), m55s, myHandler);
                        loopThread9.setNeedRead(true);
                        loopThread9.start();
                        //张开
                        m56s = Utils.getCommandBytes("M56", readPreByte, readBackByte);
                        loopThread10 = new LoopThread(socket, itemFragment1.getOpen().getTag(), m56s, myHandler);
                        loopThread10.setNeedRead(true);
                        loopThread10.start();
                        //夹紧
                        m57s = Utils.getCommandBytes("M57", readPreByte, readBackByte);
                        loopThread11 = new LoopThread(socket, itemFragment1.getClose().getTag(), m57s, myHandler);
                        loopThread11.setNeedRead(true);
                        loopThread11.start();






               /*         new Thread(new Runnable() {//循环读上下/横向脉冲
                            @Override
                            public void run() {
                                if (socket != null) {
                                    try {
                                        outputStream = socket.getOutputStream();
                                        inputStream = socket.getInputStream();

                                        byte[] d5002s = Utils.getCommandBytes("D5002", readRegisterPreByte, readRegisterBackByte);
                                        if (outputStream != null) {
                                            outputStream.write(Utils.calcCrc16(d5002s));
                                            outputStream.flush();
                                        }
                                        //sendCommand(Utils.calcCrc16(d5002s));

                                        while (true) {
                                            byte[] bytes = readInputStream(inputStream);
                                            //Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                                            final String s = Utils.bytesToHexString(bytes);
                                            final String[] s1 = s.split(" ");
                                            if (s1.length > 6) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        vertical.setText(s1[3]);//数据的第一个
                                                        horizontal.setText(s1[5]);//数据的最后一个
                                                    }
                                                });
                                                //sendCommand(Utils.calcCrc16(d5002s));//循环
                                                if (outputStream != null) {
                                                    outputStream.write(Utils.calcCrc16(d5002s));
                                                    outputStream.flush();
                                                }
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showToast("返回数据有误");
                                                    }
                                                });
                                            }

                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();*/

                       /* new Thread(new Runnable() {//第一页的数据
                            @Override
                            public void run() {
                                if (socket != null) {
                                    try {
                                        outputStream1 = socket.getOutputStream();
                                        inputStream1 = socket.getInputStream();
                                        byte[] d4100s = Utils.getCommandBytes("D4100", readRegisterPreByte, readRegisterBackByte2);
                                        if (outputStream1 != null) {
                                            outputStream1.write(Utils.calcCrc16(d4100s));
                                            outputStream1.flush();
                                        }
                                        // sendCommand(Utils.calcCrc16(d4100s));

                                        while (true) {
                                            byte[] bytes = readInputStream(inputStream1);
                                            //Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                                            final String s = Utils.bytesToHexString(bytes);
                                            final String[] s1 = s.split(" ");
                                            if (s1.length > 6) {
                                                EventBusMessage<String[]> message = new EventBusMessage<>();
                                                message.setT(s1);
                                                EventBus.getDefault().post(message,Constants.DATA_FRAGMENT_1);
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showToast("返回数据有误");
                                                    }
                                                });
                                            }
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();*/

                       /* new Thread(new Runnable() {//第二页的数据
                            @Override
                            public void run() {
                                if (socket != null) {
                                    try {
                                        outputStream2 = socket.getOutputStream();
                                        inputStream2 = socket.getInputStream();
                                        byte[] d4100s = Utils.getCommandBytes("D4100", readRegisterPreByte, readRegisterBackByte2);
                                        if (outputStream2 != null) {
                                            outputStream2.write(Utils.calcCrc16(d4100s));
                                            outputStream2.flush();
                                        }
                                        // sendCommand(Utils.calcCrc16(d4100s));

                                        while (true) {
                                            byte[] bytes = readInputStream(inputStream2);
                                            //Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                                            final String s = Utils.bytesToHexString(bytes);
                                            final String[] s1 = s.split(" ");
                                            if (s1.length > 6) {
                                                EventBusMessage<String[]> message = new EventBusMessage<>();
                                                message.setT(s1);
                                                EventBus.getDefault().post(message, Constants.DATA_FRAGMENT_2);
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showToast("返回数据有误");
                                                    }
                                                });
                                            }
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();*/
                        /**
                         * 上移M50
                         * 下移M51
                         * 前进M52
                         * 后退M53
                         * 出口面M54
                         * 打印机面M55
                         * 张开M56
                         * 夹紧M57
                         */

                        /**
                         * 上下旋转位M102
                         * 上下打印机位M103
                         * 上下烤箱位M104
                         * 上下出口位M105
                         * 上下取料位M106
                         * 横向仓料位M107
                         * 横向打印机位M108
                         * 横向烤箱位M109
                         * 横向出口位M110
                         * 下放脉冲量
                         * 打印机伸出M116
                         * 打印机缩回M117
                         * 烤箱伸出M114
                         * 烤箱缩回M115
                         * 推杆伸出M112
                         * 推杆缩回M113
                         * 出货口开M201
                         * 出货口关M200
                         * 皮带出货M111
                         */

                        /**
                         * 打印机电源M119
                         * 打印机开关M118
                         * 烤箱发热丝M121
                         * 烤箱风扇M112
                         * 机械复位M10
                         * 按钮复位M130
                         */

                       /* new Thread(new Runnable() {//按钮
                            @Override
                            public void run() {
                                if (socket != null) {
                                    try {
                                        inputStream3 = socket.getInputStream();
                                        outputStream3 = socket.getOutputStream();

                                        if (outputStream3 != null) {
                                            outputStream3.write(m50s);
                                            outputStream3.flush();
                                        }
                                        while (true) {
                                            byte[] bytes = readInputStream(inputStream3);
                                            //Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                                            final String s = Utils.bytesToHexString(bytes);
                                            final String[] s1 = s.split(" ");
                                            if (s1.length > 6) {
                                                EventBusMessage<String[]> message = new EventBusMessage<>();
                                                message.setT(s1);
                                                EventBus.getDefault().post(message, Constants.DATA_BUTTON);
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        showToast("返回数据有误");
                                                    }
                                                });
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();*/


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.getTextView().setText("连接发生错误，请重启APP");
                        }
                    });
                }
            }
        }).start();





        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket != null) {

                        outputStream = socket.getOutputStream();
                        inputStream = socket.getInputStream();
                    }
                    while (true) {
                        byte[] bytes = readInputStream(inputStream);
                        Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                        final String s = Utils.bytesToHexString(bytes);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                vertical.setText(s);
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/


        /*readPulseServiceIntent = new Intent(this, ReadPulseService.class);
        startService(readPulseServiceIntent);*/
    }


    @OnClick({R.id.back, R.id.verticalBtn, R.id.horizontalBtn, R.id.item1, R.id.item2, R.id.item3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verticalBtn://上下回原点
                verticalBtn.setBackgroundResource(R.drawable.btn_click_down);
                if (m100s2 == null) {
                    m100s2 = Utils.getCommandBytes("M100", writePreByte, writeBackByte);
                }
                loopThread12 = new LoopThread(socket, verticalBtn.getTag(), m100s2, myHandler);
                loopThread12.setNeedRead(false);
                loopThread12.start();

                loopThread1.setCommandBytes(m100s1);// 检查

                break;
            case R.id.horizontalBtn://横向回原点

                break;
            case R.id.back:
                finish();
                break;
            case R.id.item1:
                viewpager.setCurrentItem(0);
                break;
            case R.id.item2:
                viewpager.setCurrentItem(1);
                break;
            case R.id.item3:
                viewpager.setCurrentItem(2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // read = false;
        loopThread.makeStop();
        loopThread1.makeStop();
        loopThread2.makeStop();
        loopThread3.makeStop();
        loopThread4.makeStop();
        loopThread5.makeStop();
        loopThread6.makeStop();
        loopThread7.makeStop();
        loopThread8.makeStop();
        loopThread9.makeStop();
        loopThread10.makeStop();
        loopThread11.makeStop();

        try {
            if (socket != null) {
                socket.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream1 != null) {
                outputStream1.close();
            }
            if (inputStream1 != null) {
                inputStream1.close();
            }
            if (outputStream2 != null) {
                outputStream2.close();
            }
            if (inputStream2 != null) {
                inputStream2.close();
            }
            if (outputStream3 != null) {
                outputStream3.close();
            }
            if (inputStream3 != null) {
                inputStream3.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // stopService(readPulseServiceIntent);
    }

/*
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
*/

/*    private void sendCommand(byte[] bytes) {
        try {
            if (outputStream != null) {
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewpager.setCurrentItem(position);
        if (position == 0) {
            item1.setTextColor(Color.WHITE);
            item1.setBackgroundColor(getResources().getColor(R.color.tips_blue));
            item2.setTextColor(Color.BLACK);
            item2.setBackgroundColor(Color.WHITE);
            item3.setTextColor(Color.BLACK);
            item3.setBackgroundColor(Color.WHITE);
        }
        if (position == 1) {
            item2.setTextColor(Color.WHITE);
            item2.setBackgroundColor(getResources().getColor(R.color.tips_blue));
            item1.setTextColor(Color.BLACK);
            item1.setBackgroundColor(Color.WHITE);
            item3.setTextColor(Color.BLACK);
            item3.setBackgroundColor(Color.WHITE);
        }
        if (position == 2) {
            item3.setTextColor(Color.WHITE);
            item3.setBackgroundColor(getResources().getColor(R.color.tips_blue));
            item2.setTextColor(Color.BLACK);
            item2.setBackgroundColor(Color.WHITE);
            item1.setTextColor(Color.BLACK);
            item1.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
