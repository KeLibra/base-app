package cn.vastsky.libs.common.config;

import cn.vastsky.libs.common.VsConstants;

/**
 * @author: kezy
 * @create_time 2019/11/26
 * @description:
 */
public class UrlConfig {
    public static String BaseUrl = "https://api-mall.blshop1.com/";

    public static void setEnv(int env) {
        switch (env) {
            case VsConstants.NetWork.PRD://生产
                BaseUrl = "https://api-mall.blshop1.com/";
                break;
            case VsConstants.NetWork.SIT://测试
                BaseUrl = "http://api-mall.sit.mizar8.com";//  测试环境
                break;

            default:
                BaseUrl = "https://api-mall.blshop1.com/";
                break;
        }
    }

}
