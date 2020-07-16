package cn.vastsky.libs.gdlocation;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;

import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.libs.gdlocation.config.LocationConfig;
import cn.vastsky.libs.gdlocation.config.LocationConstant;
import cn.vastsky.libs.gdlocation.config.LocationType;
import cn.vastsky.libs.gdlocation.impl.ILocationManager;
import cn.vastsky.libs.gdlocation.impl.LocationListener;
import cn.vastsky.libs.gdlocation.model.LocationModel;
import cn.vastsky.libs.gdlocation.utils.CityUtils;
import cn.vastsky.libs.gdlocation.utils.GaodeCodeHelper;

/**
 * @author: kezy
 * @create_time 2019/11/7
 * @description:
 *   高德定位
 */
public class LocationManagerGaode implements ILocationManager, AMapLocationListener {


    private static final String TAG = ILocationManager.class.getSimpleName();
    private static final int DEFAULT_TIME_OUT = 30000;
    private static final int DEFAULT_REQUEST_INTERVAL = 1000 * 60 * 5; //5分钟获取一次

    private boolean mAutoUnregisterAfterLocation;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private int mIsAboard = LocationConstant.RangeType.INLAND;

    private ArrayList<LocationListener> mListenerList = new ArrayList<>();

    public LocationManagerGaode() {

    }



    @Override
    public void register(LocationListener listener) {
        register(listener, false);
    }



    @Override
    public void register(LocationListener listener, boolean autoUnregisterAfterLocation) {
        mListenerList.add(listener);
        mAutoUnregisterAfterLocation = autoUnregisterAfterLocation;
    }

    @Override
    public void unregister() {
        if (mLocationClient != null) {
            Log.d(TAG, " destrory location");
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }


    @Override
    public void stop() {
        if (mLocationClient != null) {
            Log.d(TAG, " stop location");
            mLocationClient.stopLocation();
        }
    }

    @Override
    public void locate(LocationType type, Context context) {
        if (mLocationClient == null) {
            initLocation(type, context);
        }
        mLocationClient.stopLocation();
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        Log.d(TAG, "onLocationChanged");
        if (mAutoUnregisterAfterLocation) {
            unregister();
        }
        if (mListenerList.size() == 0) {
            return;
        }

        if (location != null && location.getErrorCode() == 0) {
            LocationModel model = new LocationModel();
            // 经纬度测试状态
            if (LocationConfig.sLatTest != -1 && LocationConfig.sLngTest != -1) {

                model.latitude = LocationConfig.sLatTest;
                model.longitude = LocationConfig.sLngTest;
                setLocationSuccessInfo(model);
            } else {

                model.latitude = location.getLatitude();
                model.longitude = location.getLongitude();

                model.country = location.getCountry();
                model.address = location.getAddress();
                model.province = location.getProvince();
                model.city = location.getCity();
                model.district = location.getDistrict();
                model.street = location.getStreet();

                model.provinceCode = handleProviceCode(location.getAdCode());
                model.cityCode = location.getCityCode();
                model.districtCode = location.getAdCode();
                setLocationSuccessInfo(model);
                for (LocationListener listener : mListenerList) {
                    listener.onLocationFinished(true, model);
                }
            }
        } else {
            setLocationFailInfo();
            LogUtils.e("====msg_loaction", " 定位失败: error code: " + location.getErrorCode() + " , err info: " + location.getErrorInfo());
        }
    }

    //缓存一些基本定位信息
    private synchronized void setLocationSuccessInfo(LocationModel location) {
        if (location == null) {
            return;
        }
        Log.d(TAG, "onLocationChanged success");

        LocationConfig.sIsLocatedSuccess = true;
        mIsAboard = "中国".equals(location.country) ? LocationConstant.RangeType.INLAND : LocationConstant.RangeType.OUTLAND;
        LocationConfig.setCurrentCityIsAbroad(mIsAboard);

        LocationConfig.sLat = location.latitude;
        LocationConfig.sLng = location.longitude;

        LocationConfig.setLatitude(location.latitude);
        LocationConfig.setLongtitude(location.longitude);

        LocationConfig.setLocationAddress(location.address);
        LocationConfig.setLocationProvince(location.province);
        LocationConfig.setLocationCity((location.city));
        LocationConfig.setLocationDistrict(location.district);
        LocationConfig.setsLocationStreet((location.street));

        LocationConfig.setsLocationProvinceCode((location.provinceCode));
        LocationConfig.setLocationCityCode((location.cityCode));
        LocationConfig.setsLocationDistrictCode((location.districtCode));

        //更新高德码
        updateGaodeCode(location.districtCode, location.cityCode);
    }


    /**
     * 更新高德码，若为空，根据cityCode 查高德码
     *
     * @param adCode
     * @param cityCode
     */
    private void updateGaodeCode(String adCode, String cityCode) {
        if (TextUtils.isEmpty(adCode)) {
            GaodeCodeHelper helper = new GaodeCodeHelper();
            helper.loadCityAdCode(cityCode, new GaodeCodeHelper.OnLoadCodeListener() {
                @Override
                public void onCodeLoaded(String cityAdCode) {
                    updateAdCode(cityAdCode);
                }
            });
        } else {
            updateAdCode(adCode);
        }
    }

    /**
     * 更新高德码
     *
     * @param adCode
     */
    private void updateAdCode(String adCode) {
        LocationConfig.setAdCode(adCode);
        LocationConfig.setLocationProvinceAdCode(handleProviceCode(adCode));
        LocationConfig.setLocationCityAdCode(handleCityCode(adCode));

        //更新城市简称
        String locationCity = LocationConfig.getLocationDistrict();
        String locationCityAdcode = LocationConfig.getAdCode();

        if (!CityUtils.isLastCharacter(locationCity, "市") && !CityUtils.isLastCharacter(locationCity, "县")) {
            locationCityAdcode = LocationConfig.getLocationCityAdCode();
        }
        GaodeCodeHelper helper = new GaodeCodeHelper();
        final String finalLocationCityAdcode = locationCityAdcode;
        helper.loadShortName(locationCityAdcode, new GaodeCodeHelper.OnLoadShortNameListener() {
            @Override
            public void onShortNameLoaded(String name) {
                LocationConfig.setShortName(name);
                LocationConfig.setShortAdCode(finalLocationCityAdcode);
            }
        });
    }

    private void setLocationFailInfo() {

        Log.d(TAG, "onLocationChanged fail");

        LocationConfig.sIsLocatedSuccess = false;
        LocationConfig.setLocationAddress("");
        LocationConfig.setLocationProvince("");
        LocationConfig.setLocationCity("");
        LocationConfig.setLocationDistrict("");
        LocationConfig.setsLocationStreet("");

        LocationConfig.setsLocationProvinceCode("");
        LocationConfig.setLocationCityCode("");
        LocationConfig.setsLocationDistrictCode("");

        LocationConfig.setLocationProvinceAdCode("");
        LocationConfig.setLocationCityAdCode("");
        LocationConfig.setAdCode("");
        LocationConfig.setShortName("");
        LocationConfig.setShortAdCode("");
    }


    /**
     * 初始化定位参数
     *
     * @param type
     * @param context
     */
    private void initLocation(LocationType type, Context context) {
        //初始化client
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        mLocationOption = getLocationOption(type);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 设置定位监听
        mLocationClient.setLocationListener(this);
    }


    /**
     * 初始化定位参数
     *
     * @param type
     * @return
     */
    private AMapLocationClientOption getLocationOption(LocationType type) {
        AMapLocationClientOption mOption = new AMapLocationClientOption();

        AMapLocationClientOption.AMapLocationMode mode;
        switch (type) {
            case GPS_ONLY:
                mode = AMapLocationClientOption.AMapLocationMode.Device_Sensors; //不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
                break;
            case NETWORK_ONLY:
                mode = AMapLocationClientOption.AMapLocationMode.Battery_Saving; //不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）
                break;
            case MULTY:
                mode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy; //会同时使用网络定位和GPS定位，优先返回最高精度的定位结果
                break;
            default:
                mode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
                break;
        }
        mOption.setLocationMode(mode);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(DEFAULT_TIME_OUT);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(DEFAULT_REQUEST_INTERVAL);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 根据高德码计算 省份的编码
     *
     * @param adCode
     * @return
     */
    private String handleProviceCode(String adCode) {
        if (TextUtils.isEmpty(adCode) || adCode.length() < 2) {
            return "";
        }
        return adCode.substring(0, 2) + "0000";
    }

    /**
     * 根据高德码计算  城市的编码
     *
     * @param adCode
     * @return
     */
    private String handleCityCode(String adCode) {
        if (TextUtils.isEmpty(adCode) || adCode.length() < 4) {
            return "";
        }
        return adCode.substring(0, 4) + "00";
    }

}
