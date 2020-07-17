package test.kezy.com.lib_commom.weight;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.vastsky.lib.utils.ImageLoadUtils;
import test.kezy.com.lib_commom.R;


/**
 * 贷款详情页申请信息 item
 * Created by airal on 2018/8/14.
 */

public class LoanCertItemView extends RelativeLayout {

    public LoanCertItemView(Context context) {
        this(context, R.layout.view_loan_cert_item);
    }

    public LoanCertItemView(Context context, int layoutId) {
        super(context);

        View view = LayoutInflater.from(context).inflate(layoutId, null);
        ivIcon = view.findViewById(R.id.iv_item_icon);
        tvTitle = view.findViewById(R.id.tv_item_title);
        tvStatus = view.findViewById(R.id.tv_item_status);


        this.addView(view);
    }

    private TextView tvTitle;
    private ImageView ivIcon;
    private TextView tvStatus;

    public void setItemStatus(int status) {
        switch (status) {
            // 0.未认证  1. 已认证  2. 认证中
            case 0:
                tvStatus.setText("待认证");
                tvStatus.setTextColor(Color.parseColor("#999999"));
                break;
            case 1:
                tvStatus.setText("已完成");
                tvStatus.setTextColor(Color.parseColor("#47C89B"));
                break;
            case 2:
                tvStatus.setText("认证中");
                tvStatus.setTextColor(Color.parseColor("#FF6F24"));
                break;
        }
    }

    public void setIcon(String url) {
        ImageLoadUtils.loadCircleCropImage(getContext(), url, ivIcon);
    }

    public void setTitle(String text) {
        if (tvTitle != null) {
            tvTitle.setText(text);
        }
    }
}
