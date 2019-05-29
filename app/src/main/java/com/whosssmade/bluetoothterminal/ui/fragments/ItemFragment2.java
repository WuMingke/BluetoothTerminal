package com.whosssmade.bluetoothterminal.ui.fragments;

import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract2;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter2;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;

import org.simple.eventbus.Subscriber;

import java.util.IdentityHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ItemFragment2 extends BaseFragment<ItemPresenter2> implements ItemContract2.View {

    @BindView(R.id.vertical_rotation)
    TextView vertical_rotation;
    @BindView(R.id.vertical_machine)
    TextView vertical_machine;
    @BindView(R.id.vertical_hot)
    TextView vertical_hot;
    @BindView(R.id.vertical_out)
    TextView vertical_out;
    @BindView(R.id.vertical_get)
    TextView vertical_get;

    @BindView(R.id.horizontal_entrepot)
    TextView horizontal_entrepot;
    @BindView(R.id.horizontal_machine)
    TextView horizontal_machine;
    @BindView(R.id.horizontal_hot)
    TextView horizontal_hot;
    @BindView(R.id.horizontal_out)
    TextView horizontal_out;
    @BindView(R.id.down_pulse)
    TextView down_pulse;

    @BindView(R.id.vertical_rotation_btn)
    TextView vertical_rotation_btn;
    @BindView(R.id.vertical_machine_btn)
    TextView vertical_machine_btn;
    @BindView(R.id.vertical_hot_btn)
    TextView vertical_hot_btn;
    @BindView(R.id.vertical_out_btn)
    TextView vertical_out_btn;
    @BindView(R.id.vertical_get_btn)
    TextView vertical_get_btn;
    @BindView(R.id.horizontal_entrepot_btn)
    TextView horizontal_entrepot_btn;
    @BindView(R.id.horizontal_machine_btn)
    TextView horizontal_machine_btn;
    @BindView(R.id.horizontal_hot_btn)
    TextView horizontal_hot_btn;
    @BindView(R.id.horizontal_out_btn)
    TextView horizontal_out_btn;
    @BindView(R.id.down_pulse_btn)
    TextView down_pulse_btn;
    @BindView(R.id.machine_out)
    TextView machine_out;
    @BindView(R.id.machine_in)
    TextView machine_in;
    @BindView(R.id.hot_out)
    TextView hot_out;
    @BindView(R.id.hot_in)
    TextView hot_in;
    @BindView(R.id.handspike_out)
    TextView handspike_out;
    @BindView(R.id.handspike_in)
    TextView handspike_in;
    @BindView(R.id.out_out)
    TextView out_out;
    @BindView(R.id.out_in)
    TextView out_in;
    @BindView(R.id.belt_out)
    TextView belt_out;


    private SetValueDialog setValueDialog;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_2;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.vertical_rotation, R.id.vertical_machine, R.id.vertical_hot, R.id.vertical_out, R.id.vertical_get,
            R.id.horizontal_entrepot, R.id.horizontal_machine, R.id.horizontal_hot, R.id.horizontal_out, R.id.down_pulse,
            R.id.vertical_rotation_btn, R.id.vertical_machine_btn, R.id.vertical_hot_btn, R.id.vertical_out_btn, R.id.vertical_get_btn,
            R.id.horizontal_entrepot_btn, R.id.horizontal_machine_btn, R.id.horizontal_hot_btn, R.id.horizontal_out_btn, R.id.down_pulse_btn,
            R.id.machine_out, R.id.machine_in,
            R.id.hot_out, R.id.hot_in,
            R.id.handspike_out, R.id.handspike_in,
            R.id.out_out, R.id.out_in,
            R.id.belt_out,

    })
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.vertical_rotation_btn://上下旋转位 M102

                break;
            case R.id.vertical_rotation:
                dialogSetTextView(vertical_rotation);
                break;
            case R.id.vertical_machine_btn://上下打印机位 M103

                break;
            case R.id.vertical_machine:
                dialogSetTextView(vertical_machine);
                break;
            case R.id.vertical_hot_btn://上下烤箱位 M104

                break;
            case R.id.vertical_hot:
                dialogSetTextView(vertical_hot);
                break;
            case R.id.vertical_out_btn://上下出口位 M105

                break;
            case R.id.vertical_out:
                dialogSetTextView(vertical_out);
                break;
            case R.id.vertical_get_btn://上下取料 M106

                break;
            case R.id.vertical_get:
                dialogSetTextView(vertical_get);
                break;
            case R.id.horizontal_entrepot_btn://横向料仓位 M107

                break;
            case R.id.horizontal_entrepot:
                dialogSetTextView(horizontal_entrepot);
                break;
            case R.id.horizontal_machine_btn://横向打印机位 M108

                break;
            case R.id.horizontal_machine:
                dialogSetTextView(horizontal_machine);
                break;
            case R.id.horizontal_hot_btn://横向烤箱位 M109

                break;
            case R.id.horizontal_hot:
                dialogSetTextView(horizontal_hot);
                break;
            case R.id.horizontal_out_btn://横向出口位 M110

                break;
            case R.id.horizontal_out:
                dialogSetTextView(horizontal_out);
                break;
            case R.id.down_pulse_btn://下放脉冲位    无数据

                break;
            case R.id.down_pulse:
                dialogSetTextView(down_pulse);
                break;
            case R.id.machine_out://打印机伸出 M116

                break;
            case R.id.machine_in://打印机缩回 M117

                break;
            case R.id.hot_out://烤箱伸出 M114

                break;
            case R.id.hot_in://烤箱缩回 M113

                break;
            case R.id.handspike_out://推杆伸出 M112

                break;
            case R.id.handspike_in://推杆缩回 M113

                break;
            case R.id.out_out://出货口开 M201

                break;
            case R.id.out_in://出货口关  M200

                break;
            case R.id.belt_out://皮带出货 M111

                break;

        }
    }

    private void dialogSetTextView(TextView textView) {
        if (setValueDialog == null) {
            setValueDialog = new SetValueDialog(getContext());
        }
        setValueDialog.setTextView(textView);
        setValueDialog.show();
    }

    @Subscriber(tag = Constants.DATA_FRAGMENT_2)
    public void setData(EventBusMessage<String[]> message) {
        if (message.getT() != null) {// D4100---读了20个
            String[] strings = message.getT();
            vertical_rotation.setText(strings[3]);//上下旋转位 D4100
            vertical_machine.setText(strings[5]);//上下打印机位 D4102
            vertical_hot.setText(strings[7]);//上下烤箱位 D4104
            vertical_out.setText(strings[9]);//上下出口位 D4106
            vertical_get.setText(strings[19]);//上下取料位 D4116
            horizontal_entrepot.setText(strings[11]);//横向料仓位 D4108
            horizontal_machine.setText(strings[13]);//横向打印机位 D4110
            horizontal_hot.setText(strings[15]);//横向烤箱位 D4112
            horizontal_out.setText(strings[17]);//横向出口位 D4114
            down_pulse.setText(strings[21]);//下放脉冲位 D4118
        }
    }

    @Subscriber(tag = Constants.DATA_BUTTON)
    public void setBtnState(EventBusMessage<String[]> message) {
        if (message.getT() != null) {
            String[] strings = message.getT();
           /* vertical_rotation_btn.setBackgroundResource(strings[]);//上下旋转位
            vertical_machine_btn.setBackgroundResource();//上下打印机位
            vertical_hot_btn.setBackgroundResource();//上下烤箱位
            vertical_out_btn.setBackgroundResource();//上下出口位置
            vertical_get_btn.setBackgroundResource();//上下取料位置
            horizontal_entrepot_btn.setBackgroundResource();//横向仓料位
            horizontal_machine_btn.setBackgroundResource();//横向打印机位
            horizontal_hot_btn.setBackgroundResource();//横向烤箱位
            horizontal_out_btn.setBackgroundResource();//横向出口位
            down_pulse_btn.setBackgroundResource();//下放脉冲量
            machine_out.setBackgroundResource();//打印机伸出
            machine_in.setBackgroundResource();//打印机缩回
            hot_out.setBackgroundResource();//烤箱伸出
            hot_in.setBackgroundResource();//烤箱缩回
            handspike_out.setBackgroundResource();//推杆伸出
            handspike_in.setBackgroundResource();//推杆缩回
            out_out.setBackgroundResource();//出货口开
            out_in.setBackgroundResource();//出货口关
            belt_out.setBackgroundResource();//皮带出货*/
        }
    }
}
