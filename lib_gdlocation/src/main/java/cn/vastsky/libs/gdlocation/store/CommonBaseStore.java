package cn.vastsky.libs.gdlocation.store;


import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.store.Store;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class CommonBaseStore extends Store {

    public static final String GLOBAL_CONFIG = "GLOBAL_CONFIG";
    public static final String BLACFISH_FINGER_PRINT = "BLACFISH_FINGER_PRINT";

    public static final String BASE_CURRENT_CITY = "BASE_CURRENT_CITY"; //当前位置
    public static final String BASE_CURRENT_CITY_CODE = "BASE_CURRENT_CITY_CODE"; //当前位置
    public static final String BASE_LAST_CITY = "BASE_LAST_CITY"; //上次定位位置
    public static final String BASE_LAST_CITY_CODE = "BASE_LAST_CITY_CODE"; //上次定位位置
    public static final String BASE_CITY_SHORT_NAME = "BASE_CITY_SHORT_NAME"; //城市简称
    public static final String BASE_CITY_SHORT_AD_CODE = "BASE_CITY_SHORT_AD_CODE"; //城市简称

    public static void saveStringByKey(String key, String value) {
        sp(AppConfigLib.getContext()).putString(key, value);
    }

    public static String getStringByKey(String key) {
        return sp(AppConfigLib.getContext()).getString(key, "");
    }

}
