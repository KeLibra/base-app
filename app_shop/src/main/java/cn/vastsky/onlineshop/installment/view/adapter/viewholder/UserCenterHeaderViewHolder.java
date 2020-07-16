package cn.vastsky.onlineshop.installment.view.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.vastsky.lib.base.config.AppConfigLib;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.libs.common.utils.AppInfoUtils;
import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.onlineshop.installment.BuildConfig;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;
import cn.vastsky.onlineshop.installment.view.adapter.base.BaseViewHolder;


/**
 *
 */
public class UserCenterHeaderViewHolder extends BaseViewHolder<UserMineResponse> {


    TextView tvUserPhone;
    LinearLayout llUserLoanLayout;
    TextView tvUserRecentRepay;
    TextView tvUserAllRepay;
    LinearLayout llUserLoanBill;
    ImageView iv_user_head_img;


    Context context;

    public UserCenterHeaderViewHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.vh_user_center_header_view;
    }

    @Override
    public void onCreateView() {
        tvUserPhone = findViewById(R.id.tv_user_phone);
        llUserLoanLayout = findViewById(R.id.ll_user_loan_layout);
        tvUserRecentRepay = findViewById(R.id.tv_user_recent_repay);
        tvUserAllRepay = findViewById(R.id.tv_user_all_repay);
        llUserLoanBill = findViewById(R.id.ll_user_loan_bill);
        iv_user_head_img = findViewById(R.id.iv_user_head_img);

        ImageLoadUtils.loadCircleCropImage(context, R.drawable.icon_user_center_head_img, iv_user_head_img);

        if (BuildConfig.DEBUG) {
            iv_user_head_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showLong("当前版本号： " + AppInfoUtils.getVersionName());
                }
            });
        }
    }

    @Override
    public void setData(UserMineResponse response, int flatPosition) {

        if (AppConfigLib.isCkFlag()) {
            llUserLoanLayout.setVisibility(View.GONE);
        } else {
            llUserLoanLayout.setVisibility(View.VISIBLE);
        }

        tvUserPhone.setText(response.phone);
        tvUserRecentRepay.setText(response.recentNeedHKAmount);
        tvUserAllRepay.setText(response.totalNeedHKAmount);

        llUserLoanBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(mContext, response.zdDetailUrl);
            }
        });
    }

    @Override
    public BaseViewHolder<UserMineResponse> getInstance() {
        return new UserCenterHeaderViewHolder(getContext());
    }
}
