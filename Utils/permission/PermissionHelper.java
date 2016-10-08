package com.benbun.evtmaster.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

/**
 * Created by wangzhe on 2016/9/5.
 * Android 6.0 之后权限处理工具类
 */
public class PermissionHelper {

    public static final int PERMISSION_REQUEST_CODE = 0x10;

    public PermissionHelper() {

    }

    public static class Holder {
        private static PermissionHelper permissionHelper = new PermissionHelper();
    }

    public static PermissionHelper getInstance() {
        return Holder.permissionHelper;
    }


    private static OnPermissionCallback mListener;

    public void setPermissionListener(OnPermissionCallback listener) {
        mListener = listener;
    }

    /**
     * 检测并请求权限
     *
     * @param context    Activity或Fragment
     * @param permission String[] permission
     */
    public void requestPermission(Object context, String permission) {
        if (Build.VERSION.SDK_INT > 21) {
            if (checkPermission(context, permission)) {
                if (mListener != null) {
                    mListener.onSuccess();
                }
            } else {
                request(context, new String[]{permission});
            }
        } else {
            if (mListener != null) {
                mListener.onSuccess();
            }
        }

    }

    /**
     * 请求权限
     *
     * @param context     Activity或Fragment
     * @param permissions String[] permission
     */
    public void request(Object context, String[] permissions) {
        if (context instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
        } else if (context instanceof Fragment) {
            ((Fragment) context).requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 检测是否具有某个权限
     *
     * @param context    Activity或Fragment
     * @param permission Manifest.permission.xxx
     * @return 是否具有此权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermission(Object context, String permission) {
        if (context instanceof Activity) {
            return ContextCompat.checkSelfPermission((Activity) context, permission)
                    == PackageManager.PERMISSION_GRANTED;
        } else if (context instanceof Fragment) {
            return ((Fragment) context).getActivity().checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            throw new RuntimeException("context is not a activity or fragment");
        }
    }

    /**
     * 检测并请求权限组
     *
     * @param context     Activity或Fragment
     * @param permissions permissions
     */
    public void requestPermissions(Object context, String[] permissions) {
        if (Build.VERSION.SDK_INT > 21) {
            String[] permissionNo = checkPermissionArray(context, permissions);
            if (permissionNo.length <= 0) {
                if (mListener != null) {
                    mListener.onSuccess();
                }
            } else {
                request(context, permissionNo);
            }
        } else {
            if (mListener != null) {
                mListener.onSuccess();
            }
        }
    }

    /**
     * 检测一组权限
     *
     * @param context     Activity或Fragment
     * @param permissions 权限组
     * @return 不具有权限的权限组
     */
    public String[] checkPermissionArray(Object context, String[] permissions) {
        ArrayList<String> permiList = new ArrayList<>();
        for (String p : permissions) {
            if (!checkPermission(context, p)) {
                permiList.add(p);
            }
        }
        return permiList.toArray(new String[permiList.size()]);
    }

    /**
     * 处理onRequestPermissionsResult数据
     *
     * @param requestCode  requestCode
     * @param grantResults grantResults
     */
    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mListener != null) {
                        mListener.onSuccess();
                    }
                } else {
                    if (mListener != null) {
                        mListener.onFailure();
                    }
                }
                break;
        }
    }

    /**
     * 对话框，提示用户到设置中设置权限
     *
     * @param context context
     * @param msg     message
     */
    public void showPermissionDialog(final Context context, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri packageURI = Uri.parse("package:" + context.getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        context.startActivity(intent);
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(true);
    }
}
