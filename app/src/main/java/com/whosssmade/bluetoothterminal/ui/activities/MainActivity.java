package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.whosssmade.bluetoothterminal.utils.Utils;

import org.simple.eventbus.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private OutputStream outputStream, outputStream1;
    private InputStream inputStream, inputStream1;
    private static final byte[] readPreByte = new byte[]{0x01, 0x01};
    private static final byte[] readBackByte = new byte[]{0x01, 0x01};
    private static final byte[] writePreByte = new byte[]{0x01, 0x05};
    private static final byte[] writeBackByte = new byte[]{(byte) 0xFF, 0x00};
    private byte[] verticalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8a, 0x00, 0x01};//上下脉冲
    private byte[] horizontalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8c, 0x00, 0x01};//横向脉冲

    private static final byte[] readRegisterPreByte = new byte[]{0x01, 0x03};
    private static final byte[] readRegisterBackByte = new byte[]{0x00, 0x03};

    //private boolean read = true;

    private ItemFragmentPagerAdapter pagerAdapter;
    //private Intent readPulseServiceIntent;

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

        final LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        List<Fragment> fragments = new ArrayList<>();
        ItemFragment1 fragment1 = new ItemFragment1();
        fragments.add(fragment1);
        ItemFragment2 fragment2 = new ItemFragment2();
        fragments.add(fragment2);
        ItemFragment3 fragment3 = new ItemFragment3();
        fragments.add(fragment3);
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
                        new Thread(new Runnable() {//循环读上下/横向脉冲
                            @Override
                            public void run() {
                                if (socket != null) {
                                    try {
                                        outputStream = socket.getOutputStream();
                                        inputStream = socket.getInputStream();

                                        byte[] d5002s = Utils.getCommandBytes("D5002", readRegisterPreByte, readRegisterBackByte);
                                        sendCommand(Utils.calcCrc16(d5002s));

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
                                                sendCommand(Utils.calcCrc16(d5002s));//循环
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
                        }).start();

                        new Thread(new Runnable() {//第一页的数据
                            @Override
                            public void run() {

                            }
                        }).start();


                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
                String tag = (String) verticalBtn.getTag();
                byte[] bytes = Utils.getCommandBytes(tag, writePreByte, writeBackByte);
                sendCommand(Utils.calcCrc16(bytes));
                break;
            case R.id.horizontalBtn://横向回原点
                String tag1 = (String) horizontalBtn.getTag();
                byte[] bytes1 = Utils.getCommandBytes(tag1, writePreByte, writeBackByte);
                sendCommand(Utils.calcCrc16(bytes1));
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

        // stopService(readPulseServiceIntent);
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

    private void sendCommand(byte[] bytes) {
        try {
            if (outputStream != null) {
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
