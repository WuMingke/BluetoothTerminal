/*
package com.whosssmade.bluetoothterminal.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.model.bean.DeviceBean;
import com.whosssmade.bluetoothterminal.model.constant.EventBusConstants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

*/
/**
 * Created by Administrator on 2019/5/24.
 *//*


public class MatchDialog extends Dialog {

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.input)
    EditText input;

    private final Unbinder bind;
    private DeviceBean mDeviceBean;

    public void setDeviceBean(DeviceBean mDeviceBean) {
        this.mDeviceBean = mDeviceBean;
        name.setText("设备：" + mDeviceBean.getName());
    }

    public MatchDialog(@NonNull Context context) {
        super(context);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_match, null);
        setContentView(view);
        bind = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.cancel, R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sure:
                String pwd = input.getText().toString();
                EventBusMessage<String> message = new EventBusMessage<>();
                message.setT(pwd);
                EventBus.getDefault().post(message, Constants.PASSWORD);
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        bind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
*/
