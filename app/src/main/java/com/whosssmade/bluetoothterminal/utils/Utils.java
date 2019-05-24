package com.whosssmade.bluetoothterminal.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.whosssmade.bluetoothterminal.R;

/**
 * Created by Administrator on 2019/5/24.
 */

public class Utils {
    public static void showToast(Context context, String msg) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.diy_toast, null);
        textView.setText(msg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(textView);
        toast.show();
    }
}
