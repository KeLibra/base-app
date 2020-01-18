package cn.kezy.libs.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;



/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class CommomStatusUtils {


    /**
     * 获取设备
     *
     * @return
     */
    public static String getDevice() {
        return null;
    }


    /**
     * 获取手机品牌
     * @return
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机操作系统
     * @return
     */
    public static String getDeviceOs() {
        return null;
    }

    /**
     * 获取手机操作系统 版本
     * @return
     */
    public static String getDeviceOs_Version() {
        return null;
    }

    /**
     * 获取 系统当前语言
     * @return
     */
    public static String getLanguage() {
        return null;
    }

    /**
     * 获取 地理
     * @return
     */
    public static String getGEO() {
        return null;
    }

    /**
     * 获取 国家
     * @return
     */
    public static String getCountry() {
        return null;
    }


    /**
     * 获取 省
     * @return
     */
    public static String getProvince() {
        return null;
    }


    /**
     * 获取 市县
     * @return
     */
    public static String getCity() {
        return null;
    }


    /**
     * 获取 区县
     * @return
     */
    public static String getDistrict() {
        return null;
    }

    /**
     * 获取  块
     * @return
     */
    public static String getBlock() {
        return null;
    }


    /**
     * 获取  经纬度
     * @return
     */
    public static String getCoordinate() {
        return null;
    }

    /**
     * 获取  机构
     * @return
     */
    public static String getInstitution() {
        return null;
    }

    /**
     * 获取  产品
     * @return
     */
    public static String getProduct() {
        return null;
    }



    /**
     * 获取应用当前版名称
     */
    public static String getCurrentVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e("==msg_CommomStatusUtils", "package info not get");
            return "";
        }
    }


}
