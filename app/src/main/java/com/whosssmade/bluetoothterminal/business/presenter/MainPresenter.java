package com.whosssmade.bluetoothterminal.business.presenter;

import com.whosssmade.bluetoothterminal.base.RxPresenter;
import com.whosssmade.bluetoothterminal.business.contract.MainContract;
import com.whosssmade.bluetoothterminal.ui.activities.MainActivity;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/5/24.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter() {
    }
}
