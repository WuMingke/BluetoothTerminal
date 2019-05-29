package com.whosssmade.bluetoothterminal.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.whosssmade.bluetoothterminal.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoadingDialog extends Dialog {

    private Unbinder bind;
    private TextView textView;

    public TextView getTextView() {
        return textView;
    }

    public LoadingDialog(@NonNull Context context) {
        super(context);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
         textView = (TextView) LayoutInflater.from(context).inflate(R.layout.diy_toast, null);
        setContentView(textView);

        textView.setText("正在连接蓝牙...");
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //bind = ButterKnife.bind(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // bind.unbind();
    }
}
