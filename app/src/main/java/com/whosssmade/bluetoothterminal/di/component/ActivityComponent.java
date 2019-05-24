package com.whosssmade.bluetoothterminal.di.component;


import android.app.Activity;

import com.whosssmade.bluetoothterminal.di.ActivityScope;
import com.whosssmade.bluetoothterminal.di.module.ActivityModule;
import com.whosssmade.bluetoothterminal.ui.activities.MainActivity;
import com.whosssmade.bluetoothterminal.ui.activities.SearchActivity;

import dagger.Component;

/**
 * Created by Administrator on 2019/4/15.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    //搜索蓝牙
    void inject(SearchActivity searchActivity);

    //业务
    void inject(MainActivity mainActivity);
}

