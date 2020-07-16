package cn.vastsky.libs.gdlocation.config;

import android.content.Context;
import android.text.TextUtils;

import cn.vastsky.libs.gdlocation.store.CommonBaseStore;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 */
public class LocationConfig {
    private static Context sContext; //Application的context
    //当前纬度
    public static double sLat;
    //当前经度
    public static double sLng;
    //高德定位是否成功
    public static boolean sIsLocatedSuccess = false;
    //定位城市信息
    public static String sLocationAddress;
    public static String sLocationCity;
    public static String sLocationProvince;
    public static String sLocationDistrict;
    public static String sLocationStreet;
    public static String sLocationCityCode;
    public static String sLocationProvinceCode;
    public static String sLocationDistrictCode;
    //高德码
    public static String sLocationCityAdCode;
    public static String sLocationProvinceAdCode;
    public static String sAdCode;
    public static int sLocationIsAbroad;
    //当前位置&上一次启动定位
    public static String sLocationShortName;
    public static String sLocationShortAdCode;
    //测试数据配置
    public static double sLatTest = -1;
    public static double sLngTest = -1;

    public static void init(Context context) {
        sContext = context;
    }

    /**
     * 纬度
     *
     * @param latitude
     */
    public static void setLatitude(double latitude) {
        LocationConfig.sLat = latitude;
    }

    public static double getLatitude() {
        return LocationConfig.sLat;
    }

    /**
     * 经度
     *
     * @param longtitude
     */
    public static void setLongtitude(double longtitude) {
        LocationConfig.sLng = longtitude;
    }

    public static double getLongtitude() {
        return LocationConfig.sLng;
    }

    /**
     * 获取定位地址信息
     */
    public static String getLocationAddress() {
        if (TextUtils.isEmpty(LocationConfig.sLocationAddress)) {
            LocationConfig.sLocationAddress = LocationConstant.DEFAULT_ADDR_INFO;
        }
        return LocationConfig.sLocationAddress;
    }

    /**
     * 设置定位地址信息
     *
     * @param address 地址信息
     */
    public static void setLocationAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            LocationConfig.sLocationAddress = LocationConstant.DEFAULT_ADDR_INFO;
        } else {
            LocationConfig.sLocationAddress = address;
        }
    }

    /**
     * 获取定位省份
     *
     * @return
     */
    public static String getLocationProvince() {
        if (TextUtils.isEmpty(LocationConfig.sLocationProvince)) {
            LocationConfig.sLocationProvince = LocationConstant.DEFAULT_CITY_PROVINCE;
        }
        return LocationConfig.sLocationProvince;
    }

    /**
     * 设置定位省份
     *
     * @param locationProvince 省份
     */
    public static void setLocationProvince(String locationProvince) {
        if (TextUtils.isEmpty(locationProvince)) {
            LocationConfig.sLocationProvince = LocationConstant.DEFAULT_CITY_PROVINCE;
        } else {
            LocationConfig.sLocationProvince = locationProvince;
        }
    }

    /**
     * 获取定位城市信息
     */
    public static String getLocationCity() {
        if (TextUtils.isEmpty(LocationConfig.sLocationCity)) {
            LocationConfig.sLocationCity = LocationConstant.DEFAULT_CITY_PROVINCE;
        }
        return LocationConfig.sLocationCity;
    }

    /**
     * 设置定位城市信息
     *
     * @param address 地址信息
     */
    public static void setLocationCity(String address) {
        if (TextUtils.isEmpty(address)) {
            LocationConfig.sLocationCity = LocationConstant.DEFAULT_CITY_NAME;
        } else {
            LocationConfig.sLocationCity = address;
        }
    }

    /**
     * 获取定位区信息
     *
     * @return
     */
    public static String getLocationDistrict() {
        if (TextUtils.isEmpty(LocationConfig.sLocationDistrict)) {
            LocationConfig.sLocationDistrict = LocationConstant.DEFAULT_CITY_DISTRICT;
        }
        return LocationConfig.sLocationDistrict;
    }

    /**
     * 设置定位区信息
     *
     * @param locationDistrict 区信息
     */
    public static void setLocationDistrict(String locationDistrict) {
        if (TextUtils.isEmpty(locationDistrict)) {
            LocationConfig.sLocationDistrict = LocationConstant.DEFAULT_CITY_DISTRICT;
        } else {
            LocationConfig.sLocationDistrict = locationDistrict;
        }
    }

    public static String getsLocationStreet() {
        if (TextUtils.isEmpty(LocationConfig.sLocationStreet)) {
            LocationConfig.sLocationStreet = LocationConstant.DEFAULT_CITY_PROVINCE;
        }
        return LocationConfig.sLocationStreet;
    }

    public static void setsLocationStreet(String sLocationStreet) {
        if (TextUtils.isEmpty(sLocationStreet)) {
            LocationConfig.sLocationStreet = LocationConstant.DEFAULT_CITY_STREET;
        } else {
            LocationConfig.sLocationStreet = sLocationStreet;
        }
    }

    /**
     * 获取定位城市编码
     */
    public static String getLocationCityCode() {
        if (TextUtils.isEmpty(LocationConfig.sLocationCityCode)) {
            LocationConfig.sLocationCityCode = LocationConstant.DEFAULT_CITY_CODE;
        }
        return LocationConfig.sLocationCityCode;
    }

    /**
     * 设置定位城市编码
     *
     * @param cityCode 城市编码
     */
    public static void setLocationCityCode(String cityCode) {
        if (TextUtils.isEmpty(cityCode)) {
            LocationConfig.sLocationCityCode = LocationConstant.DEFAULT_CITY_CODE;
        } else {
            LocationConfig.sLocationCityCode = cityCode;
        }
    }

    /**
     * 截取高德码 获取城市编码 如：320100
     */
    public static String getLocationCityAdCode() {
        if (TextUtils.isEmpty(LocationConfig.sLocationCityAdCode)) {
            LocationConfig.sLocationCityAdCode = LocationConstant.DEFAULT_CITY_CODE;
        }
        return LocationConfig.sLocationCityAdCode;
    }

    /**
     * 设置定位城市编码
     *
     * @param cityCode 城市编码
     */
    public static void setLocationCityAdCode(String cityCode) {
        if (TextUtils.isEmpty(cityCode)) {
            LocationConfig.sLocationCityAdCode = LocationConstant.DEFAULT_CITY_CODE;
        } else {
            LocationConfig.sLocationCityAdCode = cityCode;
        }
    }

    /**
     * 获取定位省code
     * <p>
     * 高德不返回省份code
     *
     * @return
     */
    @Deprecated
    public static String getsLocationProvinceCode() {
        if (TextUtils.isEmpty(LocationConfig.sLocationProvinceCode)) {
            LocationConfig.sLocationProvinceCode = LocationConstant.DEFAULT_PROVINCE_CODE;
        }
        return LocationConfig.sLocationProvinceCode;
    }

    public static void setsLocationProvinceCode(String sLocationProvinceCode) {
        if (TextUtils.isEmpty(sLocationProvinceCode)) {
            LocationConfig.sLocationProvinceCode = LocationConstant.DEFAULT_PROVINCE_CODE;
        } else {
            LocationConfig.sLocationProvinceCode = sLocationProvinceCode;
        }
    }


    /**
     * 获取定位省高德code 如：320101
     * <p>
     *
     * @return
     */
    public static String getLocationProvinceAdCode() {
        if (TextUtils.isEmpty(LocationConfig.sLocationProvinceAdCode)) {
            LocationConfig.sLocationProvinceAdCode = LocationConstant.DEFAULT_PROVINCE_CODE;
        }
        return LocationConfig.sLocationProvinceAdCode;
    }

    public static void setLocationProvinceAdCode(String sLocationProvinceCode) {
        if (TextUtils.isEmpty(sLocationProvinceCode)) {
            LocationConfig.sLocationProvinceAdCode = LocationConstant.DEFAULT_PROVINCE_CODE;
        } else {
            LocationConfig.sLocationProvinceAdCode = sLocationProvinceCode;
        }
    }

    /**
     * 暂时返回区域编码，高德不返回区的编码
     *
     * @return
     */
    public static String getsLocationDistrictCode() {
        if (TextUtils.isEmpty(LocationConfig.sLocationDistrictCode)) {
            LocationConfig.sLocationDistrictCode = LocationConstant.DEFAULT_DISTRICT_CODE;
        }
        return LocationConfig.sLocationDistrictCode;
    }

    public static void setsLocationDistrictCode(String sLocationDistrictCode) {
        if (TextUtils.isEmpty(sLocationDistrictCode)) {
            LocationConfig.sLocationDistrictCode = LocationConstant.DEFAULT_DISTRICT_CODE;
        } else {
            LocationConfig.sLocationDistrictCode = sLocationDistrictCode;
        }
    }

    /**
     * 高德码
     *
     * @return
     */
    public static String getAdCode() {
        if (TextUtils.isEmpty(LocationConfig.sAdCode)) {
            LocationConfig.sAdCode = LocationConstant.DEFAULT_DISTRICT_CODE;
        }
        return LocationConfig.sAdCode;
    }

    public static void setAdCode(String adCode) {
        if (TextUtils.isEmpty(adCode)) {
            LocationConfig.sAdCode = LocationConstant.DEFAULT_DISTRICT_CODE;
        } else {
            LocationConfig.sAdCode = adCode;
        }
    }

    public static int getCurrentCityIsAbroad() {
        return LocationConfig.sLocationIsAbroad;
    }

    public static synchronized void setCurrentCityIsAbroad(int isAbroad) {
        LocationConfig.sLocationIsAbroad = isAbroad;
    }

    /**
     * 当前位置，用户手动选择的城市(基于用户)
     *
     * @return
     */
    public static String getCurrentCity() {
        return CommonBaseStore.getStringByKey(CommonBaseStore.BASE_CURRENT_CITY);
    }

    public static void setCurrentCity(String currentCity) {
        CommonBaseStore.saveStringByKey(CommonBaseStore.BASE_CURRENT_CITY, currentCity);
    }

    public static String getCurrentCityAdCode() {
        return CommonBaseStore.getStringByKey(CommonBaseStore.BASE_CURRENT_CITY_CODE);
    }

    public static void setCurrentCityAdCode(String cityAdCode) {
        CommonBaseStore.saveStringByKey(CommonBaseStore.BASE_CURRENT_CITY_CODE, cityAdCode);
    }

    /**
     * 上次启动定位位置(基于用户)
     *
     * @return
     */
    public static String getLastCity() {
        return CommonBaseStore.getStringByKey(CommonBaseStore.BASE_LAST_CITY);
    }

    public static void setLastCity(String lastCity) {
        CommonBaseStore.saveStringByKey(CommonBaseStore.BASE_LAST_CITY, lastCity);
    }

    public static String getLastCityAdCode() {
        return CommonBaseStore.getStringByKey(CommonBaseStore.BASE_LAST_CITY_CODE);
    }

    public static void setLastCityAdCode(String lastCityAdCode) {
        CommonBaseStore.saveStringByKey(CommonBaseStore.BASE_LAST_CITY_CODE, lastCityAdCode);
    }

    /**
     * 城市简称
     *
     * @return
     */
    public static String getShortName() {
        if (TextUtils.isEmpty(LocationConfig.sLocationShortName)) {
            LocationConfig.sLocationShortName = CommonBaseStore.getStringByKey(CommonBaseStore.BASE_CITY_SHORT_NAME);
        }
        return LocationConfig.sLocationShortName;
    }

    public static void setShortName(String name) {
        LocationConfig.sLocationShortName = name;
        CommonBaseStore.saveStringByKey(CommonBaseStore.BASE_CITY_SHORT_NAME, name);
    }

    public static String getShortAdCode() {
        if (TextUtils.isEmpty(LocationConfig.sLocationShortAdCode)) {
            LocationConfig.sLocationShortAdCode = CommonBaseStore.getStringByKey(CommonBaseStore.BASE_CITY_SHORT_AD_CODE);
        }
        return LocationConfig.sLocationShortAdCode;
    }

    public static void setShortAdCode(String code) {
        LocationConfig.sLocationShortAdCode = code;
        CommonBaseStore.saveStringByKey(CommonBaseStore.BASE_CITY_SHORT_AD_CODE, code);
    }

}
