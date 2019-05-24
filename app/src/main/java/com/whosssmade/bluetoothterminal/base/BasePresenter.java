package com.whosssmade.bluetoothterminal.base;


/**
 * Created by Administrator on 2019/4/15.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
