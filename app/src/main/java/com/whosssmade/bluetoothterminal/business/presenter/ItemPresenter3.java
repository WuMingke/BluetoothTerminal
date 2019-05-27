package com.whosssmade.bluetoothterminal.business.presenter;

import com.whosssmade.bluetoothterminal.base.RxPresenter;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract3;

import javax.inject.Inject;

public class ItemPresenter3 extends RxPresenter<ItemContract3.View> implements ItemContract3.Presenter {
    @Inject
    public ItemPresenter3() {
    }
}
