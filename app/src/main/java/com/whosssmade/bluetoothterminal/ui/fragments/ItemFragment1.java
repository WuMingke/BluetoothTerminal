package com.whosssmade.bluetoothterminal.ui.fragments;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract1;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter1;

public class ItemFragment1 extends BaseFragment<ItemPresenter1> implements ItemContract1.View {

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_1;
    }

    @Override
    protected void initEventAndData() {

    }
}
