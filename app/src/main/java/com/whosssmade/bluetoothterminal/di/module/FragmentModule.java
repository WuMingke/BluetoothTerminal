package com.whosssmade.bluetoothterminal.di.module;

import android.app.Activity;
import android.app.Fragment;

import com.whosssmade.bluetoothterminal.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    @Provides
    @FragmentScope
    public Activity providerActivity(){
        return mFragment.getActivity();
    }
}
