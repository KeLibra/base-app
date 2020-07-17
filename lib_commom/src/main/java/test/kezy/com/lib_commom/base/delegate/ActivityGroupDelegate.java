package test.kezy.com.lib_commom.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;
import cn.vastsky.lib.utils.LogUtils;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * 首页容器代理
 */

public class ActivityGroupDelegate {

    private static final String STATES_KEY = "android:states";

    private LocalActivityManager mLocalActivityManager;
    private FragmentActivity mActivity;

    public LocalActivityManager getLocalActivityManager() {
        return mLocalActivityManager;
    }

    public ActivityGroupDelegate(FragmentActivity activity) {
        mActivity = activity;
        try {
            mLocalActivityManager = new LocalActivityManager(new WeakReference<Activity>(mActivity).get(), true);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
//        Bundle states = bundle != null
//                ? (Bundle) bundle.getBundle(STATES_KEY) : null;
        mLocalActivityManager.dispatchCreate(null);
        activity.getApplication().registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks(activity, mLocalActivityManager));
    }

    private static class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        private WeakReference<Activity> activityWeakReference;
        private WeakReference<LocalActivityManager> localActivityManagerWeakReference;

        MyActivityLifecycleCallbacks(Activity activity, LocalActivityManager localActivityManager) {
            activityWeakReference = new WeakReference<>(activity);
            localActivityManagerWeakReference = new WeakReference<>(localActivityManager);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (activityWeakReference.get() == activity) {
                if (localActivityManagerWeakReference != null && localActivityManagerWeakReference.get() != null) {
                    localActivityManagerWeakReference.get().dispatchResume();
                }
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (activityWeakReference.get() == activity) {
                if (localActivityManagerWeakReference != null && localActivityManagerWeakReference.get() != null) {
                    localActivityManagerWeakReference.get().dispatchPause(activityWeakReference.get().isFinishing());
                }
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activityWeakReference.get() == activity) {
                if (localActivityManagerWeakReference != null && localActivityManagerWeakReference.get() != null) {
                    localActivityManagerWeakReference.get().dispatchStop();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            if (activityWeakReference.get() == activity && localActivityManagerWeakReference.get() != null) {
                Bundle state = localActivityManagerWeakReference.get().saveInstanceState();
                if (state != null) {
                    bundle.putBundle(STATES_KEY, state);
                }
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activityWeakReference.get() == activity) {
                if (localActivityManagerWeakReference != null && localActivityManagerWeakReference.get() != null) {
                    localActivityManagerWeakReference.get().removeAllActivities();
                }
            }
        }
    }

    public void startChildActivity(ViewGroup container, String key, Intent intent) {
        try {
            //移除内容部分全部的View
            container.removeAllViews();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);

            Activity contentActivity = mLocalActivityManager.getActivity(key);
            if (contentActivity != null) {
                container.removeAllViews();
                container.addView(
                        contentActivity.getWindow().getDecorView(),
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                mLocalActivityManager.startActivity(key, intent);
            } else {
                execStartChildActivityInternal(container, key, intent);
            }

        } catch (Exception e) {
            LogUtils.e("------- err:  mLocalActivityManager.getActivity(key)  " + mLocalActivityManager.getActivity(key));
            LogUtils.e("------- err:   " + e.toString());
            e.printStackTrace();
        }
    }

    private void performLaunchChildActivity(ViewGroup container, String key, Intent intent) {
        if (intent == null) {
            LogUtils.e("====msg _ ActivityGroupDelegate", "intent is null stop performLaunchChildActivity");
            return;
        }
        mLocalActivityManager.startActivity(key, intent);
        container.addView(
                mLocalActivityManager.getActivity(key)
                        .getWindow().getDecorView(),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void execStartChildActivityInternal(ViewGroup container, String key, Intent intent) {
        String packageName = null;
        String componentName = null;
        Context context = container.getContext();
        if (intent.getComponent() != null) {
            packageName = intent.getComponent().getPackageName();
            componentName = intent.getComponent().getClassName();
        } else {
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo != null && resolveInfo.activityInfo != null) {
                packageName = resolveInfo.activityInfo.packageName;
                componentName = resolveInfo.activityInfo.name;
            }
        }
        if (componentName == null) {
            LogUtils.e("====msg _ActivityGroupDelegate", "can not find componentName");
        }
        if (!TextUtils.equals(context.getPackageName(), packageName)) {
            LogUtils.e("====msg _ActivityGroupDelegate", "childActivity can not be external Activity");
        }
        // Try to get class from system Classloader
        try {
            Class<?> clazz = null;
            clazz = getClass().getClassLoader().loadClass(componentName);
            if (clazz != null) {
                performLaunchChildActivity(container, key, intent);
            }
        } catch (ClassNotFoundException e) {
            LogUtils.e("====msg _ActivityGroupDelegate", e.getMessage());
        }
    }
}
