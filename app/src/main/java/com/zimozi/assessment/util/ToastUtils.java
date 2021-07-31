package com.zimozi.assessment.util;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.zimozi.assessment.AssessmentApplication;


public class ToastUtils {

    private static String oldMsg;
    private static long time;

    public static void showToast(Context context, String msg) {
        if (!msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;
    }


    public static void showToast(String msg) {
        showToast(AssessmentApplication.appContext, msg);
    }

    /**
     * @param string
     */
    public static void toastOnUIThread(String string) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                showToast(string);
            }
        });
    }

}
