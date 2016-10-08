package com.benbun.evtmaster.utils.permission;

import android.Manifest;
import android.content.Context;

import com.benbun.evtmaster.utils.ActivityUtil;
import com.benbun.evtmaster.utils.DialogUtils;

/**
 * Created by wangzhe on 2016/9/6.
 * 具体权限处理
 */
public class PermissionUtils {

    /**
     * 请求相机权限
     * @param context 当前context，activity或fragment
     * @param ctx getContext
     */
    public static void requestCameraPermission(final Object context, final Context ctx) {
        PermissionHelper.getInstance().setPermissionListener(new OnPermissionCallback() {
            @Override
            public void onSuccess() {
                ActivityUtil.startCaptureActivity(ctx);
            }

            @Override
            public void onFailure() {
                PermissionHelper.getInstance().showPermissionDialog(ctx,
                        "为了正常使用扫码功能，请在设置中开启相机权限");
            }
        });
        PermissionHelper.getInstance().requestPermission(context, Manifest.permission.CAMERA);
    }

    /**
     * 上传头像时，请求相机和内存卡权限
     *
     * @param context 当前context，activity或fragment
     * @param ctx getContext
     * @param listener 选择拍照和从相册选取监听
     */
    public static void requestCameraAndSdCardPermission(final Object context, final Context ctx, final DialogUtils.OnChangeHeadImgListener listener) {
        PermissionHelper.getInstance().setPermissionListener(new OnPermissionCallback() {
            @Override
            public void onSuccess() {
                DialogUtils.getInstance().showChangeHeadImgDialog(ctx, listener);
            }

            @Override
            public void onFailure() {
                PermissionHelper.getInstance().showPermissionDialog(ctx,
                        "为了更换头像，请在设置中开启相机和存储空间权限");
            }
        });
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        PermissionHelper.getInstance().requestPermissions(context, permissions);
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        PermissionHelper.getInstance().onRequestPermissionsResult(requestCode, grantResults);
    }

}
