package cn.kezy.libs.common.config;

import android.content.Context;


public class AppConfigLib {

    private static Context sContext; //Application的context
    private static Context currentContext; //当前activity的context

    private static String p_value; // p 值
    private static String channel; // p 值

    private static boolean ckFlag; // 是否开启防审核

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    public static void setCurrentActivityContext(Context context) {
        currentContext = context;
    }

    public static Context getCurrentActivityContext() {
        return currentContext;
    }

    public static String getP_value() {
        return p_value;
    }

    public static void setP_value(String p_value) {
        AppConfigLib.p_value = p_value;
    }

    public static String getChannel() {
        return channel;
    }
    public static void setChannel(String channel) {
        AppConfigLib.channel = channel;
    }

    public static boolean isCkFlag() {
        return ckFlag;
    }

    public static void setCkFlag(boolean ckFlag) {
        AppConfigLib.ckFlag = ckFlag;
    }
}
