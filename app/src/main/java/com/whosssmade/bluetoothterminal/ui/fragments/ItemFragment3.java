package com.whosssmade.bluetoothterminal.ui.fragments;

import android.view.View;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.base.BaseFragment;
import com.whosssmade.bluetoothterminal.business.contract.ItemContract3;
import com.whosssmade.bluetoothterminal.business.presenter.ItemPresenter3;

import butterknife.OnClick;

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

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.machine_power://打印机电源
                break;
            case R.id.machine_switch://打印机开关
                break;
            case R.id.hot://烤箱发热丝
                break;
            case R.id.fan://烤箱风扇
                break;
            case R.id.machine_reset://机械复位
                break;
            case R.id.btn_reset://按钮复位
                break;
        }

    }
}
