package com.whosssmade.bluetoothterminal.business.presenter;

import com.whosssmade.bluetoothterminal.base.RxPresenter;
import com.whosssmade.bluetoothterminal.business.contract.SearchContract;

import javax.inject.Inject;

/**
 * Created by Administrator on 2019/5/24.
 */

public class SearchPresenter extends RxPresenter<SearchContract.View> implements SearchContract.Presenter {
    @Inject
    public SearchPresenter() {
    }
}
