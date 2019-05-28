package com.whosssmade.bluetoothterminal.ui.fragments;

import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract1;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter1;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;

import java.nio.channels.NonReadableChannelException;
import java.util.IdentityHashMap;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.OnClick;

public class ItemFragment1 extends BaseFragment<ItemPresenter1> implements ItemContract1.View {

    @BindView(R.id.pulse1)
    TextView pulse1;

    @BindView(R.id.frequency1)
    TextView frequency1;

    @BindView(R.id.pulse2)
    TextView pulse2;

    @BindView(R.id.frequency2)
    TextView frequency2;

    @BindView(R.id.pulse3)
    TextView pulse3;

    @BindView(R.id.frequency3)
    TextView frequency3;

    @BindView(R.id.pulse4)
    TextView pulse4;

    @BindView(R.id.frequency4)
    TextView frequency4;

    @BindView(R.id.entrepot0_pulse)
    TextView entrepot0_pulse;

    @BindView(R.id.entrepot1_pulse)
    TextView entrepot1_pulse;
    @BindView(R.id.entrepot2_pulse)
    TextView entrepot2_pulse;
    @BindView(R.id.entrepot3_pulse)
    TextView entrepot3_pulse;

    @BindView(R.id.entrepot0_quantity)
    TextView entrepot0_quantity;
    @BindView(R.id.entrepot1_quantity)
    TextView entrepot1_quantity;
    @BindView(R.id.entrepot2_quantity)
    TextView entrepot2_quantity;
    @BindView(R.id.entrepot3_quantity)
    TextView entrepot3_quantity;

    @BindView(R.id.entrepot)
    TextView entrepot;

    @BindView(R.id.bottom_pulse)
    TextView bottom_pulse;

    @BindView(R.id.thickness_pulse)
    TextView thickness_pulse;

    @BindView(R.id.down)
    Button down;

    private SetValueDialog setValueDialog;


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_1;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.pulse1, R.id.frequency1,
            R.id.pulse2, R.id.frequency2,
            R.id.pulse3, R.id.frequency3,
            R.id.pulse4, R.id.frequency4,
            R.id.entrepot0_pulse, R.id.entrepot1_pulse, R.id.entrepot2_pulse, R.id.entrepot3_pulse,
            R.id.entrepot0_quantity, R.id.entrepot1_quantity, R.id.entrepot2_quantity, R.id.entrepot3_quantity,
            R.id.entrepot,
            R.id.bottom_pulse,
            R.id.thickness_pulse,

    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pulse1:
                dialogSetTextView(pulse1);
                break;
            case R.id.frequency1:
                dialogSetTextView(frequency1);
                break;
            case R.id.pulse2:
                dialogSetTextView(pulse2);
                break;
            case R.id.frequency2:
                dialogSetTextView(frequency2);
                break;
            case R.id.pulse3:
                dialogSetTextView(pulse3);
                break;
            case R.id.frequency3:
                dialogSetTextView(frequency3);
                break;
            case R.id.pulse4:
                dialogSetTextView(pulse4);
                break;
            case R.id.frequency4:
                dialogSetTextView(frequency4);
                break;
            case R.id.entrepot0_pulse:
                dialogSetTextView(entrepot0_pulse);
                break;
            case R.id.entrepot1_pulse:
                dialogSetTextView(entrepot1_pulse);
                break;
            case R.id.entrepot2_pulse:
                dialogSetTextView(entrepot2_pulse);
                break;
            case R.id.entrepot3_pulse:
                dialogSetTextView(entrepot3_pulse);
                break;
            case R.id.entrepot0_quantity:
                dialogSetTextView(entrepot0_quantity);
                break;
            case R.id.entrepot1_quantity:
                dialogSetTextView(entrepot1_quantity);
                break;
            case R.id.entrepot2_quantity:
                dialogSetTextView(entrepot2_quantity);
                break;
            case R.id.entrepot3_quantity:
                dialogSetTextView(entrepot3_quantity);
                break;
            case R.id.entrepot:
                dialogSetTextView(entrepot);
                break;
            case R.id.bottom_pulse:
                dialogSetTextView(bottom_pulse);
                break;
            case R.id.thickness_pulse:
                dialogSetTextView(thickness_pulse);
                break;
            case R.id.up://上移

                break;
            case R.id.down://下移
                break;

            case R.id.forward://前进
                break;
            case R.id.back://后退
                break;
            case R.id.rotation1://出口面
                break;
            case R.id.rotation2://打印机面
                break;
            case R.id.open://张开
                break;
            case R.id.close://夹紧
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
