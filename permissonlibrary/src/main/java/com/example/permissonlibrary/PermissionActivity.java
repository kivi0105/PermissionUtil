package com.example.permissonlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * @description: Created by kivi on 2017/7/27.
 */

public class PermissionActivity extends AppCompatActivity {

    private final static int REQUEST_PERMISSION_CODE = 1001;
    private String[] mPermissions;
    private String mExplanation;
    public static PermissionCallback mCallBack;


    public static void startActivity(Context context, String[] permissions,
                                     String explanation) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra("permissions", permissions);
        intent.putExtra("explanation", explanation);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String[] permissions) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra("permissions", permissions);
        context.startActivity(intent);
    }

    public static void setCallback(PermissionCallback callback) {
        mCallBack = callback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissions = getIntent().getStringArrayExtra("permissions");
        mExplanation = getIntent().getStringExtra("explanation");
        requestPermission();

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isShowExplanation = false;
            for (int i = 0; i < mPermissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, mPermissions[i]) !=
                        PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, mPermissions[i])) {
                        if (!TextUtils.isEmpty(mExplanation)) {
                            isShowExplanation = true;
                        }
                    } else {
                        ActivityCompat.requestPermissions(this, mPermissions, REQUEST_PERMISSION_CODE);
                    }
                }
            }

            if (isShowExplanation) {
                showExplanationDialog();
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (REQUEST_PERMISSION_CODE == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCallBack.granted();
                finish();
            } else {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mCallBack.denied(permissions[i]);
                        finish();
                    }
                }
            }
        }

    }

    private void showExplanationDialog() {
        ExplanationDialog dialog = ExplanationDialog.newInstance(mExplanation, mPermissions, REQUEST_PERMISSION_CODE);
        dialog.show(getSupportFragmentManager(), "Explanation Dialog");
    }

}
