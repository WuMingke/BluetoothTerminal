package com.whosssmade.bluetoothterminal.di.component;


import com.whosssmade.bluetoothterminal.base.BaseApplication;
import com.whosssmade.bluetoothterminal.di.ContextLife;
import com.whosssmade.bluetoothterminal.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2019/4/15.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    BaseApplication getApplicationContext();
}
