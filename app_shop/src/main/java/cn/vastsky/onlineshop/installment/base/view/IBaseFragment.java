package cn.vastsky.onlineshop.installment.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.libs.common.base.CommonnBaseFragment;
import cn.vastsky.libs.common.utils.PermissionUtils;
import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;
import cn.vastsky.onlineshop.installment.base.impl.IBaseView;


/**
 * 基础 fragment
 *
 * @param <P> 对应的presenter接口
 * @author dingzhongsheng
 */
public abstract class IBaseFragment<P extends IBasePresenter> extends CommonnBaseFragment implements IBaseView {
    protected P presenter;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("=====msg_fragment", "-------------  " + this.getClass().getSimpleName());
        presenter = getPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.d("=====msg_fragment", "------------- onCreateView --------" + this.getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.d("=====msg_fragment", "------------- onViewCreated --------" + this.getClass().getSimpleName());
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.d("=====msg_fragment", "------------- onStart --------" + this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("=====msg_fragment", "------------- onResume --------" + this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d("=====msg_fragment", "------------- onPause --------" + this.getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d("=====msg_fragment", "------------- onStop --------" + this.getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d("=====msg_fragment", "------------- onDestroyView --------" + this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("=====msg_fragment", "------------- onDestroy --------" + this.getClass().getSimpleName());
    }

    /**
     * 弹窗提示
     */
    private Toast toast;

    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

    private OnRequestPermissionListener onRequestPermissionListener;

    public void setOnRequestPermissionListener(OnRequestPermissionListener onRequestPermissionListener) {
        this.onRequestPermissionListener = onRequestPermissionListener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (onRequestPermissionListener != null) {
            onRequestPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        PermissionUtils.dispatchPermissionResult(mActivity, requestCode, permissions, grantResults, null);
    }

    public interface OnRequestPermissionListener {
        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }


    @NotNull
    @Override
    public Intent _getIntent() {
        return mActivity.getIntent();
    }

    @Override
    public void dofinish() {
        mActivity.finish();
    }

    @Override
    public void showEmptyPage(int errorCode) {
    }

    @Override
    public void hideEmptyPage() {
    }

    @Override
    public void showLoading(@NotNull String msg) {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 获取当前activity的presenter
     */
    protected abstract P getPresenter();


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
