package cn.kezy.libs.common.view.base;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import cn.kezy.libs.common.R;
import cn.kezy.libs.common.view.impl.ITitleView;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class DefaultTitleView implements ITitleView {
    private View mRoot;
    private Context mContext;
    private int rightIconRes;
    private View.OnClickListener rightIconClickListener;
    private View line;

    @Override
    public void attachTo(ViewGroup parent) {
        mRoot = View.inflate(mContext, R.layout.fm_view_title, parent);
        line = mRoot.findViewById(R.id.fm_view_line);
        imgRightIcon = (ImageView) mRoot.findViewById(R.id.fm_img_right);
        tvRight = mRoot.findViewById(R.id.tv_right_title);
        if (rightIconRes > 0) {
            imgRightIcon.setImageResource(rightIconRes);
        }
        if (rightIconClickListener == null) {
            imgRightIcon.setVisibility(View.GONE);
        } else {
            imgRightIcon.setOnClickListener(rightIconClickListener);
            imgRightIcon.setVisibility(View.VISIBLE);
        }
    }

    public void setImageBackShow(boolean isShow) {
        if (mRoot != null) {
            ImageView imgBack = (ImageView) mRoot.findViewById(R.id.fm_img_back);
            imgBack.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    public void setImageBackIconRes(int id) {
        ImageView imgBack = (ImageView) mRoot.findViewById(R.id.fm_img_back);
        imgBack.setImageResource(id);
    }

    public DefaultTitleView(Context context) {
        mContext = context;
    }

    public void setTitle(int titleRes) {
        TextView textView = (TextView) mRoot.findViewById(R.id.lib_tv_header_title);
        textView.setText(titleRes);
    }

    /**
     * 设置右上角icon是否显示
     *
     * @param show
     */
    public void showImageRightIcon(boolean show) {
        imgRightIcon.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置刷新按钮
     *
     * @param onClickListener
     */
    public void showRefreshIcon(int resIcon, View.OnClickListener onClickListener) {

    }

    /**
     * 设置右上角文字
     *
     * @param msg
     */
    public void setRightMsg(String msg, int colorFlag) {

    }

    public void setTitle(String titleRes) {
        if (mRoot != null) {
            TextView textView = (TextView) mRoot.findViewById(R.id.lib_tv_header_title);
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            textView.setText(titleRes);
        }
    }

    public void setTitleColor(int titleColor) {
        TextView textView = (TextView) mRoot.findViewById(R.id.lib_tv_header_title);
        textView.setTextColor(titleColor);
    }

    ImageView imgRightIcon;
    TextView tvRight;

    public void hideLine() {
        line.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public View getView() {
        return mRoot.findViewById(R.id.lib_rl_header_with_bar);
    }

    @Override
    public TextView getTextView() {
        return (TextView) mRoot.findViewById(R.id.lib_tv_header_title);
    }

    @Override
    public View getBackView() {
        return mRoot.findViewById(R.id.fm_img_back);
    }

    @Override
    public View getRefreshView() {
        return null;
    }

    @Override
    public View getShareView() {
        return mRoot.findViewById(R.id.fm_img_right);
    }

    @Override
    public View getCloseView() {
        return null;
    }

    @Override
    public boolean getFixedTitle() {
        return false;
    }

    public void setRightIcon(int rightIconRes, View.OnClickListener rightIconClickListener) {

        this.rightIconRes = rightIconRes;
        this.rightIconClickListener = rightIconClickListener;
        if (imgRightIcon == null) {return;}
        if (rightIconRes > 0) {
            imgRightIcon.setImageResource(rightIconRes);
        }
        if (rightIconClickListener == null) {
            imgRightIcon.setVisibility(View.GONE);
        } else {
            imgRightIcon.setOnClickListener(rightIconClickListener);
            imgRightIcon.setVisibility(View.VISIBLE);
        }
    }

    public void setRightTitle(String title, View.OnClickListener rightTitleClickListener) {
        if (TextUtils.isEmpty(title)) {
            tvRight.setVisibility(View.GONE);
        } else {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(String.valueOf(title));
            if (rightTitleClickListener != null) {
                tvRight.setOnClickListener(rightTitleClickListener);
            }
        }
    }
}
