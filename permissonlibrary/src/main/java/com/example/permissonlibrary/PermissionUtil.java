package com.example.permissonlibrary;

import android.content.Context;

/**
 * @description:
 */

public class PermissionUtil {

    public static Request with(Context context) {
        return new Request(context);
    }


}
