package cn.vastsky.onlineshop.installment.model.bean;

import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.libs.common.base.bean.BaseBean;
import cn.vastsky.libs.common.utils.AppInfoUtils;
import cn.vastsky.libs.common.utils.SystemInfoUtils;
import cn.vastsky.libs.common.utils.ViewUtils;
import cn.vastsky.libs.gdlocation.config.LocationConfig;

/**
 * h5 用的baseparams
 */

public class WebDeviceInfo extends BaseBean {

    public int source;
    public String deviceType; // ios或android
    public String platformId; //  ios, android, h5
    public String sysVersion; //  系统版本
    public String deviceModel; //  设备型号
    public String deviceId; //  设备id
    public String appVersion; //  应用版本号 1.1.0 versionname
    public int appBuildVersion; //  应用内部版本号 1100  versioncode
    public String appType; //  固定传1
    public String pValue; //  应用 p 值
    public String channel; //  应用 渠道值

    public Position position; // 经纬度

    public int navHeight; // 导航栏高度(包括状态栏高度)
    public int statusBarHeight; // 导航栏高度(包括状态栏高度)
    public int bottomHeight; // 导航栏高度(包括状态栏高度)


    public static class Position {
        public String lat;
        public String lon;
    }

    public WebDeviceInfo() {
        addValues();
    }

    private void addValues() {

        this.platformId = "android";
        this.deviceType = "android";
        this.appType = "1";
        this.appVersion = AppInfoUtils.getVersionName();
        this.appBuildVersion = AppInfoUtils.getVersionCode();

        this.pValue = AppConfigLib.getP_value();

        this.sysVersion = SystemInfoUtils.getSystemVersion();
        this.deviceModel = SystemInfoUtils.getSystemModel();
        this.deviceId = SystemInfoUtils.getIMEI(AppConfigLib.getContext());

        Position position = new Position();
        this.position = position;
        position.lat = String.valueOf(LocationConfig.getLatitude());
        position.lon = String.valueOf(LocationConfig.getLongtitude());


        this.source = 1;
        this.statusBarHeight = ViewUtils.px2dip(AppConfigLib.getContext(), ViewUtils.getStatusBarHeight());
        this.navHeight = 0;
        this.bottomHeight = 0;
        this.channel = AppConfigLib.getChannel();
    }
}
