package cn.kezy.libs.common.view.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import androidx.annotation.NonNull;
import cn.kezy.libs.common.R;
import cn.kezy.libs.common.view.impl.ILoadingDialog;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class DefaultLoadingDialog extends Dialog implements
        Dialog.OnDismissListener,
        Dialog.OnShowListener,
        ILoadingDialog {

    private LottieAnimationView mLoadingView;
    private TextView mMessageView;
    private boolean mCancellableOnTouchOutside;

    public DefaultLoadingDialog(@NonNull Context context) {
        super(context);
        initLoadingView(context);
    }



    public DefaultLoadingDialog(Context context, int theme) {
        super(context, theme);
        initLoadingView(context);
    }
    private void initLoadingView(Context context) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.lib_default_loading_dialog, null);
        setContentView(view);
        mLoadingView = (LottieAnimationView) view.findViewById(R.id.LibDialog_animation_loading);
        mMessageView = (TextView) view.findViewById(R.id.LibDialog_tv_loading);
        setOnDismissListener(this);
        setOnShowListener(this);
    }

    public void setMessageId(int resId) {
        if (resId > 0) {
            String res = getContext().getString(resId);
            setMessage(res);
        }
    }

    public void setMessage(String res) {
        if (!TextUtils.isEmpty(res)) {
            mMessageView.setText(res);
            mMessageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        mCancellableOnTouchOutside = cancel;
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        mLoadingView.setVisibility(View.GONE);
        mLoadingView.cancelAnimation();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.loop(true);
        mLoadingView.playAnimation();
    }

    @Override
    public boolean cancellableOnTouchOutside() {
        return mCancellableOnTouchOutside;
    }
}
