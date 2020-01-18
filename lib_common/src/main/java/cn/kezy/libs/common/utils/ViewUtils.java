package cn.kezy.libs.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;

import cn.kezy.libs.common.config.AppConfigLib;


/**
 * @author: kezy
 * @create_time 2019/10/31
 * @description:
 */
public class ViewUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 获取顶部statusBar高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Resources resources = AppConfigLib.getContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取底部navigationBar高度
     *
     * @return
     */

    public static int getNavigationBarHeight() {

        if (checkDeviceHasNavigationBar()) {
            Resources resources = AppConfigLib.getContext().getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }


    /**
     * 获取屏幕 高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = AppConfigLib.getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * 获取屏幕 宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = AppConfigLib.getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }


    /**
     * 获取设备是否存在NavigationBar
     *
     * @return
     */
    public static boolean checkDeviceHasNavigationBar() {
        boolean hasNav = false;
        // 通过WindowManagerGlobal 获取WindowManagerService 反射WindowManagerGlobal.getWindowManagerService();
        try {
            Class<?> windowManagerGlobalClass = Class.forName("android.view.WindowManagerGlobal");
            // 获取WindowManagerGlobal 中的私有方法
            Method getWindowManagerService = windowManagerGlobalClass.getDeclaredMethod("getWindowManagerService");
            getWindowManagerService.setAccessible(true);
            //getWindowManagerService 是静态方法
            Object iwindowManager = getWindowManagerService.invoke(null);

            // 获取windowManagerService 的hasNavigationBar 方法的返回值
            Class<?> aClass = iwindowManager.getClass();
            Method hasNavigationBar = aClass.getDeclaredMethod("hasNavigationBar");
            hasNavigationBar.setAccessible(true);
            hasNav = (boolean) hasNavigationBar.invoke(iwindowManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNav;
    }
}
