package com.whosssmade.bluetoothterminal.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SetValueDialog extends Dialog {

    @BindView(R.id.edit)
    EditText editText;

    private Unbinder bind;
    private TextView mTextView;

    public void setTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    public SetValueDialog(@NonNull Context context) {
        super(context);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_value, null);
        setContentView(view);
        bind = ButterKnife.bind(this);

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        EventBus.getDefault().register(this);

    }

    @Override
    public void show() {
        super.show();
        editText.setText("");
    }

    @OnClick({R.id.cancel, R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sure:
                String value = editText.getText().toString();
                mTextView.setText(value);
                String tag = (String) mTextView.getTag();
                EventBusMessage<Map<String, String>> message = new EventBusMessage<>();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put(tag, value);
                message.setT(stringMap);
                EventBus.getDefault().post(message, Constants.NEW_VALUE);
                dismiss();
                break;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //   bind.unbind();
        EventBus.getDefault().unregister(this);
    }
}
