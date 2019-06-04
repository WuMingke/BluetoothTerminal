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

    public TextView getVertical_rotation() {
        return vertical_rotation;
    }

    public TextView getVertical_machine() {
        return vertical_machine;
    }

    public TextView getVertical_hot() {
        return vertical_hot;
    }

    public TextView getVertical_out() {
        return vertical_out;
    }

    public TextView getVertical_get() {
        return vertical_get;
    }

    public TextView getHorizontal_entrepot() {
        return horizontal_entrepot;
    }

    public TextView getHorizontal_machine() {
        return horizontal_machine;
    }

    public TextView getHorizontal_hot() {
        return horizontal_hot;
    }

    public TextView getHorizontal_out() {
        return horizontal_out;
    }

    public TextView getDown_pulse() {
        return down_pulse;
    }

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

    public TextView getVertical_rotation_btn() {
        return vertical_rotation_btn;
    }

    public TextView getVertical_machine_btn() {
        return vertical_machine_btn;
    }

    public TextView getVertical_hot_btn() {
        return vertical_hot_btn;
    }

    public TextView getVertical_out_btn() {
        return vertical_out_btn;
    }

    public TextView getVertical_get_btn() {
        return vertical_get_btn;
    }

    public TextView getHorizontal_entrepot_btn() {
        return horizontal_entrepot_btn;
    }

    public TextView getHorizontal_machine_btn() {
        return horizontal_machine_btn;
    }

    public TextView getHorizontal_hot_btn() {
        return horizontal_hot_btn;
    }

    public TextView getHorizontal_out_btn() {
        return horizontal_out_btn;
    }

    public TextView getDown_pulse_btn() {
        return down_pulse_btn;
    }

    public TextView getMachine_out() {
        return machine_out;
    }

    public TextView getMachine_in() {
        return machine_in;
    }

    public TextView getHot_out() {
        return hot_out;
    }

    public TextView getHot_in() {
        return hot_in;
    }

    public TextView getHandspike_out() {
        return handspike_out;
    }

    public TextView getHandspike_in() {
        return handspike_in;
    }

    public TextView getOut_out() {
        return out_out;
    }

    public TextView getOut_in() {
        return out_in;
    }

    public TextView getBelt_out() {
        return belt_out;
    }

    public interface OnFragment2BtnClick {
        void onVertical_rotation_btnClick();

        void onVertical_machine_btnClick();

        void onVertical_hot_btnClick();

        void onVertical_out_btnClick();

        void onVertical_get_btnClick();

        void onHorizontal_entrepot_btnClick();

        void onHorizontal_machine_btnClick();

        void onHorizontal_hot_btnClick();

        void onHorizontal_out_btnClick();

        void onDown_pulse_btnClick();

        void onMachine_outClick();

        void onMachine_inClick();

        void onHot_outClick();

        void onHot_inClick();

        void onHandspike_outClick();

        void onHandspike_inClick();

        void onOut_outClick();

        void onOut_inClick();

        void onBelt_outClick();
    }

    private OnFragment2BtnClick onFragment2BtnClick;

    public void setOnFragment2BtnClick(OnFragment2BtnClick onFragment2BtnClick) {
        this.onFragment2BtnClick = onFragment2BtnClick;
    }

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
                onFragment2BtnClick.onVertical_rotation_btnClick();
                break;
            case R.id.vertical_rotation:
                dialogSetTextView(vertical_rotation);
                break;
            case R.id.vertical_machine_btn://上下打印机位 M103
                onFragment2BtnClick.onVertical_machine_btnClick();
                break;
            case R.id.vertical_machine:
                dialogSetTextView(vertical_machine);
                break;
            case R.id.vertical_hot_btn://上下烤箱位 M104
                onFragment2BtnClick.onVertical_hot_btnClick();
                break;
            case R.id.vertical_hot:
                dialogSetTextView(vertical_hot);
                break;
            case R.id.vertical_out_btn://上下出口位 M105
                onFragment2BtnClick.onVertical_out_btnClick();
                break;
            case R.id.vertical_out:
                dialogSetTextView(vertical_out);
                break;
            case R.id.vertical_get_btn://上下取料 M106
                onFragment2BtnClick.onVertical_get_btnClick();
                break;
            case R.id.vertical_get:
                dialogSetTextView(vertical_get);
                break;
            case R.id.horizontal_entrepot_btn://横向料仓位 M107
                onFragment2BtnClick.onHorizontal_entrepot_btnClick();
                break;
            case R.id.horizontal_entrepot:
                dialogSetTextView(horizontal_entrepot);
                break;
            case R.id.horizontal_machine_btn://横向打印机位 M108
                onFragment2BtnClick.onHorizontal_machine_btnClick();
                break;
            case R.id.horizontal_machine:
                dialogSetTextView(horizontal_machine);
                break;
            case R.id.horizontal_hot_btn://横向烤箱位 M109
                onFragment2BtnClick.onHorizontal_hot_btnClick();
                break;
            case R.id.horizontal_hot:
                dialogSetTextView(horizontal_hot);
                break;
            case R.id.horizontal_out_btn://横向出口位 M110
                onFragment2BtnClick.onHorizontal_out_btnClick();
                break;
            case R.id.horizontal_out:
                dialogSetTextView(horizontal_out);
                break;
            case R.id.down_pulse_btn://下放脉冲位    M58
                onFragment2BtnClick.onDown_pulse_btnClick();
                break;
            case R.id.down_pulse:
                dialogSetTextView(down_pulse);
                break;
            case R.id.machine_out://打印机伸出 M116
                onFragment2BtnClick.onMachine_outClick();
                break;
            case R.id.machine_in://打印机缩回 M117
                onFragment2BtnClick.onMachine_inClick();
                break;
            case R.id.hot_out://烤箱伸出 M114
                onFragment2BtnClick.onHot_outClick();
                break;
            case R.id.hot_in://烤箱缩回 M113
                onFragment2BtnClick.onHot_inClick();
                break;
            case R.id.handspike_out://推杆伸出 M112
                onFragment2BtnClick.onHandspike_outClick();
                break;
            case R.id.handspike_in://推杆缩回 M113
                onFragment2BtnClick.onHandspike_inClick();
                break;
            case R.id.out_out://出货口开 M201
                onFragment2BtnClick.onOut_outClick();
                break;
            case R.id.out_in://出货口关  M200
                onFragment2BtnClick.onOut_inClick();
                break;
            case R.id.belt_out://皮带出货 M111
                onFragment2BtnClick.onBelt_outClick();
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

/*    @Subscriber(tag = Constants.DATA_FRAGMENT_2)
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
    }*/
/*
    @Subscriber(tag = Constants.DATA_BUTTON)
    public void setBtnState(EventBusMessage<String[]> message) {
        if (message.getT() != null) {
            String[] strings = message.getT();
           *//* vertical_rotation_btn.setBackgroundResource(strings[]);//上下旋转位
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
            belt_out.setBackgroundResource();//皮带出货*//*
        }
    }*/
}
