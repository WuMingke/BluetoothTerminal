package com.whosssmade.bluetoothterminal.ui.fragments;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract2;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter2;

public class ItemFragment2 extends BaseFragment<ItemPresenter2> implements ItemContract2.View {
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_2;
    }

    @Override
    protected void initEventAndData() {

    }
}
