package cn.vastsky.onlineshop.installment.model.bean.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.vastsky.lib.base.common.config.AppConfigLib;
import cn.vastsky.lib.utils.JsonUtils;
import cn.vastsky.lib.utils.LogUtils;
import cn.vastsky.lib.base.common.base.bean.BaseBean;
import cn.vastsky.lib.base.common.config.userinfo.LoginFacade;
import cn.vastsky.lib.utils.AppInfoUtils;
import cn.vastsky.lib.utils.SystemInfoUtils;
import cn.vastsky.libs.gdlocation.config.LocationConfig;
import cn.vastsky.onlineshop.installment.common.net.util.MD5;
import cn.vastsky.onlineshop.installment.common.net.util.SecurityUtils;

/**
 *
 */
public class BaseRequest<T> extends BaseBean {
    public String bizParams;
    public String timestamp;
    public String sign;
    public String baseParams;
    public String token;


    public BaseRequest(T bizParams) {
        this.bizParams = translateBizParams(bizParams);
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.baseParams = addBaseParams();
        this.token = LoginFacade.getToken();
//        this.sign = "123456";
        this.sign = addMD5Sign();
    }

    private String translateBizParams(T bizParams) {
        String bizParamsStr = JsonUtils.obj2JsonStr(bizParams);
        LogUtils.d("---------request: bizParamsStr: " + bizParamsStr);
        return bizParamsStr;
    }

    private String addBaseParams() {

        BaseParamsInput baseParam = new BaseParamsInput();
        baseParam.deviceInfo = getDeciceInfo();
        baseParam.source = 1;

        String baseParamStr = JsonUtils.obj2JsonStr(baseParam);
        LogUtils.d("---------request:  baseParamStr:  " + baseParamStr);

        return baseParamStr;
    }

    public BaseParamsInput.DeviceInfo getDeciceInfo() {
        BaseParamsInput.DeviceInfo devicesInfos = new BaseParamsInput.DeviceInfo();

        devicesInfos.platformId = "android";
        devicesInfos.deviceType = "android";
        devicesInfos.appType = "1";
        devicesInfos.appVersion = AppInfoUtils.getVersionName();
        devicesInfos.appBuildVersion = AppInfoUtils.getVersionCode();

        devicesInfos.pValue = AppConfigLib.getP_value();

        devicesInfos.sysVersion = SystemInfoUtils.getSystemVersion();
        devicesInfos.deviceModel = SystemInfoUtils.getSystemModel();
        devicesInfos.deviceId = SystemInfoUtils.getIMEI(AppConfigLib.getContext());

        BaseParamsInput.DeviceInfo.Position position = new BaseParamsInput.DeviceInfo.Position();
        devicesInfos.position = position;
        devicesInfos.position.lat = String.valueOf(LocationConfig.getLatitude());
        devicesInfos.position.lon = String.valueOf(LocationConfig.getLongtitude());
        devicesInfos.channel = AppConfigLib.getChannel();

        return devicesInfos;
    }

    private String addMD5Sign() {
        String unSignStr = baseParams + bizParams + timestamp;
        String signedStr = MD5.sign(unSignStr, NEW_KEY);
        return signedStr;
    }


    /**
     * 加签
     */
    private String addSign() {
        HashMap<String, String> sPara = new HashMap<>();
        if (bizParams != null) {
            Map<String, String> bizMap = toFlatMap(bizParams);
            sPara.putAll(bizMap);
        }
        sPara.put("timestamp", timestamp);
        return SecurityUtils.genSignMsg(sPara, KEY);
    }

    private static final Gson GSON = (new GsonBuilder()).create();

    private Map<String, String> toFlatMap(Object object) {
        HashMap<String, String> map = new HashMap<>();
        try {
            String jsonString = GSON.toJson(object);
            map = jsonToMap(jsonString);
        } catch (OutOfMemoryError var3) {
            LogUtils.e("===msg_ JsonUtils OutOfMemoryError" + var3.getMessage());
        }

        return map;
    }

    private HashMap<String, String> jsonToMap(String value) {
        HashMap<String, String> sPara = new HashMap<>();
        if (TextUtils.isEmpty(value)) {
            return sPara;
        } else {
            try {
                JSONObject jsonObj = new JSONObject(value);
                Iterator keyIter = jsonObj.keys();
                while (keyIter.hasNext()) {
                    String subKey = (String) keyIter.next();
                    String subVal = jsonObj.getString(subKey);
                    if (!TextUtils.isEmpty(subVal)) {
                        sPara.put(subKey, subVal);
                    }
                }
            } catch (JSONException var6) {
                LogUtils.e("====msg_ JSONException: " + var6);
            }
            return sPara;
        }
    }

    private static final String KEY = "Lnb*z5KRdQ5l$hw7";
    public static final String NEW_KEY = "9d54a30e-badf-42eb-a3e8-a6555178b4fb";

}
