package cn.kezy.libs.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cn.kezy.libs.common.R;
import cn.kezy.libs.common.view.base.DefaultLoadingDialog;
import cn.kezy.libs.common.view.impl.ILoadingDialog;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public abstract class CommonnBaseFragment extends Fragment {

    protected View mRootLayout = null;
    protected ILoadingDialog mLoadingDialog;
    protected boolean mIsVisible = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootLayout = inflater.inflate(getContentLayout(), null);
        getIntentData();
        initHeaderView();
        initContentView();
        initFooterView();
        initData();
        return mRootLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsVisible = false;
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    protected void getIntentData() {
    }

    protected abstract int getContentLayout();

    protected void initContentView() {
    }

    protected void initHeaderView() {

    }

    protected void initFooterView() {
    }

    protected void initData() {
    }


    /**
     * 自定义加载弹窗
     */
    protected ILoadingDialog getLoadingDialog() {
        return null;
    }

    protected void initProgressDialog() {
        if (getLoadingDialog() != null) {
            mLoadingDialog = getLoadingDialog();
        } else {
            DefaultLoadingDialog dialog = new DefaultLoadingDialog(getActivity(), R.style.Theme_AppCompat_Dialog);
            // 默认不可关闭dialog
            dialog.setCanceledOnTouchOutside(false);
            mLoadingDialog = dialog;
        }
    }

    // 默认不可关闭dialog
    public synchronized void showProgressDialog() {
        mRootLayout.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog == null) {
                    initProgressDialog();
                }
                if (mLoadingDialog == null) {
                    return;
                }
                if (!mLoadingDialog.isShowing() && getContext() != null) {
                    try {
                        mLoadingDialog.show();
                    } catch (WindowManager.BadTokenException ignored) {
                    }
                }
            }
        });
    }

    public void dismissProgressDialog() {
        mRootLayout.post(new Runnable() {
            @Override
            public void run() {
                if (getContext() != null && mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }
}
