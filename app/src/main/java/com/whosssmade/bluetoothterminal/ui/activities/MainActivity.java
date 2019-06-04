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
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private static final byte[] readBackByte3 = new byte[]{0x00, 0x04};
    private static final byte[] readBackByte4 = new byte[]{0x00, 0x02};
    private static final byte[] writePreByte = new byte[]{0x01, 0x05};
    private static final byte[] writeBackByte = new byte[]{(byte) 0xFF, 0x00};
    private static final byte[] writeBackByte2 = new byte[]{0x00, 0x00};
    private byte[] verticalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8a, 0x00, 0x01};//上下脉冲
    private byte[] horizontalPulse = new byte[]{0x01, 0x01, 0x13, (byte) 0x8c, 0x00, 0x01};//横向脉冲

    private static final byte[] readRegisterPreByte = new byte[]{0x01, 0x03};
    private static final byte[] readRegisterBackByte = new byte[]{0x00, 0x04};
    private static final byte[] readRegisterBackByte2 = new byte[]{0x00, 0x10};//第一部分16个
    private static final byte[] readRegisterBackByte3 = new byte[]{0x00, 0x14};//第二部分20个
    private static final byte[] readRegisterBackByte4 = new byte[]{0x00, 0x08};//第二部分8个
    private static final byte[] readRegisterBackByte5 = new byte[]{0x00, 0x04};//第二部分4个
    private static final byte[] readRegisterBackByte6 = new byte[]{0x00, 0x08};//第二部分4个 ??

    private static final byte[] writeRegisterPreByte = new byte[]{0x01, 0x05};

    private ItemFragmentPagerAdapter pagerAdapter;


    private LoadingDialog loadingDialog;

    private ItemFragment1 itemFragment1;
    private ItemFragment2 itemFragment2;
    private ItemFragment3 itemFragment3;

    // private MyHandler myHandler;
    private byte[] commandBytes;
    private List<Integer> finalData = new ArrayList<>();
    private int readData;
    //private boolean needReadData;
    private byte[] m54s;
    private byte[] m55s;
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
    private String BtnTag;
    private byte[] m51s2;
    private byte[] m54s1;
    private byte[] m54s2;
    private byte[] m55s1;
    private byte[] m55s2;
    private byte[] m52s2;
    private byte[] m53s2;
    private byte[] m56s2;
    private byte[] m57s2;
    private byte[] m107s2;
    private byte[] m116s2;
    private byte[] m117s2;
    private byte[] m114s2;
    private byte[] m115s2;
    private byte[] m112s2;
    private byte[] m113s2;
    private byte[] m201s2;
    private byte[] m200s2;
    private byte[] m111s2;
    private byte[] m102s2;
    private byte[] m103s2;
    private byte[] m104s2;
    private byte[] m105s2;
    private byte[] m106s2;
    private byte[] m108s2;
    private byte[] m109s2;
    private byte[] m110s2;
    private byte[] m100s;
    private byte[] m100s1;
    private byte[] m101s;
    private byte[] m101s1;
    private byte[] m100s2;
    private byte[] m101s2;
    private byte[] m119s2;
    private byte[] m118s2;
    private byte[] m121s2;
    private byte[] m122s2;
    private byte[] m10s2;
    private byte[] m130s2;
    private byte[] m58s;
    private byte[] m58s1;
    private byte[] m58s2;
    private byte[] d4000s;

    private boolean isReadD5002;
    private boolean isReadD4000;
    private byte[] d4100s;
    private boolean isReadD4100;
    private byte[] d4020s;
    private boolean isReadD4020;
    private boolean isReadD4210;
    private byte[] d4210s;
    private boolean isReadD4200;
    private byte[] d4200s;
    private boolean isFirstCheckBtn;
    private byte[] m50s3;
    private boolean isSecondCheckBtn;
    private byte[] m58s3;
    private boolean isThirdCheckBtn;
    private byte[] m100s3;
    private boolean isForthCheckBtn;
    private byte[] m108s3;
    private boolean isFifthCheckBtn;
    private byte[] m112s3;
    private boolean isSixthCheckBtn;
    private byte[] m200s3;
    private boolean isSeventhCheckBtn;
    private byte[] m121s3;
    private boolean isEighthCheckBtn;
    private byte[] m10s3;
    private boolean isNinthCheckBtn;
    private byte[] m130s3;


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
            BtnTag = "M50";
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
            BtnTag = "M51";
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
            BtnTag = "M52";
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
            BtnTag = "M53";
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
            if (m54s1 == null) {
                m54s1 = Utils.getCommandBytes("M54", writePreByte, writeBackByte2);
            }
            sendCommand(m54s1);
            onRotation1Clicked = false;
            itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_bg);
        }
        BtnTag = "M54";
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
            if (m55s1 == null) {
                m55s1 = Utils.getCommandBytes("M55", writePreByte, writeBackByte2);
            }
            sendCommand(m55s1);
            onRotation2Clicked = false;
            itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_bg);
        }
        BtnTag = "M55";
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
            BtnTag = "M56";
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
            BtnTag = "M57";
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
            BtnTag = "M102";
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
            BtnTag = "M103";
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
            BtnTag = "M104";
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
            BtnTag = "M105";
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
            BtnTag = "M106";
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
            BtnTag = "M107";
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
            BtnTag = "M108";
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
            BtnTag = "M109";
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
            BtnTag = "M110";
        } else {
            if (m110s1 == null) {
                m110s1 = Utils.getCommandBytes("M110", writePreByte, writeBackByte2);
            }
            sendCommand(m110s1);
            onHorizontal_out_btnClicked = false;
            itemFragment2.getHorizontal_out_btn().setBackgroundResource(R.drawable.btn_bg);
        }
    }

    private boolean onDown_pulse_btnClicked;

    @Override
    public void onDown_pulse_btnClick() {
        if (!onDown_pulse_btnClicked) {
            if (m58s == null) {
                m58s = Utils.getCommandBytes("M58", writePreByte, writeBackByte);
            }
            sendCommand(m58s);
            onDown_pulse_btnClicked = true;
            itemFragment2.getDown_pulse_btn().setBackgroundResource(R.drawable.btn_click_down);
            BtnTag = "M58";
        } else {
            if (m58s1 == null) {
                m58s1 = Utils.getCommandBytes("M58", writePreByte, writeBackByte2);
            }
            sendCommand(m58s1);
            onDown_pulse_btnClicked = false;
            itemFragment2.getDown_pulse_btn().setBackgroundResource(R.drawable.btn_bg);
        }
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
            BtnTag = "M116";
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
            BtnTag = "M117";
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
            BtnTag = "M114";
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
            BtnTag = "M115";
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
            BtnTag = "M112";
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
            BtnTag = "M113";
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
            BtnTag = "M201";
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
            BtnTag = "M200";
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
            BtnTag = "M111";
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
            BtnTag = "M119";
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
            BtnTag = "M118";
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
            BtnTag = "M121";
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
            BtnTag = "M122";
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
            BtnTag = "M10";
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
            BtnTag = "M130";
        } else {
            if (m130s1 == null) {
                m130s1 = Utils.getCommandBytes("M130", writePreByte, writeBackByte2);
            }
            sendCommand(m130s1);
            onBtn_resetClicked = false;
            itemFragment3.getBtn_reset().setBackgroundResource(R.drawable.btn_bg);
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

        //myHandler = new MyHandler(this);

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
                        //上面两个
                        d5002s = Utils.getCommandBytes("D5002", readRegisterPreByte, readRegisterBackByte);
                        //Log.i("wmk", "命令：" + Utils.bytesToHexString(d5002s));
                        sendCommand(d5002s);
                        isReadD5002 = true;
                        //下面数据
                        //脉冲/频率
                        d4000s = Utils.getCommandBytes("D4000", readRegisterPreByte, readRegisterBackByte2);
                        //第二页数值
                        d4100s = Utils.getCommandBytes("D4100", readRegisterPreByte, readRegisterBackByte3);
                        //横向脉冲
                        d4020s = Utils.getCommandBytes("D4020", readRegisterPreByte, readRegisterBackByte4);
                        //放料数量
                        d4210s = Utils.getCommandBytes("D4210", readRegisterPreByte, readRegisterBackByte5);
                        //取货料仓/底部脉冲/料厚脉冲
                        d4200s = Utils.getCommandBytes("D4200", readRegisterPreByte, readRegisterBackByte6);

                        //读按钮
                        m50s3 = Utils.getCommandBytes("M50", readPreByte, readBackByte2);
                        m58s3 = Utils.getCommandBytes("M58", readPreByte, readBackByte);
                        m100s3 = Utils.getCommandBytes("M100", readPreByte, readBackByte2);
                        m108s3 = Utils.getCommandBytes("M108", readPreByte, readBackByte3);
                        m112s3 = Utils.getCommandBytes("M112", readPreByte, readBackByte2);
                        m200s3 = Utils.getCommandBytes("M200", readPreByte, readBackByte4);
                        m121s3 = Utils.getCommandBytes("M121", readPreByte, readBackByte4);
                        m10s3 = Utils.getCommandBytes("M10", readPreByte, readBackByte);
                        m130s3 = Utils.getCommandBytes("M130", readPreByte, readBackByte);


                        while (true) {

                            final byte[] bytes = Utils.readInputStream(mInputStream);
                            Log.i("wmk", "-------------------------------------readInputStream-----" + Utils.bytesToHexString(bytes));
                            //if (checkData(bytes)) return;
                            if (bytes.length < 2) {
                                continue;
                                //return true;
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
                                // return true;
                            }

                            //计算数据  1176  67296-66976

                            if (bytes[1] == (byte) 0x03 && isReadD5002) {
                                Log.i("wmk", "--读寄存器处理D5002--" + Utils.bytesToHexString(bytes));
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
                                        isReadD5002 = false;
                                        sendCommand(d4000s);
                                        isReadD4000 = true;
                                    }
                                });

                            }

                            if (bytes[1] == (byte) 0x03 && isReadD4000) {
                                Log.i("wmk", "--读寄存器处理D4000--" + Utils.bytesToHexString(bytes));
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
                                        isReadD4000 = false;
                                        itemFragment1.getPulse1().setText(String.valueOf(finalData.get(0)));
                                        itemFragment1.getFrequency1().setText(String.valueOf(finalData.get(1)));
                                        itemFragment1.getPulse2().setText(String.valueOf(finalData.get(2)));
                                        itemFragment1.getFrequency2().setText(String.valueOf(finalData.get(3)));
                                        itemFragment1.getPulse3().setText(String.valueOf(finalData.get(4)));
                                        itemFragment1.getFrequency3().setText(String.valueOf(finalData.get(5)));
                                        itemFragment1.getPulse4().setText(String.valueOf(finalData.get(6)));
                                        itemFragment1.getFrequency4().setText(String.valueOf(finalData.get(7)));
                                        finalData.clear();
                                        sendCommand(d4100s);
                                        isReadD4100 = true;
                                    }
                                });
                            }

                            if (bytes[1] == (byte) 0x03 && isReadD4100) {
                                Log.i("wmk", "--读寄存器处理D4100--" + Utils.bytesToHexString(bytes));
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
                                        isReadD4100 = false;
                                        itemFragment2.getVertical_rotation().setText(String.valueOf(finalData.get(0)));
                                        itemFragment2.getVertical_machine().setText(String.valueOf(finalData.get(1)));
                                        itemFragment2.getVertical_hot().setText(String.valueOf(finalData.get(2)));
                                        itemFragment2.getVertical_out().setText(String.valueOf(finalData.get(3)));
                                        itemFragment2.getVertical_get().setText(String.valueOf(finalData.get(8)));
                                        itemFragment2.getHorizontal_entrepot().setText(String.valueOf(finalData.get(4)));
                                        itemFragment2.getHorizontal_machine().setText(String.valueOf(finalData.get(5)));
                                        itemFragment2.getHorizontal_hot().setText(String.valueOf(finalData.get(6)));
                                        itemFragment2.getHorizontal_out().setText(String.valueOf(finalData.get(7)));
                                        itemFragment2.getDown_pulse().setText(String.valueOf(finalData.get(9)));
                                        finalData.clear();
                                        sendCommand(d4020s);
                                        isReadD4020 = true;
                                    }
                                });
                            }

                            if (bytes[1] == (byte) 0x03 && isReadD4020) {
                                Log.i("wmk", "--读寄存器处理D4020--" + Utils.bytesToHexString(bytes));
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
                                        isReadD4020 = false;
                                        itemFragment1.getEntrepot0_pulse().setText(String.valueOf(finalData.get(0)));
                                        itemFragment1.getEntrepot1_pulse().setText(String.valueOf(finalData.get(1)));
                                        itemFragment1.getEntrepot2_pulse().setText(String.valueOf(finalData.get(2)));
                                        itemFragment1.getEntrepot3_pulse().setText(String.valueOf(finalData.get(3)));
                                        finalData.clear();
                                        sendCommand(d4210s);
                                        isReadD4210 = true;
                                    }
                                });
                            }

                            if (bytes[1] == (byte) 0x03 && isReadD4210) {//数据只有一个点位
                                Log.i("wmk", "--读寄存器处理D4210--" + Utils.bytesToHexString(bytes));
                                //  01 03 08    00 01 00 01 00 01 00 01   28 d7
                                byte[] dataByte = new byte[bytes.length - 5];
                                for (int i = 0; i < dataByte.length; i++) {
                                    dataByte[i] = bytes[i + 3];
                                }
                                for (int i = 0; i < dataByte.length / 2; i++) {//4
                                    byte[] itemByte = new byte[2];
                                    for (int j = 0; j < 2; j++) {
                                        itemByte[j] = dataByte[2 * i + j];
                                    }
                                    int i1 = Utils.bytesToInt(new byte[]{itemByte[0], itemByte[1]});
                                    finalData.add(i1);
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        isReadD4210 = false;
                                        itemFragment1.getEntrepot0_quantity().setText(String.valueOf(finalData.get(0)));
                                        itemFragment1.getEntrepot1_quantity().setText(String.valueOf(finalData.get(1)));
                                        itemFragment1.getEntrepot2_quantity().setText(String.valueOf(finalData.get(2)));
                                        itemFragment1.getEntrepot3_quantity().setText(String.valueOf(finalData.get(3)));
                                        finalData.clear();
                                        sendCommand(d4200s);
                                        isReadD4200 = true;
                                    }
                                });
                            }

                            if (bytes[1] == (byte) 0x03 && isReadD4200) {
                                Log.i("wmk", "--读寄存器处理D4200--" + Utils.bytesToHexString(bytes));
                                //00 00    00 01   0f a0    00 00    00 00    d0 90       00 03 00 00
                                //                  4000                      53392
                               /* byte[] dataByte = new byte[bytes.length - 5];
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
                                }*/
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        isReadD4200 = false;
                                        //   itemFragment1.getEntrepot().setText();
                                        // itemFragment1.getBottom_pulse().setText();
                                        //itemFragment1.getThickness_pulse().setText();
                                        finalData.clear();
                                        isFirstCheckBtn = true;
                                        sendCommand(m50s3);
                                        Log.i("wmk", "--m50s3--" + Utils.bytesToHexString(m50s3));
                                    }
                                });
                            }

                            if (isFirstCheckBtn) {
                                isFirstCheckBtn = false;
                                String format = String.format("%8s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format--" + format);
                                Log.i("wmk", "--读按钮--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment1.getUp().setBackgroundResource(R.drawable.btn_bg);
                                            onUpClicked = false;
                                        } else {
                                            itemFragment1.getUp().setBackgroundResource(R.drawable.btn_click_down);
                                            onUpClicked = true;
                                        }
                                        if (strings.get(1).equals("0")) {
                                            itemFragment1.getDown().setBackgroundResource(R.drawable.btn_bg);
                                            onDownClicked = false;
                                        } else {
                                            itemFragment1.getDown().setBackgroundResource(R.drawable.btn_click_down);
                                            onDownClicked = true;
                                        }
                                        if (strings.get(2).equals("0")) {
                                            itemFragment1.getForward().setBackgroundResource(R.drawable.btn_bg);
                                            onForwardClicked = false;
                                        } else {
                                            itemFragment1.getForward().setBackgroundResource(R.drawable.btn_click_down);
                                            onForwardClicked = true;
                                        }
                                        if (strings.get(3).equals("0")) {
                                            itemFragment1.getBack().setBackgroundResource(R.drawable.btn_bg);
                                            onBackClicked = false;
                                        } else {
                                            itemFragment1.getBack().setBackgroundResource(R.drawable.btn_click_down);
                                            onBackClicked = true;
                                        }
                                        if (strings.get(4).equals("0")) {
                                            itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_bg);
                                            onRotation1Clicked = false;
                                        } else {
                                            itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_click_down);
                                            onRotation1Clicked = true;
                                        }
                                        if (strings.get(5).equals("0")) {
                                            itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_bg);
                                            onRotation2Clicked = false;
                                        } else {
                                            itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_click_down);
                                            onRotation2Clicked = true;
                                        }
                                        if (strings.get(6).equals("0")) {
                                            itemFragment1.getOpen().setBackgroundResource(R.drawable.btn_bg);
                                            onOpenClicked = false;
                                        } else {
                                            itemFragment1.getOpen().setBackgroundResource(R.drawable.btn_click_down);
                                            onOpenClicked = true;
                                        }
                                        if (strings.get(7).equals("0")) {
                                            itemFragment1.getClose().setBackgroundResource(R.drawable.btn_bg);
                                            onCloseClicked = false;
                                        } else {
                                            itemFragment1.getClose().setBackgroundResource(R.drawable.btn_click_down);
                                            onCloseClicked = true;
                                        }
                                        sendCommand(m58s3);
                                        isSecondCheckBtn = true;
                                    }
                                });

                            }
                            if (isSecondCheckBtn) {
                                isSecondCheckBtn = false;
                                String format = String.format("%1s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format2--" + format);
                                Log.i("wmk", "--读按钮2--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序2--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment2.getDown_pulse_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onDown_pulse_btnClicked = false;
                                        } else {
                                            itemFragment2.getDown_pulse_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onDown_pulse_btnClicked = true;
                                        }
                                        sendCommand(m100s3);
                                        isThirdCheckBtn = true;
                                    }
                                });
                            }
                            if (isThirdCheckBtn) {
                                isThirdCheckBtn = false;
                                String format = String.format("%8s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format3--" + format);
                                Log.i("wmk", "--读按钮3--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序3--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            verticalBtn.setBackgroundResource(R.drawable.btn_bg);
                                            onVerticalBtnClicked = false;
                                        } else {
                                            verticalBtn.setBackgroundResource(R.drawable.btn_click_down);
                                            onVerticalBtnClicked = true;
                                        }
                                        if (strings.get(1).equals("0")) {
                                            horizontalBtn.setBackgroundResource(R.drawable.btn_bg);
                                            onHorizontalBtnClicked = false;
                                        } else {
                                            horizontalBtn.setBackgroundResource(R.drawable.btn_click_down);
                                            onHorizontalBtnClicked = true;
                                        }
                                        if (strings.get(2).equals("0")) {
                                            itemFragment2.getVertical_rotation_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onVertical_rotation_btnClicked = false;
                                        } else {
                                            itemFragment2.getVertical_rotation_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onVertical_rotation_btnClicked = true;
                                        }
                                        if (strings.get(3).equals("0")) {
                                            itemFragment2.getVertical_machine_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onVertical_machine_btnClicked = false;
                                        } else {
                                            itemFragment2.getVertical_machine_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onVertical_machine_btnClicked = true;
                                        }
                                        if (strings.get(4).equals("0")) {
                                            itemFragment2.getVertical_hot_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onVertical_hot_btnClicked = false;
                                        } else {
                                            itemFragment2.getVertical_hot_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onVertical_hot_btnClicked = true;
                                        }
                                        if (strings.get(5).equals("0")) {
                                            itemFragment2.getVertical_out_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onVertical_out_btnClicked = false;
                                        } else {
                                            itemFragment2.getVertical_out_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onVertical_out_btnClicked = true;
                                        }
                                        if (strings.get(6).equals("0")) {
                                            itemFragment2.getVertical_get_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onVertical_get_btnClicked = false;
                                        } else {
                                            itemFragment2.getVertical_get_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onVertical_get_btnClicked = true;
                                        }
                                        if (strings.get(7).equals("0")) {
                                            itemFragment2.getHorizontal_entrepot_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onHorizontal_entrepot_btnClicked = false;
                                        } else {
                                            itemFragment2.getHorizontal_entrepot_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onHorizontal_entrepot_btnClicked = true;
                                        }
                                        sendCommand(m108s3);
                                        isForthCheckBtn = true;
                                    }
                                });
                            }
                            if (isForthCheckBtn) {
                                isForthCheckBtn = false;
                                String format = String.format("%4s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format4--" + format);
                                Log.i("wmk", "--读按钮4--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序4--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment2.getHorizontal_machine_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onHorizontal_machine_btnClicked = false;
                                        } else {
                                            itemFragment2.getHorizontal_machine_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onHorizontal_machine_btnClicked = true;
                                        }
                                        if (strings.get(1).equals("0")) {
                                            itemFragment2.getHorizontal_hot_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onHorizontal_hot_btnClicked = false;
                                        } else {
                                            itemFragment2.getHorizontal_hot_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onHorizontal_hot_btnClicked = true;
                                        }
                                        if (strings.get(2).equals("0")) {
                                            itemFragment2.getHorizontal_out_btn().setBackgroundResource(R.drawable.btn_bg);
                                            onHorizontal_out_btnClicked = false;
                                        } else {
                                            itemFragment2.getHorizontal_out_btn().setBackgroundResource(R.drawable.btn_click_down);
                                            onHorizontal_out_btnClicked = true;
                                        }
                                        if (strings.get(3).equals("0")) {
                                            itemFragment2.getBelt_out().setBackgroundResource(R.drawable.btn_bg);
                                            onBelt_outClick = false;
                                        } else {
                                            itemFragment2.getBelt_out().setBackgroundResource(R.drawable.btn_click_down);
                                            onBelt_outClick = true;
                                        }
                                        sendCommand(m112s3);
                                        isFifthCheckBtn = true;
                                    }
                                });
                            }

                            if (isFifthCheckBtn) {
                                isFifthCheckBtn = false;
                                String format = String.format("%8s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format5--" + format);
                                Log.i("wmk", "--读按钮5--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序5--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment2.getHandspike_out().setBackgroundResource(R.drawable.btn_bg);
                                            onHandspike_outClicked = false;
                                        } else {
                                            itemFragment2.getHandspike_out().setBackgroundResource(R.drawable.btn_click_down);
                                            onHandspike_outClicked = true;
                                        }
                                        if (strings.get(1).equals("0")) {
                                            itemFragment2.getHandspike_in().setBackgroundResource(R.drawable.btn_bg);
                                            onHandspike_inClicked = false;
                                        } else {
                                            itemFragment2.getHandspike_in().setBackgroundResource(R.drawable.btn_click_down);
                                            onHandspike_inClicked = true;
                                        }
                                        if (strings.get(2).equals("0")) {
                                            itemFragment2.getHot_out().setBackgroundResource(R.drawable.btn_bg);
                                            onHot_outClick = false;
                                        } else {
                                            itemFragment2.getHot_out().setBackgroundResource(R.drawable.btn_click_down);
                                            onHot_outClick = true;
                                        }
                                        if (strings.get(3).equals("0")) {
                                            itemFragment2.getHot_in().setBackgroundResource(R.drawable.btn_bg);
                                            onHot_inClicked = false;
                                        } else {
                                            itemFragment2.getHot_in().setBackgroundResource(R.drawable.btn_click_down);
                                            onHot_inClicked = true;
                                        }
                                        if (strings.get(4).equals("0")) {
                                            itemFragment2.getMachine_out().setBackgroundResource(R.drawable.btn_bg);
                                            onMachine_outClicked = false;
                                        } else {
                                            itemFragment2.getMachine_out().setBackgroundResource(R.drawable.btn_click_down);
                                            onMachine_outClicked = true;
                                        }
                                        if (strings.get(5).equals("0")) {
                                            itemFragment2.getMachine_in().setBackgroundResource(R.drawable.btn_bg);
                                            onMachine_inClicked = false;
                                        } else {
                                            itemFragment2.getMachine_in().setBackgroundResource(R.drawable.btn_click_down);
                                            onMachine_inClicked = true;
                                        }
                                        if (strings.get(6).equals("0")) {
                                            itemFragment3.getMachine_switch().setBackgroundResource(R.drawable.btn_bg);
                                            onMachine_switchClicked = false;
                                        } else {
                                            itemFragment3.getMachine_switch().setBackgroundResource(R.drawable.btn_click_down);
                                            onMachine_switchClicked = true;
                                        }
                                        if (strings.get(7).equals("0")) {
                                            itemFragment3.getMachine_power().setBackgroundResource(R.drawable.btn_bg);
                                            onMachine_powerClicked = false;
                                        } else {
                                            itemFragment3.getMachine_power().setBackgroundResource(R.drawable.btn_click_down);
                                            onMachine_powerClicked = true;
                                        }
                                        sendCommand(m200s3);
                                        isSixthCheckBtn = true;
                                    }
                                });
                            }
                            if (isSixthCheckBtn) {
                                isSixthCheckBtn = false;
                                String format = String.format("%2s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format6--" + format);
                                Log.i("wmk", "--读按钮6--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序6--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment2.getOut_in().setBackgroundResource(R.drawable.btn_bg);
                                            onOut_inClick = false;
                                        } else {
                                            itemFragment2.getOut_in().setBackgroundResource(R.drawable.btn_click_down);
                                            onOut_inClick = true;
                                        }
                                        if (strings.get(1).equals("0")) {
                                            itemFragment2.getOut_out().setBackgroundResource(R.drawable.btn_bg);
                                            onOut_outClicked = false;
                                        } else {
                                            itemFragment2.getOut_out().setBackgroundResource(R.drawable.btn_click_down);
                                            onOut_outClicked = true;
                                        }
                                        sendCommand(m121s3);
                                        isSeventhCheckBtn = true;
                                    }
                                });
                            }

                            if (isSeventhCheckBtn) {
                                isSeventhCheckBtn = false;
                                String format = String.format("%2s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format7--" + format);
                                Log.i("wmk", "--读按钮7--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序7--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment3.getHot().setBackgroundResource(R.drawable.btn_bg);
                                            onHotClicked = false;
                                        } else {
                                            itemFragment3.getHot().setBackgroundResource(R.drawable.btn_click_down);
                                            onHotClicked = true;
                                        }
                                        if (strings.get(1).equals("0")) {
                                            itemFragment3.getFan().setBackgroundResource(R.drawable.btn_bg);
                                            onFanClicked = false;
                                        } else {
                                            itemFragment3.getFan().setBackgroundResource(R.drawable.btn_click_down);
                                            onFanClicked = true;
                                        }
                                        sendCommand(m10s3);
                                        isEighthCheckBtn = true;
                                    }
                                });
                            }

                            if (isEighthCheckBtn) {
                                isEighthCheckBtn = false;
                                String format = String.format("%1s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format8--" + format);
                                Log.i("wmk", "--读按钮8--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序8--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment3.getMachine_reset().setBackgroundResource(R.drawable.btn_bg);
                                            onMachine_resetClicked = false;
                                        } else {
                                            itemFragment3.getMachine_reset().setBackgroundResource(R.drawable.btn_click_down);
                                            onMachine_resetClicked = true;
                                        }
                                        sendCommand(m130s3);
                                        isNinthCheckBtn = true;
                                    }
                                });
                            }
                            if (isNinthCheckBtn) {
                                String format = String.format("%1s", Integer.toBinaryString(bytes[3] & 0xFF)).replace(' ', '0');
                                Log.i("wmk", "--读按钮format9--" + format);
                                Log.i("wmk", "--读按钮9--" + Utils.bytesToHexString(bytes));
                                final ArrayList<String> strings = new ArrayList<>();
                                for (int i = 0; i < format.length(); i++) {
                                    String substring = format.substring(i, i + 1);
                                    strings.add(substring);
                                }
                                Collections.reverse(strings);
                                Log.i("wmk", "--倒序9--" + strings.toString());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (strings.get(0).equals("0")) {
                                            itemFragment3.getBtn_reset().setBackgroundResource(R.drawable.btn_bg);
                                            onBtn_resetClicked = false;
                                        } else {
                                            itemFragment3.getBtn_reset().setBackgroundResource(R.drawable.btn_click_down);
                                            onBtn_resetClicked = true;
                                        }
                                    }
                                });
                            }


                            if (bytes[1] == (byte) 0x05) {//对方收到写线圈，然后我要去读
                                switch (BtnTag) {
                                    case "M50":
                                        if (m50s2 == null) {
                                            m50s2 = Utils.getCommandBytes("M50", readPreByte, readBackByte);
                                        }
                                        sendCommand(m50s2);
                                        //  commandBytes = m50s2;
                                        Log.i("wmk", "读M50");
                                        break;
                                    case "M51":
                                        if (m51s2 == null) {
                                            m51s2 = Utils.getCommandBytes("M51", readPreByte, readBackByte);
                                        }
                                        sendCommand(m51s2);
                                        //  commandBytes = m51s2;
                                        Log.i("wmk", "读M51");
                                        break;
                                    case "M52":
                                        if (m52s2 == null) {
                                            m52s2 = Utils.getCommandBytes("M52", readPreByte, readBackByte);
                                        }
                                        sendCommand(m52s2);
                                        Log.i("wmk", "读M52");
                                        //  commandBytes = m52s2;
                                        break;
                                    case "M53":
                                        if (m53s2 == null) {
                                            m53s2 = Utils.getCommandBytes("M53", readPreByte, readBackByte);
                                        }
                                        sendCommand(m53s2);
                                        Log.i("wmk", "读M53");
                                        //  commandBytes = m53s2;
                                        break;
                                    case "M54":
                                        if (m54s2 == null) {
                                            m54s2 = Utils.getCommandBytes("M54", readPreByte, readBackByte);
                                        }
                                        sendCommand(m54s2);
                                        //  commandBytes = m54s2;
                                        Log.i("wmk", "读M54");
                                        break;
                                    case "M55":
                                        if (m55s2 == null) {
                                            m55s2 = Utils.getCommandBytes("M55", readPreByte, readBackByte);
                                        }
                                        sendCommand(m55s2);
                                        //   commandBytes = m55s2;
                                        Log.i("wmk", "读M55");
                                        break;
                                    case "M56":
                                        if (m56s2 == null) {
                                            m56s2 = Utils.getCommandBytes("M56", readPreByte, readBackByte);
                                        }
                                        sendCommand(m56s2);
                                        Log.i("wmk", "读M56");
                                        break;
                                    case "M57":
                                        if (m57s2 == null) {
                                            m57s2 = Utils.getCommandBytes("M57", readPreByte, readBackByte);
                                        }
                                        sendCommand(m57s2);
                                        Log.i("wmk", "读M57");
                                        break;
                                    case "M107":
                                        if (m107s2 == null) {
                                            m107s2 = Utils.getCommandBytes("M107", readPreByte, readBackByte);
                                        }
                                        sendCommand(m107s2);
                                        Log.i("wmk", "读M107");
                                        break;
                                    case "M116":
                                        if (m116s2 == null) {
                                            m116s2 = Utils.getCommandBytes("M116", readPreByte, readBackByte);
                                        }
                                        sendCommand(m116s2);
                                        Log.i("wmk", "读M116");
                                        break;
                                    case "M117":
                                        if (m117s2 == null) {
                                            m117s2 = Utils.getCommandBytes("M117", readPreByte, readBackByte);
                                        }
                                        sendCommand(m117s2);
                                        Log.i("wmk", "读M117");
                                        break;
                                    case "M114":
                                        if (m114s2 == null) {
                                            m114s2 = Utils.getCommandBytes("M114", readPreByte, readBackByte);
                                        }
                                        sendCommand(m114s2);
                                        Log.i("wmk", "读M114");
                                        break;
                                    case "M115":
                                        if (m115s2 == null) {
                                            m115s2 = Utils.getCommandBytes("M115", readPreByte, readBackByte);
                                        }
                                        sendCommand(m115s2);
                                        Log.i("wmk", "读M115");
                                        break;
                                    case "M112":
                                        if (m112s2 == null) {
                                            m112s2 = Utils.getCommandBytes("M112", readPreByte, readBackByte);
                                        }
                                        sendCommand(m112s2);
                                        Log.i("wmk", "读M112");
                                        break;
                                    case "M113":
                                        if (m113s2 == null) {
                                            m113s2 = Utils.getCommandBytes("M113", readPreByte, readBackByte);
                                        }
                                        sendCommand(m113s2);
                                        Log.i("wmk", "读M113");
                                        break;
                                    case "M201":
                                        if (m201s2 == null) {
                                            m201s2 = Utils.getCommandBytes("M201", readPreByte, readBackByte);
                                        }
                                        sendCommand(m201s2);
                                        Log.i("wmk", "读M201");
                                        break;
                                    case "M200":
                                        if (m200s2 == null) {
                                            m200s2 = Utils.getCommandBytes("M200", readPreByte, readBackByte);
                                        }
                                        sendCommand(m200s2);
                                        Log.i("wmk", "读M200");
                                        break;
                                    case "M111":
                                        if (m111s2 == null) {
                                            m111s2 = Utils.getCommandBytes("M111", readPreByte, readBackByte);
                                        }
                                        sendCommand(m111s2);
                                        Log.i("wmk", "读M111");
                                        break;
                                    case "M102":
                                        if (m102s2 == null) {
                                            m102s2 = Utils.getCommandBytes("M102", readPreByte, readBackByte);
                                        }
                                        sendCommand(m102s2);
                                        Log.i("wmk", "读M102");
                                        break;
                                    case "M103":
                                        if (m103s2 == null) {
                                            m103s2 = Utils.getCommandBytes("M103", readPreByte, readBackByte);
                                        }
                                        sendCommand(m103s2);
                                        Log.i("wmk", "读M103");
                                        break;
                                    case "M104":
                                        if (m104s2 == null) {
                                            m104s2 = Utils.getCommandBytes("M104", readPreByte, readBackByte);
                                        }
                                        sendCommand(m104s2);
                                        Log.i("wmk", "读M104");
                                        break;
                                    case "M105":
                                        if (m105s2 == null) {
                                            m105s2 = Utils.getCommandBytes("M105", readPreByte, readBackByte);
                                        }
                                        sendCommand(m105s2);
                                        Log.i("wmk", "读M105");
                                        break;
                                    case "M106":
                                        if (m106s2 == null) {
                                            m106s2 = Utils.getCommandBytes("M106", readPreByte, readBackByte);
                                        }
                                        sendCommand(m106s2);
                                        Log.i("wmk", "读M106");
                                        break;
                                    case "M108":
                                        if (m108s2 == null) {
                                            m108s2 = Utils.getCommandBytes("M108", readPreByte, readBackByte);
                                        }
                                        sendCommand(m108s2);
                                        Log.i("wmk", "读M108");
                                        break;
                                    case "M109":
                                        if (m109s2 == null) {
                                            m109s2 = Utils.getCommandBytes("M109", readPreByte, readBackByte);
                                        }
                                        sendCommand(m109s2);
                                        Log.i("wmk", "读M109");
                                        break;
                                    case "M110":
                                        if (m110s2 == null) {
                                            m110s2 = Utils.getCommandBytes("M110", readPreByte, readBackByte);
                                        }
                                        sendCommand(m110s2);
                                        Log.i("wmk", "读M110");
                                        break;
                                    case "M100":
                                        if (m100s2 == null) {
                                            m100s2 = Utils.getCommandBytes("M100", readPreByte, readBackByte);
                                        }
                                        sendCommand(m100s2);
                                        Log.i("wmk", "读M100");
                                        break;
                                    case "M101":
                                        if (m101s2 == null) {
                                            m101s2 = Utils.getCommandBytes("M101", readPreByte, readBackByte);
                                        }
                                        sendCommand(m101s2);
                                        Log.i("wmk", "读M101");
                                        break;
                                    case "M119":
                                        if (m119s2 == null) {
                                            m119s2 = Utils.getCommandBytes("M119", readPreByte, readBackByte);
                                        }
                                        sendCommand(m119s2);
                                        Log.i("wmk", "读M119");
                                        break;
                                    case "M118":
                                        if (m118s2 == null) {
                                            m118s2 = Utils.getCommandBytes("M118", readPreByte, readBackByte);
                                        }
                                        sendCommand(m118s2);
                                        Log.i("wmk", "读M118");
                                        break;
                                    case "M121":
                                        if (m121s2 == null) {
                                            m121s2 = Utils.getCommandBytes("M121", readPreByte, readBackByte);
                                        }
                                        sendCommand(m121s2);
                                        Log.i("wmk", "读M121");
                                        break;
                                    case "M122":
                                        if (m122s2 == null) {
                                            m122s2 = Utils.getCommandBytes("M122", readPreByte, readBackByte);
                                        }
                                        sendCommand(m122s2);
                                        Log.i("wmk", "读M122");
                                        break;
                                    case "M10":
                                        if (m10s2 == null) {
                                            m10s2 = Utils.getCommandBytes("M10", readPreByte, readBackByte);
                                        }
                                        sendCommand(m10s2);
                                        Log.i("wmk", "读M10");
                                        break;
                                    case "M130":
                                        if (m130s2 == null) {
                                            m130s2 = Utils.getCommandBytes("M130", readPreByte, readBackByte);
                                        }
                                        sendCommand(m130s2);
                                        Log.i("wmk", "读M130");
                                        break;
                                    case "M58":
                                        if (m58s2 == null) {
                                            m58s2 = Utils.getCommandBytes("M58", readPreByte, readBackByte);
                                        }
                                        sendCommand(m58s2);
                                        Log.i("wmk", "读M58");
                                        break;
                                }
                            }

                            if (!isFirstCheckBtn && !isSecondCheckBtn && !isThirdCheckBtn && !isForthCheckBtn &&
                                    !isFifthCheckBtn && !isSixthCheckBtn &&
                                    !isSeventhCheckBtn && !isEighthCheckBtn && !isNinthCheckBtn) {//不是第一次检查按钮的
                                if (BtnTag == null) continue;
                                if (bytes[0] == (byte) 0x01 && bytes[1] == (byte) 0x01 && bytes[2] == (byte) 0x01
                                        && bytes[3] == (byte) 0x00) {
                                    Log.i("wmk", "--读线圈处理00--" + Utils.bytesToHexString(bytes));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            switch (BtnTag) {
                                                case "M58":
                                                    itemFragment2.getDown_pulse_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--下放脉冲量结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M122":
                                                    itemFragment3.getFan().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--烤箱风扇结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M10":
                                                    itemFragment3.getMachine_reset().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--机械复位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M130":
                                                    itemFragment3.getBtn_reset().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--按钮复位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M121":
                                                    itemFragment3.getHot().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--烤箱发热丝结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M118":
                                                    itemFragment3.getMachine_switch().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--打印机开关结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M119":
                                                    itemFragment3.getMachine_power().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--打印机电源结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M100":
                                                    verticalBtn.setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上下回原点结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M101":
                                                    horizontalBtn.setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--横向回原点结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M110":
                                                    itemFragment2.getHorizontal_out_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--横向出口结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M109":
                                                    itemFragment2.getHorizontal_hot_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--横向烤箱结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M108":
                                                    itemFragment2.getHorizontal_machine_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--横向打印机结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M106":
                                                    itemFragment2.getVertical_get_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上下取料结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M105":
                                                    itemFragment2.getVertical_out_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上下出口位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M104":
                                                    itemFragment2.getVertical_hot_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上下烤箱位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M103":
                                                    itemFragment2.getVertical_machine_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上下打印机位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M102":
                                                    itemFragment2.getVertical_rotation_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上下旋转位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M111":
                                                    itemFragment2.getBelt_out().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--皮带出货结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M201":
                                                    itemFragment2.getOut_out().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--出货口开结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M200":
                                                    itemFragment2.getOut_in().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--出货口关结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M112":
                                                    itemFragment2.getHandspike_out().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--推杆伸出结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M113":
                                                    itemFragment2.getHandspike_in().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--推杆缩回结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M114":
                                                    itemFragment2.getHot_out().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--烤箱伸出结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M115":
                                                    itemFragment2.getHot_in().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--烤箱缩回结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M116":
                                                    itemFragment2.getMachine_out().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--打印机伸出结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M117":
                                                    itemFragment2.getMachine_in().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--打印机缩回结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M107":
                                                    itemFragment2.getHorizontal_entrepot_btn().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--横向料仓位结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M50":
                                                    itemFragment1.getUp().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--上移结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M51":
                                                    itemFragment1.getDown().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--下移结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M52":
                                                    itemFragment1.getForward().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--前进结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M53":
                                                    itemFragment1.getBack().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--后退结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M54":
                                                    itemFragment1.getRotation1().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--出口面结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M55":
                                                    itemFragment1.getRotation2().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--打印机面结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M56":
                                                    itemFragment1.getOpen().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--张开结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                                case "M57":
                                                    itemFragment1.getClose().setBackgroundResource(R.drawable.btn_bg);
                                                    Log.i("wmk", "--夹紧结束--" + Utils.bytesToHexString(bytes));
                                                    break;
                                            }
                                        }
                                    });

                                }

                                if (bytes[0] == (byte) 0x01 && bytes[1] == (byte) 0x01 && bytes[2] == (byte) 0x01
                                        && bytes[3] == (byte) 0x01) {
                                    Log.i("wmk", "--读线圈处理01--" + Utils.bytesToHexString(bytes));
                                    switch (BtnTag) {
                                        case "M58":
                                            sendCommand(m58s2);
                                            Log.i("wmk", "--继续下放脉冲量--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M122":
                                            sendCommand(m122s2);
                                            Log.i("wmk", "--继续读烤箱风扇--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M10":
                                            sendCommand(m10s2);
                                            Log.i("wmk", "--继续读机械复位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M130":
                                            sendCommand(m130s2);
                                            Log.i("wmk", "--继续读按钮复位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M121":
                                            sendCommand(m121s2);
                                            Log.i("wmk", "--继续读烤箱发热丝--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M118":
                                            sendCommand(m118s2);
                                            Log.i("wmk", "--继续读打印机开关--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M119":
                                            sendCommand(m119s2);
                                            Log.i("wmk", "--继续读打印机电源--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M100":
                                            sendCommand(m100s2);
                                            Log.i("wmk", "--继续读上下回原点--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M101":
                                            sendCommand(m101s2);
                                            Log.i("wmk", "--继续读横向回原点--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M110":
                                            sendCommand(m110s2);
                                            Log.i("wmk", "--继续读横向出口位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M109":
                                            sendCommand(m109s2);
                                            Log.i("wmk", "--继续读横向烤箱位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M108":
                                            sendCommand(m108s2);
                                            Log.i("wmk", "--继续读横向打印位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M106":
                                            sendCommand(m106s2);
                                            Log.i("wmk", "--继续读上下取料位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M105":
                                            sendCommand(m105s2);
                                            Log.i("wmk", "--继续读上下出口位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M104":
                                            sendCommand(m104s2);
                                            Log.i("wmk", "--继续读上下烤箱位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M103":
                                            sendCommand(m103s2);
                                            Log.i("wmk", "--继续读上下打印机位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M102":
                                            sendCommand(m102s2);
                                            Log.i("wmk", "--继续读上下旋转位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M111":
                                            sendCommand(m111s2);
                                            Log.i("wmk", "--继续读皮带出货--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M201":
                                            sendCommand(m201s2);
                                            Log.i("wmk", "--继续读出货口开--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M200":
                                            sendCommand(m200s2);
                                            Log.i("wmk", "--继续读出货口关--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M112":
                                            sendCommand(m112s2);
                                            Log.i("wmk", "--继续读推杆伸出--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M113":
                                            sendCommand(m113s2);
                                            Log.i("wmk", "--继续读推杆缩回--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M114":
                                            sendCommand(m114s2);
                                            Log.i("wmk", "--继续读烤箱伸出--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M115":
                                            sendCommand(m115s2);
                                            Log.i("wmk", "--继续读烤箱缩回--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M116":
                                            sendCommand(m116s2);
                                            Log.i("wmk", "--继续读打印机伸出--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M117":
                                            sendCommand(m117s2);
                                            Log.i("wmk", "--继续读打印机缩回--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M107":
                                            sendCommand(m107s2);
                                            Log.i("wmk", "--继续读横向料仓位--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M50":
                                            sendCommand(m50s2);
                                            Log.i("wmk", "--继续读上移--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M51":
                                            sendCommand(m51s2);
                                            Log.i("wmk", "--继续读下移--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M52":
                                            sendCommand(m52s2);
                                            Log.i("wmk", "--继续读前进--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M53":
                                            sendCommand(m53s2);
                                            Log.i("wmk", "--继续读后退--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M54":
                                            sendCommand(m54s2);
                                            //   commandBytes = m54s2;
                                            Log.i("wmk", "--继续读出口面--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M55":
                                            sendCommand(m55s2);

                                            Log.i("wmk", "--继续读打印机面--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M56":
                                            sendCommand(m56s2);
                                            Log.i("wmk", "--继续读张开--" + Utils.bytesToHexString(bytes));
                                            break;
                                        case "M57":
                                            sendCommand(m57s2);
                                            Log.i("wmk", "--继续读夹紧--" + Utils.bytesToHexString(bytes));
                                            break;
                                    }
                                }

                            }


                        }


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

    private boolean onVerticalBtnClicked;
    private boolean onHorizontalBtnClicked;

    @OnClick({R.id.back, R.id.verticalBtn, R.id.horizontalBtn,
            R.id.item1, R.id.item2, R.id.item3,
            R.id.commandTitle})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.verticalBtn://上下回原点
                if (!onVerticalBtnClicked) {
                    if (m100s == null) {
                        m100s = Utils.getCommandBytes("M100", writePreByte, writeBackByte);
                    }
                    sendCommand(m100s);
                    onVerticalBtnClicked = true;
                    verticalBtn.setBackgroundResource(R.drawable.btn_click_down);
                    BtnTag = "M100";
                } else {
                    if (m100s1 == null) {
                        m100s1 = Utils.getCommandBytes("M100", writePreByte, writeBackByte2);
                    }
                    sendCommand(m100s1);
                    onVerticalBtnClicked = false;
                    verticalBtn.setBackgroundResource(R.drawable.btn_bg);
                }

                break;
            case R.id.horizontalBtn://横向回原点

                if (!onHorizontalBtnClicked) {
                    if (m101s == null) {
                        m101s = Utils.getCommandBytes("M101", writePreByte, writeBackByte);
                    }
                    sendCommand(m101s);
                    onHorizontalBtnClicked = true;
                    horizontalBtn.setBackgroundResource(R.drawable.btn_click_down);
                    BtnTag = "M101";
                } else {
                    if (m101s1 == null) {
                        m101s1 = Utils.getCommandBytes("M101", writePreByte, writeBackByte2);
                    }
                    sendCommand(m101s1);
                    onHorizontalBtnClicked = false;
                    horizontalBtn.setBackgroundResource(R.drawable.btn_bg);
                }


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

        commandBytes = bytes;
        try {
            if (mOutputStream != null) {
                mOutputStream.write(Utils.calcCrc16(commandBytes));
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
