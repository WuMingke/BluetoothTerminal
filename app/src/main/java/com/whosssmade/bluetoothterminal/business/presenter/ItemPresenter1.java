package com.whosssmade.bluetoothterminal.business.presenter;

import com.whosssmade.bluetoothterminal.base.RxPresenter;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract1;

import javax.inject.Inject;

public class ItemPresenter1 extends RxPresenter<ItemContract1.View> implements ItemContract1.Presenter {
    @Inject
    public ItemPresenter1() {
    }
}
