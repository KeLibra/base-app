package cn.kezy.libs.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.kezy.libs.common.R;
import cn.kezy.libs.common.utils.ViewUtils;


/**
 * @author: kezy
 * @create_time 2019/11/25
 * @description:
 */
public class CommonDialog extends Dialog {

    /**
     * 显示的图片
     */
    private ImageView imageIv;

    /**
     * 显示的标题
     */
    private TextView titleTv;

    /**
     * 显示的消息
     */
    private TextView messageTv;

    /**
     * 确认和取消按钮
     */
    private TextView negtiveBn, positiveBn;

    public CommonDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    /**
     * 都是内容数据
     */
    private String message;
    private String title;
    private String positive, negtive;
    private int imageResId = -1;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBtnListener != null) {
                    onClickBtnListener.onPositiveClick();
                }

                if (singleBtnListener != null) {
                    singleBtnListener.onClick();
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBtnListener != null) {
                    onClickBtnListener.onNegtiveClick();
                }
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        } else {
            positiveBn.setText("确定");
        }
        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        } else {
            negtiveBn.setText("取消");
        }

        if (imageResId != -1) {
            imageIv.setImageResource(imageResId);
            imageIv.setVisibility(View.VISIBLE);
        } else {
            imageIv.setVisibility(View.GONE);
        }

        if (isSingle) {
            negtiveBn.setVisibility(View.GONE);
            positiveBn.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(ViewUtils.dip2px(this.getContext(), 30), 0, ViewUtils.dip2px(this.getContext(), 30), 0);
            positiveBn.setLayoutParams(layoutParams);
        } else {
            negtiveBn.setVisibility(View.VISIBLE);
        }

        final WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(params);

    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = findViewById(R.id.negtive);
        positiveBn = findViewById(R.id.positive);
        titleTv = findViewById(R.id.title);
        messageTv = findViewById(R.id.message);
        imageIv = findViewById(R.id.image);

    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBtnListener onClickBtnListener;

    public CommonDialog setOnClickBtnListener(OnClickBtnListener onClickBtnListener) {
        this.onClickBtnListener = onClickBtnListener;
        return this;
    }

    public interface OnClickBtnListener {
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();

        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }

    /**
     * 单键按钮的点击事件
     */
    public onSingleBtnListener singleBtnListener;

    public interface onSingleBtnListener {
        /**
         * 点击单键事件
         */
        public void onClick();
    }

    public String getMessage() {
        return message;
    }

    public CommonDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPositive() {
        return positive;
    }

    public CommonDialog setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public String getNegtive() {
        return negtive;
    }

    public CommonDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public CommonDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }

    public CommonDialog setSingleBtnText(String singleText) {
        isSingle = true;
        positive = singleText;
        return this;
    }

    public CommonDialog setSingleBtnTextWithListener(String singleText, onSingleBtnListener listener) {
        isSingle = true;
        positive = singleText;
        this.singleBtnListener = listener;
        return this;
    }

    public CommonDialog setImageResId(int imageResId) {
        this.imageResId = imageResId;
        return this;
    }
}
