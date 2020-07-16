package cn.vastsky.onlineshop.installment.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.model.bean.response.PaymentInfoResponse;
import cn.vastsky.onlineshop.installment.view.adapter.base.OnItemClickListener;

/**
 * @author: kezy
 * @create_time 2019/11/12
 * @description:
 */
public class PayPageLoanListAdapter extends RecyclerView.Adapter<PayPageLoanListAdapter.MyViewHolder> {

    private List<PaymentInfoResponse.PayChannelBean.LoanItemBean> mData;
    private Context mContext;

    private OnItemClickListener onItemClick;   //定义点击事件接口
    private loanItemClickListener loanClickListner;   //定义点击事件接口

    private int selectedPosition = -1; // 选中item

    //定义设置点击事件监听的方法
    public void setOnitemClickLintener(OnItemClickListener onitemClick) {
        this.onItemClick = onitemClick;
    }

    //定义设置点击事件监听的方法
    public void setOnLoanClickListner(loanItemClickListener loanClickListner) {
        this.loanClickListner = loanClickListner;
    }

    public interface loanItemClickListener {
        void onLoanItemClick(View view, int index);
    }

    public PayPageLoanListAdapter(Context context, List<PaymentInfoResponse.PayChannelBean.LoanItemBean> list) {
        mData = list;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pay_page_loan_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {

        PaymentInfoResponse.PayChannelBean.LoanItemBean itemData = mData.get(position);
        if (position == 0) {
            viewHolder.viewTopLine.setVisibility(View.GONE);
        } else {
            viewHolder.viewTopLine.setVisibility(View.VISIBLE);
        }

        ImageLoadUtils.loadCircleCropImage(mContext, itemData.logoPicUrl, viewHolder.ivLoanItemIcon);
        viewHolder.tvLoanItemName.setText(itemData.name);
        viewHolder.tvLoanItemDesc.setText(itemData.productDesc);
        viewHolder.tvLoanItemInfo.setText(itemData.condition);
        showStatusView(viewHolder, itemData.productStatus, position);

        viewHolder.rlLoanItemLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClick != null) {
                            if (itemData.productStatus == 1) {
                                onItemClick.onItemClicked(v, position);
                            }
                        }
                    }
                }
        );

        viewHolder.rl_loan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loanClickListner != null) {
                    loanClickListner.onLoanItemClick(v, position);
                }
            }
        });
    }

    /**
     * @param viewHolder
     * @param productStatus |1 可申请|2 申请中|3 已被拒|
     * @param itemPosition
     */
    private void showStatusView(MyViewHolder viewHolder, int productStatus, int itemPosition) {
        switch (productStatus) {
            case 1: // 可申请
                viewHolder.tvLoanItemInfo.setTextColor(Color.parseColor("#2BABF8")); // 凭身份证申请文案
                viewHolder.ivLoanItemStatus.setVisibility(View.VISIBLE);
                if (itemPosition == selectedPosition) {
                    viewHolder.ivLoanItemStatus.setBackgroundResource(R.drawable.icon_pay_checked); // 右上角icon
                } else {
                    viewHolder.ivLoanItemStatus.setBackgroundResource(R.drawable.icon_pay_uncheck);
                }
                viewHolder.tvLoanItemStatus.setVisibility(View.GONE); // 右上角 文字

                viewHolder.tvLoanItemTag.setVisibility(View.VISIBLE); // 标签
                break;
            case 2: // 申请中
                viewHolder.tvLoanItemInfo.setTextColor(Color.parseColor("#999999")); // 凭身份证申请文案

                viewHolder.ivLoanItemStatus.setVisibility(View.GONE); // 右上角icon
                viewHolder.tvLoanItemStatus.setVisibility(View.VISIBLE);// 右上角icon
                viewHolder.tvLoanItemStatus.setText("审批中"); // 右上角 文字
                viewHolder.tvLoanItemStatus.setTextColor(Color.parseColor("#ff6f24"));// 右上角 文字

                viewHolder.tvLoanItemTag.setVisibility(View.GONE); // 标签

                break;
            case 3: // 已被拒
                viewHolder.tvLoanItemInfo.setTextColor(Color.parseColor("#999999")); // 凭身份证申请文案

                viewHolder.ivLoanItemStatus.setVisibility(View.GONE); // 右上角icon
                viewHolder.tvLoanItemStatus.setVisibility(View.VISIBLE); // 右上角 文字
                viewHolder.tvLoanItemStatus.setText("已被拒"); // 右上角 文字
                viewHolder.tvLoanItemStatus.setTextColor(Color.parseColor("#333333")); // 右上角 文字

                viewHolder.tvLoanItemTag.setVisibility(View.GONE); // 标签
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setSelectedIndex(int position) {
        this.selectedPosition = position;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlLoanItemLayout;
        RelativeLayout rl_loan_detail;

        View viewTopLine;
        ImageView ivLoanItemIcon;
        TextView tvLoanItemName;
        TextView tvLoanItemInfo;
        ImageView ivLoanItemStatus;
        TextView tvLoanItemStatus;
        TextView tvLoanItemDesc;
        TextView tvLoanItemTag;


        public MyViewHolder(View view) {
            super(view);
            rlLoanItemLayout = view.findViewById(R.id.rl_loan_item_layout);
            rl_loan_detail = view.findViewById(R.id.rl_loan_detail);
            viewTopLine = view.findViewById(R.id.view_top_line);
            ivLoanItemIcon = view.findViewById(R.id.iv_loan_item_icon);
            tvLoanItemName = view.findViewById(R.id.tv_loan_item_name);
            tvLoanItemInfo = view.findViewById(R.id.tv_loan_item_info);
            tvLoanItemDesc = view.findViewById(R.id.tv_loan_item_desc);
            ivLoanItemStatus = view.findViewById(R.id.iv_loan_item_status);
            tvLoanItemStatus = view.findViewById(R.id.tv_loan_item_status);
            tvLoanItemTag = view.findViewById(R.id.tv_loan_item_tag);
        }
    }
}
