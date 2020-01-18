package cn.kezy.libs.common.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import cn.kezy.libs.common.config.AppConfigLib;


public class ToastUtil {

    private static Toast t_toast;

    private static Toast initToast(CharSequence message, int duration) {
        if (!TextUtils.isEmpty(message)) {
            if (t_toast == null) {
                t_toast = Toast.makeText(AppConfigLib.getContext(), message, duration);
            } else {
                t_toast.setText(message);
                t_toast.setDuration(duration);
            }
            t_toast.setGravity(Gravity.CENTER, 0, 0);
            return t_toast;
        } else {
            return null;
        }
    }

    public static void showLong(CharSequence message) {

        Toast toast = initToast(message, Toast.LENGTH_LONG);
        if (toast != null) {
            toast.show();
        }
    }

    public static void showLong(int strResId) {

        Toast toast = initToast(AppConfigLib.getContext().getResources().getText(strResId), Toast.LENGTH_LONG);
        if (toast != null) {
            toast.show();
        }
    }

    public static void show(CharSequence message) {
        Toast toast = initToast(message, Toast.LENGTH_SHORT);
        if (toast != null) {
            toast.show();
        }
    }

    public static void show(int strResId) {
        Toast toast = initToast(AppConfigLib.getContext().getResources().getText(strResId), Toast.LENGTH_SHORT);
        if (toast != null) {
            toast.show();
        }
    }

    public static void show(CharSequence message, int duration) {
        Toast toast = initToast(message, duration);
        if (toast != null) {
            toast.show();
        }
    }


    public static void show(int strResId, int duration) {
        Toast toast = initToast(AppConfigLib.getContext().getResources().getText(strResId), duration);
        if (toast != null) {
            toast.show();
        }
    }

    public static void showNewToast(CharSequence message) {
        Toast toast = Toast.makeText(AppConfigLib.getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (toast != null) {
            toast.show();
        }
    }
}
