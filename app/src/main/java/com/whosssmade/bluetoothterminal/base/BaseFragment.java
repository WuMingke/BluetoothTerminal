package com.whosssmade.bluetoothterminal.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.whosssmade.bluetoothterminal.R;
import com.whosssmade.bluetoothterminal.di.component.DaggerFragmentComponent;
import com.whosssmade.bluetoothterminal.di.component.FragmentComponent;
import com.whosssmade.bluetoothterminal.di.module.FragmentModule;
import com.whosssmade.bluetoothterminal.utils.Utils;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    @Inject
    protected T mPresenter;
    private Unbinder unbinder;

    @Override
    public void showToast(String msg) {
        Utils.showToast(BaseApplication.mContext, msg);
    }

    protected void onCreateBefore() {
    }

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        onCreateBefore();
        initInject();
        View inflate = inflater.inflate(getLayoutId(), null);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_base, null, false);
        linearLayout.addView(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return linearLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        unbinder = ButterKnife.bind(this, view);
        initEventAndData();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(BaseApplication.getAppComponent())
                .fragmentModule(new FragmentModule(this)).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        EventBus.getDefault().unregister(this);
    }
}
