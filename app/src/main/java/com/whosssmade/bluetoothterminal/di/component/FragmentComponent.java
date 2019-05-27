package com.whosssmade.bluetoothterminal.di.component;


import android.app.Activity;

import com.whosssmade.bluetoothterminal.di.FragmentScope;
import com.whosssmade.bluetoothterminal.di.module.FragmentModule;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment1;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment2;
import com.whosssmade.bluetoothterminal.ui.fragments.ItemFragment3;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();


    void inject(ItemFragment1 itemFragment);

    void inject(ItemFragment2 itemFragment2);

    void inject(ItemFragment3 itemFragment3);
}
