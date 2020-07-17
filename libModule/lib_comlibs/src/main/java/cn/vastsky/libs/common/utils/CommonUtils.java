package cn.vastsky.libs.common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.vastsky.lib.utils.LogUtils;

/**
 * @author: kezy
 * @create_time 2019/11/16
 * @description:
 */
public class CommonUtils {

    /**
     * 事件戳 转 时分秒
     *
     * @param value
     * @return
     */
    public static String formatTime(long value) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(" HH:mm:ss");
            String time = format.format(new Date(value * 1000L));
            return time;
        } catch (Exception e) {
            LogUtils.e("===== err: " + e.toString());
            e.printStackTrace();
        }
        return "00:00:00";
    }


    public static String secondToTime(long second) {
        try {
            long days = second / 86400;//转换天数
            second = second % 86400;//剩余秒数
            long hours = second / 3600;//转换小时数
            second = second % 3600;//剩余秒数
            long minutes = second / 60;//转换分钟
            second = second % 60;//剩余秒数
            if (0 < days) {
                return formatNumber("00", days) + "：" + formatNumber("00", hours) + "：" + formatNumber("00", minutes) + "：" + formatNumber("00", second);
            } else {
                return formatNumber("00", hours) + "：" + formatNumber("00", minutes) + "：" + formatNumber("00", second);
            }
        } catch (Exception e) {
            LogUtils.e("===== err: " + e.toString());
            e.printStackTrace();
        }
        return "00:00:00";
    }

    /**
     * 手机上是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {

        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 数字格式化
     *
     * @param pattern 格式化， 例如 000  或 0000,000 或 0.00 等
     * @param number
     * @return
     */
    public static String formatNumber(String pattern, long number) {
        try {
            DecimalFormat mFormat = new DecimalFormat(pattern);//确定格式，把1转换为001
            String formatNum = mFormat.format(number);
            return formatNum;
        } catch (Exception e) {
            LogUtils.e("--------err: " + e.toString());

        }
        return "";
    }
}
