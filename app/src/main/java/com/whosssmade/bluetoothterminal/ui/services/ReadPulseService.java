/*
package com.whosssmade.bluetoothterminal.ui.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.whosssmade.bluetoothterminal.model.constant.Constants;
import com.whosssmade.bluetoothterminal.model.constant.EventBusMessage;

import org.simple.eventbus.EventBus;

public class ReadPulseService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBusMessage<String> message = new EventBusMessage<>();
        message.setT("REQUEST_VALUE");
        EventBus.getDefault().post(message, Constants.REQUEST_VALUE);
        onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
*/
