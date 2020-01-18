package cn.kezy.libs.common.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

import cn.kezy.libs.common.config.AppConfigLib;


/**
 * @author: kezy
 * @create_time 2019/10/30
 * @description:
 */
public class AppInfoUtils {

    /**
     * 获取版本名称
     *
     * @return 版本名称
     */
    public static String getVersionName() {

        //获取包管理器
        PackageManager pm = AppConfigLib.getContext().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(AppConfigLib.getContext().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static int getVersionCode() {

        //获取包管理器
        PackageManager pm = AppConfigLib.getContext().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(AppConfigLib.getContext().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }

    /**
     * 获取App的名称
     *
     * @return 名称
     */
    public static String getAppName() {
        PackageManager pm = AppConfigLib.getContext().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(AppConfigLib.getContext().getPackageName(), 0);
            //获取应用 信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //获取albelRes
            int labelRes = applicationInfo.labelRes;
            //返回App的名称
            return AppConfigLib.getContext().getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取系统语言
     *
     * @return
     */
    public static String getLocalLanguage() {

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = AppConfigLib.getContext().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = AppConfigLib.getContext().getResources().getConfiguration().locale;
        }
        return locale.getLanguage() + "-" + locale.getCountry();
    }
}
