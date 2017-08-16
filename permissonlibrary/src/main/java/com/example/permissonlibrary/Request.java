package com.example.permissonlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 */

public class Request {
    private Context context;
    private String[] permissions;
    private PermissionCallback callback;
    private String explanation;
    private boolean isNoMore = false;


    public Request(Context context) {
        this.context = context;
    }

    public Request addPermissions(@NonNull String[] permissions) {
        this.permissions = permissions;
        return this;
    }

    public Request addCallback(@NonNull PermissionCallback callback) {
        this.callback = callback;
        return this;
    }

    public Request addExplanation(String explanation) {
        this.explanation = explanation;
        return this;
    }


    public void request() {
        String[] applyPermissions;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callback.granted();
            return;
        }
        if (permissions != null && permissions.length > 0) {
            //判断是否第一次申请权限
            boolean isFirstRequest = context.getSharedPreferences(ConfigConsts.SPF_PERMISSION, Context.MODE_PRIVATE)
                    .getBoolean(ConfigConsts.ISFIRST_REQUEST, true);
            if (!isFirstRequest) {
                applyPermissions = checkPermissions(permissions);
            } else {
                context.getSharedPreferences(ConfigConsts.SPF_PERMISSION, Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean(ConfigConsts.ISFIRST_REQUEST, false)
                        .apply();
                applyPermissions = permissions;
            }
        } else {
            throw new NullPointerException(context.getString(R.string.permission_not_null));
        }
        if (isNoMore) {
            return;
        }
        if (applyPermissions.length > 0) {
            startPermissionActivity(applyPermissions);
        } else {
            callback.granted();
        }
    }


    private String[] checkPermissions(String[] permissions) {
        List<String> deniedList = new ArrayList<String>();

        for (int i = 0; i < permissions.length; i++) {
            //检查权限
            if (ContextCompat.checkSelfPermission(context, permissions[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                //判断是否勾选禁止询问并禁止
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissions[i])) {
                    isNoMore = true;
                    Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_LONG).show();
                }
                deniedList.add(permissions[i]);
            }
        }
        return deniedList.toArray(new String[deniedList.size()]);
    }

    private void startPermissionActivity(String[] applyPermissions) {
        PermissionActivity.setCallback(callback);
        if (TextUtils.isEmpty(this.explanation)) {
            PermissionActivity.startActivity(context, applyPermissions);
        } else {
            PermissionActivity.startActivity(context, applyPermissions, explanation);
        }

    }


}
