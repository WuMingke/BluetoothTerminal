package com.whosssmade.bluetoothterminal.ui.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
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
import com.whosssmade.bluetoothterminal.ui.adapters.ItemFragmentPagerAdapter;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment1;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment2;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment3;
import com.whosssmade.bluetoothterminal.utils.Utils;

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

    private BluetoothDevice deviceBean;
    private BluetoothSocket socket;
    private byte[] open = new byte[]{0x55, 0x01, 0x01, 0x02, 0x00, 0x00, 0x00, (byte) 0x59};
    private byte[] close = new byte[]{0x55, 0x01, 0x01, 0x01, 0x00, 0x00, 0x00, (byte) 0x58};

    private byte[] handOpen = new byte[]{0x01, 0x05, 0x00, 0x38, (byte) 0xFF, 0x00, 0x0D, (byte) 0xF7};
    private byte[] handClose = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00, 0x5C, 0x37};

    private OutputStream outputStream = null;
    private InputStream inputStream;

    private boolean read = true;

    private ItemFragmentPagerAdapter pagerAdapter;

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

    /*    deviceBean = getIntent().getParcelableExtra(Constants.DEVICE_BEAN);
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
        }).start();*/

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ItemFragment1());
        fragments.add(new ItemFragment2());
        fragments.add(new ItemFragment3());
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
    }


    @OnClick({R.id.back, R.id.verticalBtn, R.id.horizontalBtn, R.id.item1, R.id.item2, R.id.item3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verticalBtn://上下回原点
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
