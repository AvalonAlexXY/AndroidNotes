package com.benbun.evtmaster.utils.permission;

/**
 * Created by wangzhe on 2016/9/6.
 * 是否成功获取权限接口
 */
public interface OnPermissionCallback {
    void onSuccess();

    void onFailure();
}
