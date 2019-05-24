package com.whosssmade.bluetoothterminal.di.module;


import com.whosssmade.bluetoothterminal.base.BaseApplication;
import com.whosssmade.bluetoothterminal.di.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2019/4/15.
 */

@Module
public class AppModule {
    private BaseApplication mBaseApplication;

    public AppModule(BaseApplication mBaseApplication) {
        this.mBaseApplication = mBaseApplication;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    BaseApplication providerBaseApplication() {
        return this.mBaseApplication;
    }
}
