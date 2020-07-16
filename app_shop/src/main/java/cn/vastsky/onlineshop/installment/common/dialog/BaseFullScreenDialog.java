package cn.vastsky.onlineshop.installment.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import cn.vastsky.libs.common.utils.ViewUtils;
import cn.vastsky.onlineshop.installment.R;


/**
 *
 */
public abstract class BaseFullScreenDialog extends Dialog {
    public BaseFullScreenDialog(@NonNull Context context) {
        super(context, R.style.Transparent);
        initView();
    }

    public BaseFullScreenDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected void initView() {
        initFullScreen();
        setContentView(getLayoutResId());
        onViewCreated();
    }

    public void initFullScreen() {
        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;

        int screenWidth = ViewUtils.getScreenWidth();
        int screenHeight = ViewUtils.getScreenHeight();
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
    }


    @Override
    public void setCanceledOnTouchOutside(final boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        View bg = findViewById(R.id.bm_dialog_bg);
        if (bg != null) {
            bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancel) {
                        cancel();
                    } else {
                        // TODO: 2018/1/8
                    }
                }
            });
        }
    }

    /**
     * dialog 布局
     *
     * @return
     */
    protected abstract int getLayoutResId();

    protected abstract void onViewCreated();

}
