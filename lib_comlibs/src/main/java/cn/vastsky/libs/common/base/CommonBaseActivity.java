package cn.vastsky.libs.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.libs.common.R;
import cn.vastsky.libs.common.manager.AppActivityManager;
import cn.vastsky.libs.common.reten.ActivityConfig;
import cn.vastsky.libs.common.utils.EventBusUtils;
import cn.vastsky.libs.common.view.base.DefaultErrorPageView;
import cn.vastsky.libs.common.view.base.DefaultLoadingDialog;
import cn.vastsky.libs.common.view.base.DefaultTitleView;
import cn.vastsky.libs.common.view.impl.IErrorPageView;
import cn.vastsky.libs.common.view.impl.ILoadingDialog;
import cn.vastsky.libs.common.view.impl.ITitleView;
import cn.vastsky.libs.common.view.statusbar.ImmersionBar;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description: 基类Activity
 * <p>
 * * 1. 该基类规定了Activity初始化流程:
 * * # getContentLayout
 * * # getIntentData
 * * # initHeaderView
 * * # initContentView
 * * # initFooterView
 * * # initData
 * * <p>
 * * 2. 定义了一些公共UI:
 * * # Loading弹窗
 * * # 状态栏
 * * # 标题栏
 * * # 错误页
 */
public abstract class CommonBaseActivity extends FragmentActivity {

    protected FragmentActivity mActivity;
    protected View mRootLayout;

    /**
     * 设置layout
     */
    protected abstract int getContentLayout();

    /**
     * 自定义加载弹窗
     */
    protected ILoadingDialog getLoadingDialog() {
        return null;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initActivityConfig();
        super.onCreate(savedInstanceState);

        AppConfigLib.setCurrentActivityContext(this);

        AppActivityManager.getInstance().addActivity(this);
        LogUtils.i("------------------ " + this.getClass().getSimpleName());
        mActivity = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getIntentData(savedInstanceState);
        getIntentData();
        doBeforeLayoutInit();
//        int layoutId = hasNestedContent() ? R.layout.lib_activity_base : getContentLayout();
//        mRootLayout = LayoutInflater.from(this).inflate(layoutId, null);
//        setContentView(mRootLayout);
        initEventBus();
        int layoutId = (isHasErrorPageView || isHasTitleView()) ? R.layout.lib_activity_base : getContentLayout();
        mRootLayout = LayoutInflater.from(this).inflate(layoutId, null);
        setContentView(mRootLayout);

        initContent();
        initTitleView();
        initErrorPageView();
        initProgressDialog();

        initContentView(savedInstanceState);
        initContentView();
        initFooterView();

        initStatusBar();
        initData();

    }

    protected void doBeforeLayoutInit() {

    }

    //标记是否支持EventBus
    private boolean isRegisterEventBus = false; // 默认不开
    private boolean isHasErrorPageView = false; // 默认没有网络异常页
    private boolean isHasTitleView = true;      // 默认有标题
    private String titleDesc;

    /**
     * 初始化 activity 一些配置
     */
    private void initActivityConfig() {
        if (getClass().isAnnotationPresent(ActivityConfig.class)) {
            ActivityConfig activityConfig = getClass().getAnnotation(ActivityConfig.class);
            isRegisterEventBus = activityConfig != null ? activityConfig.isRegisterEventBus() : false;
            isHasErrorPageView = activityConfig != null ? activityConfig.isHasErrorPageView() : false;
            isHasTitleView = activityConfig != null ? activityConfig.isHasTitleView() : true;
            titleDesc = activityConfig != null ? activityConfig.titleDesc() : getString(R.string.app_name);
        }
        LogUtils.d("-------------activity config - isRegisterEventBus = " + isRegisterEventBus);
        LogUtils.d("-------------activity config - isHasErrorPageView = " + isHasErrorPageView);
        LogUtils.d("-------------activity config - isHasTitleView = " + isHasTitleView + ", title = " + titleDesc);
    }

    private void initContent() {
        if (isHasTitleView() || isHasErrorPageView) {
            mNestedContainer = findViewById(R.id.lib_nested_content);
            View nestedView = LayoutInflater.from(this).inflate(
                    getContentLayout(), mNestedContainer, false);
            mNestedContainer.addView(nestedView);
        }
    }

    /**
     * 初始计划EventBus
     */
    protected void initEventBus() {
//        isRegisterEventBus = registerEventBus();
        if (isRegisterEventBus) {
            EventBusUtils.register(this);
        }
    }


    /* -- 通用标题栏 ------------------- */
    public ITitleView mTitleView; // 标题栏UI实现
    protected FrameLayout mTitleFl; // 标题栏容器

    /**
     * 初始化标题栏
     */
    protected void initTitleView() {
        if (!isHasTitleView()) { // 不开的话就不初始化
            return;
        }

        mTitleView = getTitleView() == null ? new DefaultTitleView(this) : getTitleView();
        mTitleFl = findViewById(R.id.lib_layout_header);
        mTitleFl.setVisibility(View.VISIBLE);
        mTitleFl.removeAllViews();
        mTitleView.attachTo(mTitleFl);

        View titleBack = mTitleView.getBackView();
        TextView titleTv = mTitleView.getTextView();
        if (titleBack != null) {
            titleBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBack();
                    if (!onTitleBack()) {
                        finish();
                    }
                }
            });
        }
        titleTv.setText(titleDesc);
    }

    /**
     * 包含安卓返回键和左上角返回键
     */
    protected void onBack() {
    }

    /**
     * 标题返回按钮点击事件
     *
     * @return 如果返回事件被消耗返回true, 否则返回false交由BaseActivity处理
     */
    protected boolean onTitleBack() {
        return false;
    }

    /**
     * 自定义标题栏
     */
    protected ITitleView getTitleView() {
        return null;
    }


    protected boolean isHasTitleView() {
        return isHasTitleView;
    }


    protected void getIntentData(Bundle outState) {
    }

    protected void getIntentData() {
    }


    protected ILoadingDialog mLoadingDialog;

    public void initProgressDialog() {
        if (getLoadingDialog() != null) {
            mLoadingDialog = getLoadingDialog();
        } else {
            DefaultLoadingDialog dialog = new DefaultLoadingDialog(this, R.style.MyDialog);
            // 默认不可关闭dialog
            dialog.setCanceledOnTouchOutside(false);
            mLoadingDialog = dialog;
        }
    }

    // 默认不可关闭dialog
    public synchronized void showProgressDialog(String loadingMsg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog == null) {
                    initProgressDialog();
                }
                if (!mLoadingDialog.isShowing() && !isFinishing()) {
                    try {
                        ((DefaultLoadingDialog) mLoadingDialog).setMessage(loadingMsg);
                        mLoadingDialog.show();
                    } catch (WindowManager.BadTokenException ignored) {
                    }
                }
            }
        });
    }

    // 默认不可关闭dialog
    public synchronized void showProgressDialog(final ILoadingDialog dialog) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingDialog = dialog;
                if (!mLoadingDialog.isShowing() && !isFinishing()) {
                    try {
                        mLoadingDialog.show();
                    } catch (WindowManager.BadTokenException ignored) {
                    }
                }
            }
        });
    }

    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }


    protected void initContentView() {

    }

    protected void initContentView(Bundle savedInstanceState) {
    }

    protected void initFooterView() {
    }

    protected ImmersionBar mStatusBar;

    protected void initStatusBar() {

        mStatusBar = ImmersionBar.with(this);

        if (mTitleView != null) {
            mStatusBar.titleBar(mTitleView.getView());
        }
        mStatusBar.statusBarDarkFont(true, 1).init();
    }

    protected void destroyStatusBar() {
        if (mStatusBar != null) {
            mStatusBar.destroy();
        }
    }

    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        LogUtils.d("-------------------------onBackPressed      + commonBase ");
        onBack();
        super.onBackPressed();
    }


    /* -- 通用网络请求异常page ------------------- */
    protected FrameLayout mNestedContainer;
    protected FrameLayout mErrorPageFl; // 错误页容器
    protected IErrorPageView mErrorPageView; // 错误页UI实现

    /**
     * 初始化通用err 布局
     */
    private void initErrorPageView() {
        // 没有 err 布局，则不初始化
        if (!isHasErrorPageView) {
            return;
        }
        // 填充错误页
        mErrorPageView = getErrorPageView();

        mErrorPageFl = findViewById(R.id.lib_layout_network_error);
        mErrorPageFl.removeAllViews();
        mErrorPageFl.addView(mErrorPageView.getView());
        mErrorPageView.setOnRefreshListener(new IErrorPageView.OnRefreshListener() {
            @Override
            public void onRefreshBtnClick() {
                if (isHasErrorPageView) {
                    onErrorRefresh();
                }
            }
        });
    }

    /**
     * 自定义错误页
     */
    protected IErrorPageView getErrorPageView() {
        return new DefaultErrorPageView(mActivity);
    }

    /**
     * 点击错误页刷新按钮时的回调
     */
    protected void onErrorRefresh() {
    }

    /**
     * 显示错误页, 隐藏内容页
     */
    protected void showErrorPage(int errorCode, String errMsg) {

        LogUtils.d("====msg  has ", "---- err: " + errorCode);
        if (!isHasErrorPageView) {
            return;
        }

        LogUtils.d("====msg", "---- err: " + errorCode + " ,errMsg  = " + errMsg);
        mErrorPageFl.setVisibility(View.VISIBLE);
        mNestedContainer.setVisibility(View.GONE);
        mErrorPageView.showErrorMsg(errorCode, errMsg);
    }

    /**
     * 显示内容页, 隐藏错误页
     */
    protected void showContent() {
        if (!isHasErrorPageView) {
            return;
        }
        mErrorPageFl.setVisibility(View.GONE);
        mNestedContainer.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LogUtils.d("-------------------------onKeyDown       + commonBase ");
            if (isHasTitleView() && onTitleBack()) {
                onBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("------------------ " + this.getClass().getSimpleName());
        destroyStatusBar();
        AppActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 传递onActivityResult
     */
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.i("------------------ " + this.getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i("------------------ " + this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭输入法键盘，如果需要
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        LogUtils.i("------------------ " + this.getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("------------------ " + this.getClass().getSimpleName());
    }
}
