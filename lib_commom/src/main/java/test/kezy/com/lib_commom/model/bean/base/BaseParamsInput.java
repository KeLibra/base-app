package test.kezy.com.lib_commom.model.bean.base;

import cn.vastsky.lib.base.common.base.bean.BaseBean;

/**
 * @author dingzhongsheng
 * @copyright blackfish
 * @date 2018/7/28.
 */

public class BaseParamsInput extends BaseBean {

    public DeviceInfo deviceInfo;
    public int source;

    public static class DeviceInfo {

        public String deviceType; // ios或android
        public String platformId; //  ios, android, h5
        public String sysVersion; //  系统版本
        public String deviceModel; //  设备型号
        public String deviceId; //  设备id
        public String appVersion; //  应用版本号 1.1.0 versionname
        public int appBuildVersion; //  应用内部版本号 1100  versioncode
        public String appType; //  固定传1
        public String pValue; //  应用 p 值

        public Position position; // 经纬度

        public String channel; // 渠道号

        public static class Position {
            public String lat;
            public String lon;
        }
    }
}
