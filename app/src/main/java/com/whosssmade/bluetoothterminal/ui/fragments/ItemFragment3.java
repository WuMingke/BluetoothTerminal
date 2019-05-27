package com.whosssmade.bluetoothterminal.ui.fragments;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract3;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter3;

public class ItemFragment3 extends BaseFragment<ItemPresenter3> implements ItemContract3.View {
    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_item_3;
    }

    @Override
    protected void initEventAndData() {

    }
}
