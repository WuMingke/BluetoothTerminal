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
import android.widget.RelativeLayout;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, ViewPager.OnPageChangeListener, ItemFragment1.OnFragment1BtnClick, ItemFragment2.OnFragment2BtnClick, ItemFragment3.OnFragment3BtnClick {

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
    private byte[] handOpen1 = new byte[]{0x01, 0x05, 0x00, 0x38, (byte) 0xFF, 0x00};
    private byte[] handClose = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00, 0x5C, 0x37};
    private byte[] handClose1 = new byte[]{0x01, 0x05, 0x00, 0x39, (byte) 0xFF, 0x00};

    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private static final byte[] readPreByte = new byte[]{0x01, 0x01};
    private static final byte[] readBackByte = new byte[]{0x00, 0x01};
    private static final byte[] readBackByte2 = new byte[]{0x00, 0x08};//M50-M57
    private static final byte[] writePreByte = new byte[]{0x01, 0x05};
    private static final byte[] writeBackByte = new byte[]{(byte) 0xFF, 0x00};
    private static final byte[] writeBackByte2 = new byte[]{0x00, 0x00};
    private byte[] verticalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8a, 0x00, 0x01};//上下脉冲
    private byte[] horizontalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8c, 0x00, 0x01};//横向脉冲

    private static final byte[] readRegisterPreByte = new byte[]{0x01, 0x03};
    private static final byte[] readRegisterBackByte = new byte[]{0x00, 0x04};

    private ItemFragmentPagerAdapter pagerAdapter;


    private LoadingDialog loadingDialog;

    private ItemFragment1 itemFragment1;
    private ItemFragment2 itemFragment2;
    private ItemFragment3 itemFragment3;

    private MyHandler myHandler;
    private byte[] commandBytes;
    private List<Integer> finalData = new ArrayList<>();
    private int readData;
    //private boolean needReadData;
    private byte[] m100s;
    private byte[] m100s1;
    private byte[] m54s;
    private byte[] m54s1;
    private byte[] m55s;
    private byte[] m55s1;
    private byte[] m50s;
    private byte[] m51s;
    private byte[] m52s;
    private byte[] m53s;

    private boolean onUpClicked, onDownClicked;
    private byte[] m50s1;
    private byte[] m51s1;
    private byte[] d5002s;
    private byte[] m52s1;
    private byte[] m53s1;
    private byte[] m54s2;
    private byte[] m55s2;
    private byte[] m56s;
    private byte[] m56s1;
    private byte[] m57s;
    private byte[] m57s1;
    private byte[] m102s;
    private byte[] m102s1;
    private byte[] m107s;
    private byte[] m107s1;
    private byte[] m108s;
    private byte[] m108s1;
    private byte[] m109s;
    private byte[] m109s1;
    private byte[] m110s;
    private byte[] m110s1;
    private byte[] m116s;
    private byte[] m116s1;
    private byte[] m117s;
    private byte[] m117s1;
    private byte[] m114s;
    private byte[] m114s1;
    private byte[] m115s;
    private byte[] m115s1;
    private byte[] m112s;
    private byte[] m112s1;
    private byte[] m113s;
    private byte[] m113s1;
    private byte[] m201s;
    private byte[] m201s1;
    private byte[] m200s;
    private byte[] m200s1;
    private byte[] m111s;
    private byte[] m111s1;
    private byte[] m119s;
    private byte[] m119s1;
    private byte[] m118s;
    private byte[] m118s1;
    private byte[] m121s;
    private byte[] m121s1;
    private byte[] m122s;
    private byte[] m122s1;
    private byte[] m10s;
    private byte[] m10s1;
    private byte[] m130s;
    private byte[] m130s1;
    private byte[] m103s;
    private byte[] m103s1;
    private byte[] m104s;
    private byte[] m104s1;
    private byte[] m105s;
    private byte[] m105s1;
    private byte[] m106s;
    private byte[] m106s1;
    private byte[] m50s2;

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

    @Override
    public void onUpClick() {
        if (!onUpClicked) {
            if (m50s == null) {
                m50s = Utils.getCommandBytes("M50", writePreByte, writeBackByte);
            }
            sendCommand(m50s);
            onUpClicked = true;
            itemFragment1.getUp().setBackgroundResource(R.drawable.btn_click_down);

          /*  if (m50s2==null){
                m50s2 = Utils.getCommandBytes("M50", readPreByte, readBackByte);
            }
            sendCommand(m50s2);*/
        } else {
            if (m50s1 == null) {
                m50s1 = Utils.getCommandBytes("M50", writePreByte, writeBackByte2);
            }
            sendCommand(m50s1);
            onUpClicked = false;
            itemFragment1.getUp().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    @Override
    public void onDownClick() {
        if (!onDownClicked) {
            if (m51s == null) {
                m51s = Utils.getCommandBytes("M51", writePreByte, writeBackByte);
            }
            sendCommand(m51s);
            onDownClicked = true;
            itemFragment1.getDown().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m51s1 == null) {
                m51s1 = Utils.getCommandBytes("M51", writePreByte, writeBackByte2);
            }
            sendCommand(m51s1);
            onDownClicked = false;
            itemFragment1.getDown().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    private boolean onForwardClicked, onBackClicked;

    @Override
    public void onForwardClick() {
        if (!onForwardClicked) {
            if (m52s == null) {
                m52s = Utils.getCommandBytes("M52", writePreByte, writeBackByte);
            }
            sendCommand(m52s);
            onForwardClicked = true;
            itemFragment1.getForward().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m52s1 == null) {
                m52s1 = Utils.getCommandBytes("M52", writePreByte, writeBackByte2);
            }
            sendCommand(m52s1);
            onForwardClicked = false;
            itemFragment1.getForward().setBackgroundResource(R.drawable.btn_bg);
        }


    }

    @Override
    public void onBackClick() {
        //  itemFragment1.getBack().setBackgroundResource(R.drawable.btn_click_down);
        if (!onBackClicked) {
            if (m53s == null) {
                m53s = Utils.getCommandBytes("M53", writePreByte, writeBackByte);
            }
            sendCommand(m53s);
            onBackClicked = true;
            itemFragment1.getBack().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m53s1 == null) {
                m53s1 = Utils.getCommandBytes("M53", writePreByte, writeBackByte2);
            }
            sendCommand(m53s1);
            onBackClicked = false;
            itemFragment1.getBack().setBackgroundResource(R.drawable.btn_bg);
        }


    }

    private boolean onRotation1Clicked, onRotation2Clicked;

    @Override
    public void onRotation1Click() {

        if (!onRotation1Clicked) {
            if (m54s == null) {
                m54s = Utils.getCommandBytes("M54", writePreByte, writeBackByte);
            }
            sendCommand(m54s);
            onRotation1Clicked = true;
            itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m54s2 == null) {
                m54s2 = Utils.getCommandBytes("M54", writePreByte, writeBackByte2);
            }
            sendCommand(m54s2);
            onRotation1Clicked = false;
            itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    @Override
    public void onRotation2Click() {

        if (!onRotation2Clicked) {
            if (m55s == null) {
                m55s = Utils.getCommandBytes("M55", writePreByte, writeBackByte);
            }
            sendCommand(m55s);
            onRotation2Clicked = true;
            itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m55s2 == null) {
                m55s2 = Utils.getCommandBytes("M55", writePreByte, writeBackByte2);
            }
            sendCommand(m55s2);
            onRotation2Clicked = false;
            itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    private boolean onOpenClicked, onCloseClicked;

    @Override
    public void onOpenClick() {
        if (!onOpenClicked) {
            if (m56s == null) {
                m56s = Utils.getCommandBytes("M56", writePreByte, writeBackByte);
            }
            sendCommand(m56s);
            onOpenClicked = true;
            itemFragment1.getOpen().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m56s1 == null) {
                m56s1 = Utils.getCommandBytes("M56", writePreByte, writeBackByte2);
            }
            sendCommand(m56s1);
            onOpenClicked = false;
            itemFragment1.getOpen().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    @Override
    public void onCloseClick() {

        if (!onCloseClicked) {
            if (m57s == null) {
                m57s = Utils.getCommandBytes("M57", writePreByte, writeBackByte);
            }
            sendCommand(m57s);
            onCloseClicked = true;
            itemFragment1.getClose().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m57s1 == null) {
                m57s1 = Utils.getCommandBytes("M57", writePreByte, writeBackByte2);
            }
            sendCommand(m57s1);
            onCloseClicked = false;
            itemFragment1.getClose().setBackgroundResource(R.drawable.btn_bg);
        }

    }

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
     */

    private boolean onVertical_rotation_btnClicked;

    @Override
    public void onVertical_rotation_btnClick() {
        if (!onVertical_rotation_btnClicked) {
            if (m102s == null) {
                m102s = Utils.getCommandBytes("M102", writePreByte, writeBackByte);
            }
            sendCommand(m102s);
            onVertical_rotation_btnClicked = true;
            itemFragment2.getVertical_rotation_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m102s1 == null) {
                m102s1 = Utils.getCommandBytes("M102", writePreByte, writeBackByte2);
            }
            sendCommand(m102s1);
            onVertical_rotation_btnClicked = false;
            itemFragment2.getVertical_rotation_btn().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    private boolean onVertical_machine_btnClicked;

    @Override
    public void onVertical_machine_btnClick() {
        if (!onVertical_machine_btnClicked) {
            if (m103s == null) {
                m103s = Utils.getCommandBytes("M103", writePreByte, writeBackByte);
            }
            sendCommand(m103s);
            onVertical_machine_btnClicked = true;
            itemFragment2.getVertical_machine_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m103s1 == null) {
                m103s1 = Utils.getCommandBytes("M103", writePreByte, writeBackByte2);
            }
            sendCommand(m103s1);
            onVertical_machine_btnClicked = false;
            itemFragment2.getVertical_machine_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onVertical_hot_btnClicked;

    @Override
    public void onVertical_hot_btnClick() {
        if (!onVertical_hot_btnClicked) {
            if (m104s == null) {
                m104s = Utils.getCommandBytes("M104", writePreByte, writeBackByte);
            }
            sendCommand(m104s);
            onVertical_hot_btnClicked = true;
            itemFragment2.getVertical_hot_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m104s1 == null) {
                m104s1 = Utils.getCommandBytes("M104", writePreByte, writeBackByte2);
            }
            sendCommand(m104s1);
            onVertical_hot_btnClicked = false;
            itemFragment2.getVertical_hot_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onVertical_out_btnClicked;

    @Override
    public void onVertical_out_btnClick() {
        if (!onVertical_out_btnClicked) {
            if (m105s == null) {
                m105s = Utils.getCommandBytes("M105", writePreByte, writeBackByte);
            }
            sendCommand(m105s);
            onVertical_out_btnClicked = true;
            itemFragment2.getVertical_out_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m105s1 == null) {
                m105s1 = Utils.getCommandBytes("M105", writePreByte, writeBackByte2);
            }
            sendCommand(m105s1);
            onVertical_out_btnClicked = false;
            itemFragment2.getVertical_out_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onVertical_get_btnClicked;

    @Override
    public void onVertical_get_btnClick() {
        if (!onVertical_get_btnClicked) {
            if (m106s == null) {
                m106s = Utils.getCommandBytes("M106", writePreByte, writeBackByte);
            }
            sendCommand(m106s);
            onVertical_get_btnClicked = true;
            itemFragment2.getVertical_get_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m106s1 == null) {
                m106s1 = Utils.getCommandBytes("M106", writePreByte, writeBackByte2);
            }
            sendCommand(m106s1);
            onVertical_get_btnClicked = false;
            itemFragment2.getVertical_get_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHorizontal_entrepot_btnClicked;

    @Override
    public void onHorizontal_entrepot_btnClick() {
        if (!onHorizontal_entrepot_btnClicked) {
            if (m107s == null) {
                m107s = Utils.getCommandBytes("M107", writePreByte, writeBackByte);
            }
            onHorizontal_entrepot_btnClicked = true;
            sendCommand(m107s);
            itemFragment2.getHorizontal_entrepot_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m107s1 == null) {
                m107s1 = Utils.getCommandBytes("M107", writePreByte, writeBackByte2);
            }
            sendCommand(m107s1);
            onHorizontal_entrepot_btnClicked = false;
            itemFragment2.getHorizontal_entrepot_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHorizontal_machine_btnClicked;

    @Override
    public void onHorizontal_machine_btnClick() {
        if (!onHorizontal_machine_btnClicked) {
            if (m108s == null) {
                m108s = Utils.getCommandBytes("M108", writePreByte, writeBackByte);
            }
            sendCommand(m108s);
            onHorizontal_machine_btnClicked = true;
            itemFragment2.getHorizontal_machine_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m108s1 == null) {
                m108s1 = Utils.getCommandBytes("M108", writePreByte, writeBackByte2);
            }
            sendCommand(m108s1);
            onHorizontal_machine_btnClicked = false;
            itemFragment2.getHorizontal_machine_btn().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    private boolean onHorizontal_hot_btnClicked;

    @Override
    public void onHorizontal_hot_btnClick() {
        if (!onHorizontal_hot_btnClicked) {
            if (m109s == null) {
                m109s = Utils.getCommandBytes("M109", writePreByte, writeBackByte);
            }
            sendCommand(m109s);
            onHorizontal_hot_btnClicked = true;
            itemFragment2.getHorizontal_hot_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m109s1 == null) {
                m109s1 = Utils.getCommandBytes("M109", writePreByte, writeBackByte2);
            }
            sendCommand(m109s1);
            onHorizontal_hot_btnClicked = false;
            itemFragment2.getHorizontal_hot_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHorizontal_out_btnClicked;

    @Override
    public void onHorizontal_out_btnClick() {
        if (!onHorizontal_out_btnClicked) {
            if (m110s == null) {
                m110s = Utils.getCommandBytes("M110", writePreByte, writeBackByte);
            }
            sendCommand(m110s);
            onHorizontal_out_btnClicked = true;
            itemFragment2.getHorizontal_out_btn().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m110s1 == null) {
                m110s1 = Utils.getCommandBytes("M110", writePreByte, writeBackByte2);
            }
            sendCommand(m110s1);
            onHorizontal_out_btnClicked = false;
            itemFragment2.getHorizontal_out_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    @Override
    public void onDown_pulse_btnClick() {

    }

    /*
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
    private boolean onMachine_outClicked;

    @Override
    public void onMachine_outClick() {
        if (!onMachine_outClicked) {
            if (m116s == null) {
                m116s = Utils.getCommandBytes("M116", writePreByte, writeBackByte);
            }
            sendCommand(m116s);
            onMachine_outClicked = true;
            itemFragment2.getMachine_out().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m116s1 == null) {
                m116s1 = Utils.getCommandBytes("M116", writePreByte, writeBackByte2);
            }
            sendCommand(m116s1);
            onMachine_outClicked = false;
            itemFragment2.getMachine_out().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onMachine_inClicked;

    @Override
    public void onMachine_inClick() {
        if (!onMachine_inClicked) {
            if (m117s == null) {
                m117s = Utils.getCommandBytes("M117", writePreByte, writeBackByte);
            }
            sendCommand(m117s);
            onMachine_inClicked = true;
            itemFragment2.getMachine_in().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m117s1 == null) {
                m117s1 = Utils.getCommandBytes("M117", writePreByte, writeBackByte2);
            }
            sendCommand(m117s);
            onMachine_inClicked = false;
            itemFragment2.getMachine_in().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHot_outClick;

    @Override
    public void onHot_outClick() {
        if (!onHot_outClick) {
            if (m114s == null) {
                m114s = Utils.getCommandBytes("M114", writePreByte, writeBackByte);
            }
            sendCommand(m114s);
            onHot_outClick = true;
            itemFragment2.getHot_out().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m114s1 == null) {
                m114s1 = Utils.getCommandBytes("M114", writePreByte, writeBackByte2);
            }
            sendCommand(m114s1);
            onHot_outClick = false;
            itemFragment2.getHot_out().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHot_inClicked;

    @Override
    public void onHot_inClick() {
        if (!onHot_inClicked) {
            if (m115s == null) {
                m115s = Utils.getCommandBytes("M115", writePreByte, writeBackByte);
            }
            sendCommand(m115s);
            onHot_inClicked = true;
            itemFragment2.getHot_in().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m115s1 == null) {
                m115s1 = Utils.getCommandBytes("M115", writePreByte, writeBackByte2);
            }
            sendCommand(m115s1);
            onHot_inClicked = false;
            itemFragment2.getHot_in().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHandspike_outClicked;

    @Override
    public void onHandspike_outClick() {
        if (!onHandspike_outClicked) {
            if (m112s == null) {
                m112s = Utils.getCommandBytes("M112", writePreByte, writeBackByte);
            }
            sendCommand(m112s);
            onHandspike_outClicked = true;
            itemFragment2.getHandspike_out().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m112s1 == null) {
                m112s1 = Utils.getCommandBytes("M112", writePreByte, writeBackByte2);
            }
            sendCommand(m112s1);
            onHandspike_outClicked = false;
            itemFragment2.getHandspike_out().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHandspike_inClicked;

    @Override
    public void onHandspike_inClick() {
        if (!onHandspike_inClicked) {
            if (m113s == null) {
                m113s = Utils.getCommandBytes("M113", writePreByte, writeBackByte);
            }
            sendCommand(m113s);
            onHandspike_inClicked = true;
            itemFragment2.getHandspike_in().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m113s1 == null) {
                m113s1 = Utils.getCommandBytes("M113", writePreByte, writeBackByte2);
            }
            sendCommand(m113s1);
            onHandspike_inClicked = false;
            itemFragment2.getHandspike_in().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onOut_outClicked;

    @Override
    public void onOut_outClick() {
        if (!onOut_outClicked) {
            if (m201s == null) {
                m201s = Utils.getCommandBytes("M201", writePreByte, writeBackByte);
            }
            sendCommand(m201s);
            onOut_outClicked = true;
            itemFragment2.getOut_out().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m201s1 == null) {
                m201s1 = Utils.getCommandBytes("M201", writePreByte, writeBackByte2);
            }
            sendCommand(m201s1);
            onOut_outClicked = false;
            itemFragment2.getOut_out().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onOut_inClick;

    @Override
    public void onOut_inClick() {
        if (!onOut_inClick) {
            if (m200s == null) {
                m200s = Utils.getCommandBytes("M200", writePreByte, writeBackByte);
            }
            sendCommand(m200s);
            onOut_inClick = true;
            itemFragment2.getOut_in().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m200s1 == null) {
                m200s1 = Utils.getCommandBytes("M200", writePreByte, writeBackByte2);
            }
            sendCommand(m200s1);
            onOut_inClick = false;
            itemFragment2.getOut_in().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onBelt_outClick;

    @Override
    public void onBelt_outClick() {
        if (!onBelt_outClick) {
            if (m111s == null) {
                m111s = Utils.getCommandBytes("M111", writePreByte, writeBackByte);
            }
            sendCommand(m111s);
            onBelt_outClick = true;
            itemFragment2.getBelt_out().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m111s1 == null) {
                m111s1 = Utils.getCommandBytes("M111", writePreByte, writeBackByte2);
            }
            sendCommand(m111s1);
            onBelt_outClick = false;
            itemFragment2.getBelt_out().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    /**
     * 打印机电源M119
     * 打印机开关M118
     * 烤箱发热丝M121
     * 烤箱风扇M122
     * 机械复位M10
     * 按钮复位M130
     */

    private boolean onMachine_powerClicked;

    @Override
    public void onMachine_powerClick() {
        if (!onMachine_powerClicked) {
            if (m119s == null) {
                m119s = Utils.getCommandBytes("M119", writePreByte, writeBackByte);
            }
            sendCommand(m119s);
            onMachine_powerClicked = true;
            itemFragment3.getMachine_power().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m119s1 == null) {
                m119s1 = Utils.getCommandBytes("M119", writePreByte, writeBackByte2);
            }
            sendCommand(m119s1);
            onMachine_powerClicked = false;
            itemFragment3.getMachine_power().setBackgroundResource(R.drawable.btn_bg);
        }

    }

    private boolean onMachine_switchClicked;

    @Override
    public void onMachine_switchClick() {
        if (!onMachine_switchClicked) {
            if (m118s == null) {
                m118s = Utils.getCommandBytes("M118", writePreByte, writeBackByte);
            }
            sendCommand(m118s);
            onMachine_switchClicked = true;
            itemFragment3.getMachine_switch().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m118s1 == null) {
                m118s1 = Utils.getCommandBytes("M118", writePreByte, writeBackByte2);
            }
            sendCommand(m118s1);
            onMachine_switchClicked = false;
            itemFragment3.getMachine_switch().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onHotClicked;

    @Override
    public void onHotClick() {
        if (!onHotClicked) {
            if (m121s == null) {
                m121s = Utils.getCommandBytes("M121", writePreByte, writeBackByte);
            }
            sendCommand(m121s);
            onHotClicked = true;
            itemFragment3.getHot().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m121s1 == null) {
                m121s1 = Utils.getCommandBytes("M121", writePreByte, writeBackByte2);
            }
            sendCommand(m121s1);
            onHotClicked = false;
            itemFragment3.getHot().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onFanClicked;

    @Override
    public void onFanClick() {
        if (!onFanClicked) {
            if (m122s == null) {
                m122s = Utils.getCommandBytes("M122", writePreByte, writeBackByte);
            }
            sendCommand(m122s);
            onFanClicked = true;
            itemFragment3.getFan().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m122s1 == null) {
                m122s1 = Utils.getCommandBytes("M122", writePreByte, writeBackByte2);
            }
            sendCommand(m122s1);
            onFanClicked = false;
            itemFragment3.getFan().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onMachine_resetClicked;

    @Override
    public void onMachine_resetClick() {
        if (!onMachine_resetClicked) {
            if (m10s == null) {
                m10s = Utils.getCommandBytes("M10", writePreByte, writeBackByte);
            }
            sendCommand(m10s);
            onMachine_resetClicked = true;
            itemFragment3.getMachine_reset().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m10s1 == null) {
                m10s1 = Utils.getCommandBytes("M10", writePreByte, writeBackByte2);
            }
            sendCommand(m10s1);
            onMachine_resetClicked = false;
            itemFragment3.getMachine_reset().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onBtn_resetClicked;

    @Override
    public void onBtn_resetClick() {
        if (!onBtn_resetClicked) {
            if (m130s == null) {
                m130s = Utils.getCommandBytes("M130", writePreByte, writeBackByte);
            }
            sendCommand(m130s);
            onBtn_resetClicked = true;
            itemFragment3.getBtn_reset().setBackgroundResource(R.drawable.btn_click_down);
        } else {
            if (m130s1 == null) {
                m130s1 = Utils.getCommandBytes("M130", writePreByte, writeBackByte2);
            }
            sendCommand(m130s1);
            onBtn_resetClicked = false;
            itemFragment3.getBtn_reset().setBackgroundResource(R.drawable.btn_bg);
        }

    }

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
                        String data = msg.getData().getString("data");
                        Log.i("wmk", "handler----指令----" + s + "----data----" + data);
                        switch (s) {
                           /* case "D5002":
                                vertical.setText(data);
                                // loopThread.setCommandBytes(Utils.calcCrc16(d5002s));
                                break;
                            case "D5004":
                                horizontal.setText(data);
                                // loopThread2.setCommandBytes(Utils.calcCrc16(d5004s));
                                break;*/
                            case "M100":
                                if (!data.equals(" 0")) {
                                    // loopThread.setCommandBytes(m100s1);
                                    Utils.setTextClickDown(verticalBtn);
                                } else {
                                    Utils.setTextClickUp(verticalBtn);
                                    Log.i("wmk", "检查上下回原点结束");
                                }
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
                                if (!data.equals(" 0")) {
                                    sendCommand(m54s1);
                                    //   loopThread3.setCommandBytes(m54s1);
                                    itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_click_down);
                                    itemFragment1.getRotation1().setEnabled(false);
                                    Log.i("wmk", "继续检查出口面");
                                } else {
                                    itemFragment1.getRotation1().setEnabled(true);
                                    Utils.setTextClickUp(itemFragment1.getRotation1());
                                    //   loopThread2 = null;
                                    //  loopThread3 = null;
                                    Log.i("wmk", "检查出口面结束");
                                }
                                break;
                            case "M55":
                                if (!data.equals(" 0")) {
                                    //loopThread5.setCommandBytes(m55s1);
                                    sendCommand(m55s1);
                                    itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_click_down);
                                    itemFragment1.getRotation2().setEnabled(false);
                                    Log.i("wmk", "继续检查打印机面");
                                } else {
                                    itemFragment1.getRotation2().setEnabled(true);
                                    Utils.setTextClickUp(itemFragment1.getRotation2());
                                    //  loopThread4 = null;
                                    //loopThread5 = null;
                                    Log.i("wmk", "检查打印机面结束");
                                }
                                break;
                            case "M56":
                                //Utils.setBtnState(data, itemFragment1.getOpen());
                                if (!data.equals(" 0")) {
                                    //loopThread10.setCommandBytes(m56s);
                                } else {
                                    Utils.setTextClickUp(itemFragment1.getOpen());
                                    Log.i("wmk", "检查张开结束");
                                }
                                break;
                            case "M57":
                                // Utils.setBtnState(data, itemFragment1.getClose());
                                if (!data.equals(" 0")) {
                                    // loopThread11.setCommandBytes(m57s);
                                } else {
                                    Utils.setTextClickUp(itemFragment1.getClose());
                                    Log.i("wmk", "检查夹紧结束");
                                }
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
        itemFragment1.setOnFragment1BtnClick(this);
        fragments.add(itemFragment1);
        itemFragment2 = new ItemFragment2();
        itemFragment2.setOnFragment2BtnClick(this);
        fragments.add(itemFragment2);
        itemFragment3 = new ItemFragment3();
        itemFragment3.setOnFragment3BtnClick(this);
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

        deviceBean = getIntent().getParcelableExtra(Constants.DEVICE_BEAN);

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
                        //单线程
                        mInputStream = socket.getInputStream();
                        mOutputStream = socket.getOutputStream();

                        //读脉冲
                        d5002s = Utils.getCommandBytes("D5002", readRegisterPreByte, readRegisterBackByte);
                        Log.i("wmk", "命令：" + Utils.bytesToHexString(d5002s));

                        sendCommand(d5002s);
                        commandBytes = d5002s;

                        while (true) {

                            final byte[] bytes = Utils.readInputStream(mInputStream);
                            Log.i("wmk", "-----readInputStream-----" + Utils.bytesToHexString(bytes));
                            if (checkData(bytes)) return;

                            //计算数据  1176  67296-66976

                            if (bytes[1] == (byte) 0x03) {
                                byte[] dataByte = new byte[bytes.length - 5];
                                for (int i = 0; i < dataByte.length; i++) {
                                    dataByte[i] = bytes[i + 3];
                                }
                                for (int i = 0; i < dataByte.length / 4; i++) {
                                    byte[] itemByte = new byte[4];
                                    for (int j = 0; j < 4; j++) {
                                        itemByte[j] = dataByte[4 * i + j];
                                    }
                                    if (itemByte[2] == (byte) 0xff && itemByte[3] == (byte) 0xff) {
                                        readData = 65536 - Utils.bytesToInt(new byte[]{itemByte[0], itemByte[1]});
                                    } else {
                                        readData = Utils.bytesToInt(new byte[]{itemByte[0], itemByte[1]}) +
                                                Utils.bytesToInt(new byte[]{itemByte[2], itemByte[3]});
                                    }
                                    finalData.add(readData);
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        vertical.setText(String.valueOf(finalData.get(0)));
                                        horizontal.setText(String.valueOf(finalData.get(1)));
                                        finalData.clear();
                                    }
                                });
                            }


                        }


                        //**********************查询按钮状态*************************************************/
                /*        //上下回原点
                        m100s1 = Utils.getCommandBytes("M100", readPreByte, readBackByte);
                        loopThread1 = new LoopThread(socket, verticalBtn.getTag(), m100s1, myHandler);
                        loopThread1.setNeedRead(true);
                        loopThread1.start();
                      //横向回原点
                        m101s = Utils.getCommandBytes("M101", readPreByte, readBackByte);
                        loopThread3 = new LoopThread(socket, horizontalBtn.getTag(), m101s, myHandler);
                        loopThread3.setNeedRead(true);
                        loopThread3.start();*/



                    /*    //上移
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
                        loopThread7.start();*/
                        //出口面
                  /*      m54s = Utils.getCommandBytes("M54", readPreByte, readBackByte);
                        loopThread8 = new LoopThread(socket, itemFragment1.getRotation1().getTag(), myHandler, true);*/
                        // loopThread8.setCommandBytes(m54s);
                        // loopThread8.start();
                        //打印机面
                      /*  m55s = Utils.getCommandBytes("M55", readPreByte, readBackByte);
                        loopThread9 = new LoopThread(socket, itemFragment1.getRotation2().getTag(), myHandler, true);*/
                        //  loopThread9.setCommandBytes(m55s);
                        //  loopThread9.start();
                        //张开
                    /*    m56s = Utils.getCommandBytes("M56", readPreByte, readBackByte);
                        loopThread10 = new LoopThread(socket, itemFragment1.getOpen().getTag(), myHandler, true);
                      //  loopThread10.setCommandBytes(m56s);
                        loopThread10.start();
                        //夹紧
                        m57s = Utils.getCommandBytes("M57", readPreByte, readBackByte);
                        // Log.i("wmk", "指令：" + Arrays.toString(m57s));
                        loopThread11 = new LoopThread(socket, itemFragment1.getClose().getTag(), myHandler, true);
                      //  loopThread11.setCommandBytes(m57s);
                        loopThread11.start();*/


                   /*     //上下旋转位M102
                        m102s = Utils.getCommandBytes("M102", readPreByte, readBackByte);
                        loopThread22 = new LoopThread(socket, itemFragment2.getVertical_rotation_btn().getTag(), m102s, myHandler);
                        loopThread22.setNeedRead(true);
                        loopThread22.start();*/







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



                       /* new Thread(new Runnable() {//按钮
                            @Override
                            public void run() {
                                if (socket != null) {
                                    try {
                                        inputStream3 = socket.getInputStream();
                                        outputStream3 = socket.getOutputStream();

                                        if (outputStream3 != null) {
                                           // byte[] m50s = Utils.getCommandBytes("M50", readPreByte, readBackByte2);
                                            byte[] b = Utils.calcCrc16(new byte[]{0x01,0x01,0x00,0x32,0x00,0x01});
                                          //  byte[] b1 = {0x01, 0x01, 0x00, 0x32, 0x00, 0x01, (byte) 0x92, 0x05};
                                            outputStream3.write(b);
                                            Log.i("wmk", "指令b---" + Arrays.toString(b));
                                          //  Log.i("wmk", "指令b1---" + Arrays.toString(b1));
                                            outputStream3.flush();
                                        }
                                        while (true) {
                                            byte[] bytes = readInputStream(inputStream3);
                                            //Log.i("wmk", "----------" + Utils.bytesToHexString(bytes));
                                            final String s = Utils.bytesToHexString(bytes);
                                            final String[] s1 = s.split(" ");
                                            Log.i("wmk", "Btn_Data---" + Arrays.toString(s1));
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

    }

    private boolean checkData(byte[] bytes) {
        if (bytes.length < 2) {
            // continue;
            return true;
        }
        //校验
        byte[] bytes1 = new byte[bytes.length - 2];
        byte[] crcByte = {bytes[bytes.length - 2], bytes[bytes.length - 1]};
        for (int i = 0; i < bytes1.length; i++) {
            bytes1[i] = bytes[i];
        }
        if (!Arrays.toString(Utils.crcByte(bytes1))
                .equals(Arrays.toString(crcByte))) {
            sendCommand(commandBytes);//数据不对，重新请求
            // continue;
            return true;
        }
        return false;
    }


    @OnClick({R.id.back, R.id.verticalBtn, R.id.horizontalBtn,
            R.id.item1, R.id.item2, R.id.item3,
            R.id.commandTitle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verticalBtn://上下回原点
                // verticalBtn.setBackgroundResource(R.drawable.btn_click_down);
              /*  if (m100s == null) {
                    m100s = Utils.getCommandBytes("M100", writePreByte, writeBackByte);
                }
                if (m100s1 == null) {
                    m100s1 = Utils.getCommandBytes("M100", readPreByte, readBackByte);
                }

                sendCommand(m100s);


                loopThread = new LoopThread(socket, verticalBtn.getTag(), myHandler, true);
                loopThread.setCommandBytes(m100s1);
                loopThread.start();*/
                //   loopThread1.setCommandBytes(m100s1);// 检查

                break;
            case R.id.horizontalBtn://横向回原点
                horizontalBtn.setBackgroundResource(R.drawable.btn_click_down);
              /*  if (m101s1 == null) {
                    m101s1 = Utils.getCommandBytes("M101", writePreByte, writeBackByte);
                }*/
                //  loopThread13 = new LoopThread(socket, horizontal.getTag(), m101s1, myHandler);
                //loopThread13.setNeedRead(false);
                //  loopThread13.start();

                //  loopThread3.setCommandBytes(m101s);

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
            case R.id.commandTitle:
                sendCommand(d5002s);
                commandBytes = d5002s;
              /*  sendCommand(d5002s);
                commandBytes = d5002s;
                 byte[] bytes =null;
                try {
                    bytes = Utils.readInputStream(mInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("wmk", "-----readInputStream-----" + Utils.bytesToHexString(bytes));
               // if (checkData(bytes)) return;
                if (bytes.length < 2) {
                    // continue;
                    return;
                }
                //校验
                byte[] bytes1 = new byte[bytes.length - 2];
                byte[] crcByte = {bytes[bytes.length - 2], bytes[bytes.length - 1]};
                for (int i = 0; i < bytes1.length; i++) {
                    bytes1[i] = bytes[i];
                }
                if (!Arrays.toString(Utils.crcByte(bytes1))
                        .equals(Arrays.toString(crcByte))) {
                    sendCommand(commandBytes);//数据不对，重新请求
                    // continue;
                    return;
                }
               // return false;


                //计算数据  1176  67296-66976

                byte[] dataByte = new byte[bytes.length - 5];
                for (int i = 0; i < dataByte.length; i++) {
                    dataByte[i] = bytes[i + 3];
                }
                for (int i = 0; i < dataByte.length / 4; i++) {
                    byte[] itemByte = new byte[4];
                    for (int j = 0; j < 4; j++) {
                        itemByte[j] = dataByte[4 * i + j];
                    }
                    if (itemByte[2] == (byte) 0xff && itemByte[3] == (byte) 0xff) {
                        readData = 65536 - Utils.bytesToInt(new byte[]{itemByte[0], itemByte[1]});
                    } else {
                        readData = Utils.bytesToInt(new byte[]{itemByte[0], itemByte[1]}) +
                                Utils.bytesToInt(new byte[]{itemByte[2], itemByte[3]});
                    }
                    finalData.add(readData);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vertical.setText(String.valueOf(finalData.get(0)));
                        horizontal.setText(String.valueOf(finalData.get(1)));
                        finalData.clear();
                               *//*     try {
                                        if (socket != null) {
                                            socket.close();
                                        }
                                        if (mOutputStream != null) {
                                            mOutputStream.close();
                                        }
                                        if (mInputStream != null) {
                                            mInputStream.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }*//*
                    }
                });
                // }*/
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // read = false;

        try {
            if (socket != null) {
                socket.close();
            }
            if (mOutputStream != null) {
                mOutputStream.close();
            }
            if (mInputStream != null) {
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void sendCommand(byte[] bytes) {
        try {
            if (mOutputStream != null) {
                mOutputStream.write(Utils.calcCrc16(bytes));
                mOutputStream.flush();
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
