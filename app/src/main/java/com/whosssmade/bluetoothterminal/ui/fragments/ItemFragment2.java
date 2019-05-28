package com.whosssmade.bluetoothterminal.ui.fragments;

import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract2;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter2;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;

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
            case R.id.vertical_rotation:
                dialogSetTextView(vertical_rotation);
                break;
            case R.id.vertical_machine:
                dialogSetTextView(vertical_machine);
                break;
            case R.id.vertical_hot:
                dialogSetTextView(vertical_hot);
                break;
            case R.id.vertical_out:
                dialogSetTextView(vertical_out);
                break;
            case R.id.vertical_get:
                dialogSetTextView(vertical_get);
                break;
            case R.id.horizontal_entrepot:
                dialogSetTextView(horizontal_entrepot);
                break;
            case R.id.horizontal_machine:
                dialogSetTextView(horizontal_machine);
                break;
            case R.id.horizontal_hot:
                dialogSetTextView(horizontal_hot);
                break;
            case R.id.horizontal_out:
                dialogSetTextView(horizontal_out);
                break;
            case R.id.down_pulse:
                dialogSetTextView(down_pulse);
                break;
            case R.id.vertical_rotation_btn://上下旋转位

                break;
            case R.id.vertical_machine_btn://上下打印机位

                break;
            case R.id.vertical_hot_btn://上下烤箱位

                break;
            case R.id.vertical_out_btn://上下出口位

                break;
            case R.id.vertical_get_btn://上下取料

                break;
            case R.id.horizontal_entrepot_btn://横向料仓位

                break;
            case R.id.horizontal_machine_btn://横向打印机位

                break;
            case R.id.horizontal_hot_btn://横向烤箱位

                break;
            case R.id.horizontal_out_btn://横向出口位

                break;
            case R.id.down_pulse_btn://下放脉冲位    无数据

                break;
            case R.id.machine_out://打印机伸出

                break;
            case R.id.machine_in://打印机缩回

                break;
            case R.id.hot_out://烤箱伸出

                break;
            case R.id.hot_in://烤箱缩回

                break;
            case R.id.handspike_out://推杆伸出

                break;
            case R.id.handspike_in://推杆缩回

                break;
            case R.id.out_out://出货口开

                break;
            case R.id.out_in://出货口关

                break;
            case R.id.belt_out://皮带出货

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
}
