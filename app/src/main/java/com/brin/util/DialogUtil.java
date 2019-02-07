package com.brin.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.brin.gdufe.R;

public class DialogUtil {

    public static AlertDialog.Builder createDialog(Context mContext,boolean cancelable,String title,String message){

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        dialog.setTitle(title)
        .setMessage(message)
        .setCancelable(cancelable);
        return dialog;

    }

    public static Dialog createDialog (Context mContext, boolean cancelable,View view){
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        return dialog;
    }
}
