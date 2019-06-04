package com.whosssmade.bluetoothterminal.ui.fragments;

import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract1;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter1;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;
import com.whosssmade.bluetoothterminal.ui.dialogs.SetValueDialog;

import org.simple.eventbus.Subscriber;

import java.nio.channels.NonReadableChannelException;
import java.util.Arrays;
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

    public TextView getEntrepot0_pulse() {
        return entrepot0_pulse;
    }

    public TextView getEntrepot1_pulse() {
        return entrepot1_pulse;
    }

    public TextView getEntrepot2_pulse() {
        return entrepot2_pulse;
    }

    public TextView getEntrepot3_pulse() {
        return entrepot3_pulse;
    }

    public TextView getEntrepot0_quantity() {
        return entrepot0_quantity;
    }

    public TextView getEntrepot1_quantity() {
        return entrepot1_quantity;
    }

    public TextView getEntrepot2_quantity() {
        return entrepot2_quantity;
    }

    public TextView getEntrepot3_quantity() {
        return entrepot3_quantity;
    }

    public TextView getEntrepot() {
        return entrepot;
    }

    public TextView getBottom_pulse() {
        return bottom_pulse;
    }

    public TextView getThickness_pulse() {
        return thickness_pulse;
    }

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

    @BindView(R.id.up)
    TextView up;

    @BindView(R.id.down)
    TextView down;

    @BindView(R.id.forward)
    TextView forward;

    @BindView(R.id.back)
    TextView back;

    @BindView(R.id.rotation1)
    TextView rotation1;

    @BindView(R.id.rotation2)
    TextView rotation2;

    @BindView(R.id.open)
    TextView open;

    @BindView(R.id.close)
    TextView close;

    public TextView getUp() {
        return up;
    }

    public TextView getDown() {
        return down;
    }

    public TextView getForward() {
        return forward;
    }

    public TextView getBack() {
        return back;
    }

    public TextView getRotation1() {
        return rotation1;
    }

    public TextView getRotation2() {
        return rotation2;
    }

    public TextView getOpen() {
        return open;
    }

    public TextView getClose() {
        return close;
    }

    private SetValueDialog setValueDialog;

    public TextView getPulse1() {
        return pulse1;
    }

    public TextView getFrequency1() {
        return frequency1;
    }

    public TextView getPulse2() {
        return pulse2;
    }

    public TextView getFrequency2() {
        return frequency2;
    }

    public TextView getPulse3() {
        return pulse3;
    }

    public TextView getFrequency3() {
        return frequency3;
    }

    public TextView getPulse4() {
        return pulse4;
    }

    public TextView getFrequency4() {
        return frequency4;
    }

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

    public interface OnFragment1BtnClick {
        void onUpClick();

        void onDownClick();

        void onForwardClick();

        void onBackClick();

        void onRotation1Click();

        void onRotation2Click();

        void onOpenClick();

        void onCloseClick();
    }

    private OnFragment1BtnClick onFragment1BtnClick;

    public void setOnFragment1BtnClick(OnFragment1BtnClick onFragment1BtnClick) {
        this.onFragment1BtnClick = onFragment1BtnClick;
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
            R.id.up, R.id.down, R.id.forward, R.id.back, R.id.rotation1, R.id.rotation2, R.id.open, R.id.close
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
                onFragment1BtnClick.onUpClick();
                break;
            case R.id.down://下移
                onFragment1BtnClick.onDownClick();
                break;
            case R.id.forward://前进
                onFragment1BtnClick.onForwardClick();
                break;
            case R.id.back://后退
                onFragment1BtnClick.onBackClick();
                break;
            case R.id.rotation1://出口面
                onFragment1BtnClick.onRotation1Click();
                break;
            case R.id.rotation2://打印机面
                onFragment1BtnClick.onRotation2Click();
                break;
            case R.id.open://张开
                onFragment1BtnClick.onOpenClick();
                break;
            case R.id.close://夹紧
                onFragment1BtnClick.onCloseClick();
                break;


        }
    }

    @Subscriber(tag = Constants.DATA_BUTTON)
    public void setBtnState(EventBusMessage<String[]> message) {
        if (message.getT() != null) {
            String[] strings = message.getT();
            Log.i("wmk", "Btn_Data---" + Arrays.toString(strings));
          /*  up.setBackgroundResource();//上移
            down.setBackgroundResource();//下移
            forward.setBackgroundResource();//前进
            back.setBackgroundResource();//后退
            rotation1.setBackgroundResource();//出口面
            rotation2.setBackgroundResource();//打印机面
            open.setBackgroundResource();//张开
            close.setBackgroundResource();//夹紧
*/
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
