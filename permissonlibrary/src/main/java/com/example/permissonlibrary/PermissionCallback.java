package com.example.permissonlibrary;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * @description: Created by kivi on 2017/7/27.
 */

public interface PermissionCallback{

    void granted();

    void denied(String str);
}
