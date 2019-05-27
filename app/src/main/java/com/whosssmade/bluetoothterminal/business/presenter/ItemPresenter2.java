package com.whosssmade.bluetoothterminal.business.presenter;

import com.whosssmade.bluetoothterminal.base.RxPresenter;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract2;

import javax.inject.Inject;

public class ItemPresenter2 extends RxPresenter<ItemContract2.View> implements ItemContract2.Presenter {
    @Inject
    public ItemPresenter2() {
    }
}
