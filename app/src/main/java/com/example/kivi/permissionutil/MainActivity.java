package com.example.kivi.permissionutil;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.permissonlibrary.PermissionCallback;
import com.example.permissonlibrary.PermissionUtil;

public class MainActivity extends AppCompatActivity {
    private Button btn_photo;

    private String[] permissions = new String[]{Manifest.permission.CAMERA};
    private String explanation = "请开启权限,否则无法正常使用";

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtil.with(MainActivity.this)
                        .addPermissions(permissions)    //permission;String[]
                        .addExplanation("拒绝相机权限将影响应用正常使用！")//explanation,if need
                        .addCallback(new PermissionCallback() {         //callback
                            @Override
                            public void granted() {
                                openCamera();
                            }

                            @Override
                            public void denied(String str) {
                                Log.i(TAG, "denied: " + str);
                            }
                        })
                        .request();

            }
        });


    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }


}
