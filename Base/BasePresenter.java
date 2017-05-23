package com.baidu.bdcamlibrary.base;

/**
 * Created by zhe on 2017/5/23.
 * mvp basepresenter
 */

public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }
}