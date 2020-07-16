package cn.vastsky.onlineshop.installment.view.adapter.viewholder;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.view.adapter.base.BaseViewHolder;

/**
 * Created by airal on 2018/9/25.
 */

public class HomepageHeaderViewHolder extends BaseViewHolder<HomePageInfoResponse.PositionBean.AdIconBean> {

    private Context context;
    private ImageView ivIcon;
    private TextView tvName;

    public HomepageHeaderViewHolder(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getRootViewId() {
        return R.layout.vh_homepager_head_item_view;
    }

    @Override
    public void onCreateView() {
        ivIcon = findViewById(R.id.iv_icon);
        tvName = findViewById(R.id.tv_name);
    }

    @Override
    public void setData(HomePageInfoResponse.PositionBean.AdIconBean homepageHeadItemBean, int flatPosition) {
        ImageLoadUtils.loadImage(context, homepageHeadItemBean.adImage, ivIcon);
        tvName.setText(homepageHeadItemBean.adText);
    }

    @Override
    public BaseViewHolder<HomePageInfoResponse.PositionBean.AdIconBean> getInstance() {
        return new HomepageHeaderViewHolder(context);
    }
}
