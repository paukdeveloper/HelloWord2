package com.datsenko.yevhenii.boats.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.Window;

/**
 * Created by Yevhenii on 3/15/2016.
 */
public class LoadingViewMaterial {

    private static ProgressDialog dialog;

    public static void show(Context context) {
        if(dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setMessage("Loading...");
//            dialog.set
//            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public static void hide() {
        if(dialog != null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    dialog = null;
                }
            },200);

        }
    }

    public static boolean isNull() {
        if (dialog == null) {
            return true;
        } else {
            return false;
        }

    }
}
