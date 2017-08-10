package com.example.permissonlibrary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @description: 权限被禁用时的解释对话框
 */

public class ExplanationDialog extends DialogFragment {

    public static ExplanationDialog newInstance(String explanation, String[] permission, int code) {

        Bundle args = new Bundle();
        args.putString("explanation", explanation);
        args.putStringArray("permission", permission);
        args.putInt("code", code);
        ExplanationDialog fragment = new ExplanationDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String explanation = bundle.getString("explanation");
        final String[] permission = bundle.getStringArray("permission");
        final int code = bundle.getInt("code");
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage(explanation)
                .setPositiveButton(R.string.permission_apply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), permission, code);
                        dismiss();

                    }
                })
                .setNegativeButton(R.string.permission_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        getActivity().finish();
                    }
                })
                .create();
        ExplanationDialog.this.setCancelable(false);
        return dialog;
    }


}
