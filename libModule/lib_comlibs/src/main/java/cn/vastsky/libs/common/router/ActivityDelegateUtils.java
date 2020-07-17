package cn.vastsky.libs.common.router;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import cn.vastsky.lib.utils.LogUtils;
import cn.vastsky.libs.common.base.delegate.ActivityGroupDelegate;

/**
 * @author: kezy
 * @create_time 2019/11/11
 * @description:
 */
public class ActivityDelegateUtils {


    private static ActivityGroupDelegate groupDelegate;
    private static FragmentActivity mActivity;
    static Map<ACTIVITY_TYPE, String> activityMap;

    private static ActivityDelegateUtils utils;

    public static Activity getCurrentActivity() {

        if (groupDelegate != null) {
            return groupDelegate.getLocalActivityManager().getCurrentActivity();
        }
        LogUtils.e("=====msg err ActivityGroupDelegate current activity == null");
        return null;
    }


    public static ActivityDelegateUtils init(FragmentActivity activity) {
        mActivity = activity;
        if (groupDelegate == null) {
            groupDelegate = new ActivityGroupDelegate(mActivity);
            initActivityMap();
        }

        if (utils == null) {
            utils = new ActivityDelegateUtils();
        }
        return utils;
    }

    private static void initActivityMap() {
        activityMap = new HashMap<>();
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_HOME, CLASS_PAGE_HOME);
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_ORDER, CLASS_PAGE_ORDER);
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_MINE, CLASS_PAGE_MINE);
        activityMap.put(ACTIVITY_TYPE.ACTIVITY_TYPE_PAGE_WEB, CLASS_PAGE_WEB);
    }

    public void switchToActivity(ViewGroup fl_layout, ACTIVITY_TYPE activityType, String url, String parameters) {
        Intent intent = new Intent();
        String activityName = activityMap.get(activityType);
        if (TextUtils.isEmpty(activityName)) {
            LogUtils.e("====msg switch activity name = " + activityName);
            return;
        }
        LogUtils.d("====msg class name: " + activityName);
        intent.setClassName(mActivity.getBaseContext(), activityName);
        intent.putExtra("params", parameters);
        intent.putExtra("h5_url", url);
        groupDelegate.startChildActivity(fl_layout, activityName + activityType, intent);
    }

    // 跳转类型
    public enum ACTIVITY_TYPE {
        ACTIVITY_TYPE_PAGE_HOME, // 首页
        ACTIVITY_TYPE_PAGE_ORDER, // 订单页
        ACTIVITY_TYPE_PAGE_MINE, // 我的页面
        ACTIVITY_TYPE_PAGE_WEB, // web页面
    }

    // 页面地址
    private static final String CLASS_PAGE_HOME = "cn.vastsky.onlineshop.installment.view.activity.HomepageActivity";
    private static final String CLASS_PAGE_ORDER = "cn.vastsky.onlineshop.installment.base.web.VsWebviewActivity";
    private static final String CLASS_PAGE_MINE = "cn.vastsky.onlineshop.installment.view.activity.MinePageActivity";
    private static final String CLASS_PAGE_WEB = "";
}
