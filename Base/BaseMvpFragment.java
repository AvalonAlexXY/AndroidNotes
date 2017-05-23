package com.baidu.bdcamlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhe on 2017/5/23.
 * mvp basefragment
 */

public abstract class BaseMvpFragment<V, T extends BasePresenter<V>> extends Fragment {

    public T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        presenter.attach((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        presenter.dettach();
        super.onDestroyView();
    }

    public abstract T initPresenter();

}
