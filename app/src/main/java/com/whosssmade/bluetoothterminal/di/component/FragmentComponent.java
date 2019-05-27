package com.whosssmade.bluetoothterminal.di.component;


import android.app.Activity;
import android.app.Fragment;

import com.whosssmade.bluetoothterminal.di.FragmentScope;
import com.whosssmade.bluetoothterminal.di.module.FragmentModule;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();



}
