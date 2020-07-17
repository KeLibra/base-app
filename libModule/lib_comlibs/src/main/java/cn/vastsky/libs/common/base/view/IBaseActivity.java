package cn.vastsky.libs.common.base.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.vastsky.lib.utils.LogUtils;
import cn.vastsky.lib.utils.ToastUtil;
import cn.vastsky.lib.base.common.base.CommonBaseActivity;
import cn.vastsky.lib.reten.ActivityConfig;
import cn.vastsky.lib.utils.PermissionUtils;
import cn.vastsky.lib.base.common.view.defaultview.DefaultTitleView;
import cn.vastsky.libs.common.base.impl.IBasePresenter;
import cn.vastsky.libs.common.base.impl.IBaseView;

import static android.view.View.OnClickListener;


/**
 * 基础activity
 *
 * @param <P> 对应的presenter接口
 * @author dingzhongsheng
 */
@ActivityConfig(isHasTitleView = true)
public abstract class IBaseActivity<P extends IBasePresenter> extends CommonBaseActivity implements IBaseView {
    protected P presenter;
    private boolean isForeground;

    private Unbinder unbinder;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        super.initContentView(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        presenter = getPresenter();
        initView();

    }

    @Override
    protected void initData() {
        super.initData();
        loadData();
    }


    protected boolean hasBack() {
        return true;
    }

    protected DefaultTitleView fmTitleView = new DefaultTitleView(this);

    @Override
    protected DefaultTitleView getTitleView() {
        return fmTitleView;
    }

    protected void setBack(final OnClickListener onClickListener) {
        if (fmTitleView != null && fmTitleView.getBackView() != null) {
            fmTitleView.getBackView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v);
                }
            });
        }
    }

    protected String getPageName() {
        return null;
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
        if (fmTitleView != null ) {
            if (!hasBack()) {
                fmTitleView.setImageBackShow(false);
            }
            if (getPageName() != null) {
                fmTitleView.setTitle(getPageName());
            }
        }
    }

    protected void setTitle(String title) {
        if (fmTitleView != null  && !TextUtils.isEmpty(title)) {
            fmTitleView.setTitle(title);
        }
    }


    protected void setImageBackIconRes(int rightIconRes) {
        if (fmTitleView != null) {
            fmTitleView.setImageBackIconRes(rightIconRes);
        }
    }

    protected void setRightIcon(int rightIconRes, OnClickListener rightIconClickListener) {
        if (fmTitleView != null) {
            fmTitleView.setRightIcon(rightIconRes, rightIconClickListener);
        }
    }

    protected void setRightTitle(String rightTitle, OnClickListener rightIconClickListener) {
        if (fmTitleView != null) {
            fmTitleView.setRightTitle(rightTitle, rightIconClickListener);
        }
    }


    @Override
    protected void onErrorRefresh() {
        super.onErrorRefresh();
        loadData();
    }

    @Override
    public FragmentActivity getActivity() {
        if (getParent() != null) {
            return (FragmentActivity) getParent();
        }
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (onRequestPermissionListener != null) {
            onRequestPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        PermissionUtils.dispatchPermissionResult(this, requestCode, permissions, grantResults, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private OnRequestPermissionListener onRequestPermissionListener;

    public void setOnRequestPermissionListener(OnRequestPermissionListener onRequestPermissionListener) {
        this.onRequestPermissionListener = onRequestPermissionListener;
    }

    public interface OnRequestPermissionListener {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }

    protected boolean umengPage() {
        return true;
    }

    @Override
    public void dofinish() {
        finish();
    }


    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public void showLoading(final String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Intent _getIntent() {
        return getIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
        /**
         * 如果当前类注册了event bus， 就注销掉
         */
        if (EventBus.getDefault().isRegistered(this)) {
            LogUtils.i("=====msg_event", "unregister : " + this.getClass().getSimpleName());
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean isForeground() {
        return isForeground;
    }

    public void nextActivity(Class<? extends IBaseActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * 获取当前activity的presenter
     */
    protected abstract P getPresenter();

    /**
     * 给当前activity添加控制事件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    @Override
    public void hideEmptyPage() {
        showContent();
    }

    @Override
    public void showEmptyPage(int errorCode) {
        if (errorCode == -1) {
            showErrorPage(errorCode, "");
        }
    }


    /**
     * 传递onActivityResult
     */
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 注册 event bus， 用于通信
     */
    public void registerEventBus() {
        LogUtils.i("=====msg_event", "register : " + this.getClass().getSimpleName());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    //	/**
//	 * 响应 eventbus 消息， 在主线程处理
//	 */
//	@Subscribe(threadMode = ThreadMode.MAIN)
//	public void responseEventBusMainThread() {
//
//	}
}
