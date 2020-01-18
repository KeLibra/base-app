package cn.kezy.libs.common.view.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.kezy.libs.common.R;
import cn.kezy.libs.common.view.impl.IErrorPageView;

/**
 * @author: kezy
 * @create_time 2019/10/28
 * @description:
 */
public class DefaultErrorPageView extends LinearLayout implements
        View.OnClickListener, IErrorPageView {

    private Context mContext;
    private ImageView mLogoIv;
    private TextView mButtonTv;
    private IErrorPageView.OnRefreshListener mOnRefreshBtnClickListener;
    private boolean mRefresh = false;
    private TextView mMainTipsTv;

    public DefaultErrorPageView(Context context) {
        this(context, null);
    }

    public DefaultErrorPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        View view = View.inflate(mContext, R.layout.fm_view_network_error, this);
        mLogoIv = (ImageView) view.findViewById(R.id.img_error);
        mButtonTv = (TextView) view.findViewById(R.id.btn_refresh);
        mMainTipsTv = (TextView) view.findViewById(R.id.tv_error_msg);

        mButtonTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_refresh) {
            retry();
        }
    }


    @Override
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshBtnClickListener = listener;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void report() {

    }

    @Override
    public void retry() {
        if (mOnRefreshBtnClickListener != null) {
            mOnRefreshBtnClickListener.onRefreshBtnClick();
        }
    }

    /**
     * 错误码 <0 显示网络异常，默认数据出错
     *
     * @param restErrorCode 返回的错误码
     */
    @Override
    public void networkErrorOrNot(int restErrorCode) {

    }

}
