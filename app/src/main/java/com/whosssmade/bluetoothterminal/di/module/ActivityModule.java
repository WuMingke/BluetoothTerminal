package com.whosssmade.bluetoothterminal.di.module;

import android.app.Activity;

import com.whosssmade.bluetoothterminal.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/4/15.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityScope
    public Activity providerActivity() {
        return this.mActivity;
    }
}
