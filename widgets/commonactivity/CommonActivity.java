package com.meishubao.app.common.commonactivity;

import android.os.Bundle;

import com.meishubao.app.R;
import com.meishubao.app.base.BaseFragment;
import com.meishubao.app.base.SwipeBaseActivity;
import com.meishubao.app.utils.KeyboardUtils;

/**
 * Created by wangzhe on 2016/11/24.
 * fragment 共用的activity
 */

public class CommonActivity extends SwipeBaseActivity {

    public static final String FRAGMENT_PATH  = "fragment_path";
    public static final String FRAGMENT_PARAM = "fragment_param";

    BaseFragment fragment = null;

    @Override
    protected void onCreate() {
        setContentView(R.layout.common_activity);
        String path = getIntent().getStringExtra(FRAGMENT_PATH);
        String data = getIntent().getStringExtra(FRAGMENT_PARAM);

        try {
            Class clazz = Class.forName(path);
            fragment = (BaseFragment) clazz.newInstance();
            Bundle b = new Bundle();
            b.putString(FRAGMENT_PARAM, data);
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.comment_frame, fragment)
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideKeyboard(CommonActivity.this, findViewById(R.id.comment_frame));
    }
}
