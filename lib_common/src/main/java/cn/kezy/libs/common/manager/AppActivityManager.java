package cn.kezy.libs.common.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

import static android.content.Context.ACTIVITY_SERVICE;


public class AppActivityManager {

    private static Stack<Activity> mStack;
    private static AppActivityManager mAppCompatActivity;

    private AppActivityManager() {

    }

    public static AppActivityManager getInstance() {
        if (mAppCompatActivity == null) {
            mAppCompatActivity = new AppActivityManager();
        }
        return mAppCompatActivity;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mStack == null) {
            mStack = new Stack<>();
        }
        mStack.add(activity);
    }

    /**
     * 移除Activity到堆外
     */
    public void removeActivity(Activity activity) {
        if (mStack != null) {
            mStack.remove(activity);
        }
    }

    /**
     * 获取栈顶Activity
     */
    public Activity getTopActivity() {
        if (mStack != null) {
            return mStack.lastElement();
        } else {
            return null;
        }
    }

    /**
     * 结束栈顶Activity
     */
    public void killTopActivity() {
        if (mStack != null) {
            Activity activity = mStack.lastElement();
            killActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            if (mStack != null) {
                mStack.remove(activity);
            }
        }
        activity.finish();
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivityWithName(Class<?> cls) {
        if (mStack != null) {
            Iterator<Activity> iterator = mStack.iterator();
            while (iterator != null && iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity.getClass().equals(cls)) {
                    iterator.remove();
                    activity.finish();
                }
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        int size = mStack.size();
        for (int i = 0; i < size; i++) {
            if (null != mStack.get(i)) {
                mStack.get(i).finish();
            }
        }
        mStack.clear();
    }


    public Stack<Activity> getActivitys() {
        if (mStack != null && !mStack.isEmpty()) {
            return mStack;
        }
        return null;
    }

    /**
     * 判断activity是否存在
     *
     * @param cls
     * @return
     */
    public boolean isActivityExsit(Class<?> cls) {
        boolean flag = false;
        Stack<Activity> stack = getActivitys();
        if (stack == null || stack.empty()) {
            flag = false;
        } else {
            for (Activity activity : stack) {
                if (activity.getClass().equals(cls)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 从栈底开始， 向上 取 activity
     *
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        Activity needActivity = null;
        Stack<Activity> stack = getActivitys();
        if (stack == null || stack.empty()) {
            return null;
        } else {
            for (Activity activity : stack) {
                if (activity.getClass().equals(cls)) {
                    needActivity = activity;
                }
            }
        }
        return needActivity;
    }

    /**
     * 将 栈内某个activity 所有类都取到一个栈
     *
     * @param cls
     * @return
     */
    public Stack getActivityStack(Class<?> cls) {
         Stack<Activity> activityStack = new Stack<>();
        Stack<Activity> stack = getActivitys();
        if (stack == null || stack.empty()) {
            return null;
        } else {
            for (Activity activity : stack) {
                if (activity.getClass().equals(cls)) {
                    activityStack.add(activity);
                }
            }
        }
        return activityStack;
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            killAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            Log.e("AppActivityManager", "" + e);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

}
