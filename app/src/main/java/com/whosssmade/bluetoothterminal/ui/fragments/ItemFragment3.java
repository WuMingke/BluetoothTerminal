package com.whosssmade.bluetoothterminal.ui.fragments;

import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract3;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter3;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;
import com.whosssmade.bluetoothterminal.utils.Utils;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;

public class ItemFragment3 extends BaseFragment<ItemPresenter3> implements ItemContract3.View {

    @BindView(R.id.machine_power)
    TextView machine_power;

    @BindView(R.id.machine_switch)
    TextView machine_switch;

    @BindView(R.id.hot)
    TextView hot;

    @BindView(R.id.fan)
    TextView fan;

    @BindView(R.id.machine_reset)
    TextView machine_reset;

    @BindView(R.id.btn_reset)
    TextView btn_reset;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_3;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.machine_power, R.id.machine_switch, R.id.hot, R.id.fan, R.id.machine_reset, R.id.btn_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.machine_power://打印机电源 M119
               // Utils.setTextClickDown();
                break;
            case R.id.machine_switch://打印机开关 M118
                break;
            case R.id.hot://烤箱发热丝  M121
                break;
            case R.id.fan://烤箱风扇 M112
                break;
            case R.id.machine_reset://机械复位 M10
                break;
            case R.id.btn_reset://按钮复位 M130
                break;
        }
    }

    @Subscriber(tag = Constants.DATA_BUTTON)
    public void setBtnState(EventBusMessage<String[]> message) {
        if (message.getT() != null) {
            String[] strings = message.getT();
         /*   machine_power.setBackgroundResource();//打印机电源
            machine_switch.setBackgroundResource();//打印机开关
            hot.setBackgroundResource();//烤箱发热丝
            fan.setBackgroundResource();//烤箱风扇
            machine_reset.setBackgroundResource();//机械复位
            btn_reset.setBackgroundResource();//按钮复位*/
        }
    }
}
