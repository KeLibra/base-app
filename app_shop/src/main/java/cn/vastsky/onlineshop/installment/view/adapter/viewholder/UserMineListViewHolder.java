package cn.vastsky.onlineshop.installment.view.adapter.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;
import cn.vastsky.onlineshop.installment.view.adapter.base.BaseViewHolder;

/**
 * Created by airal on 2018/9/25.
 */

public class UserMineListViewHolder extends BaseViewHolder<UserMineResponse.UrlInfoBean> {

    TextView tvUserMineName;
    RelativeLayout rlUserItemLayout;
    private Context context;

    public UserMineListViewHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.vh_user_center_list_view;
    }

    @Override
    public void onCreateView() {
        tvUserMineName = findViewById(R.id.tv_user_mine_name);
        rlUserItemLayout = findViewById(R.id.rl_user_item_layout);
    }

    @Override
    public void setData(UserMineResponse.UrlInfoBean infoBean, int flatPosition) {

        if (infoBean != null) {
            tvUserMineName.setText(infoBean.text);

            rlUserItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PageRouter.resolve(context, infoBean.url);
                }
            });
        }
    }

    @Override
    public BaseViewHolder<UserMineResponse.UrlInfoBean> getInstance() {
        return new UserMineListViewHolder(context);
    }
}
