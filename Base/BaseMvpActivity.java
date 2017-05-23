package com.baidu.bdcamlibrary.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhe on 2017/5/23.
 * mvp baseactivity
 */

public abstract class BaseMvpActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();
    }

    public abstract T initPresenter();

}