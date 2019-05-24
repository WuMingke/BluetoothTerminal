package com.whosssmade.bluetoothterminal.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.whosssmade.bluetoothterminal.di.component.AppComponent;
import com.whosssmade.bluetoothterminal.di.component.DaggerAppComponent;
import com.whosssmade.bluetoothterminal.di.module.AppModule;

/**
 * Created by Administrator on 2019/4/15.
 */

public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        //activity管理
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof BaseActivity) {
                    ActivityManager.addActivity(activity);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityManager.finishActivity(activity);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule((BaseApplication) mContext)).build();
    }

}
