package cn.vastsky.onlineshop.installment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.common.utils.CommonUtils;
import cn.vastsky.onlineshop.installment.model.bean.response.LoanDetailInfoResponse;

/**
 * @author: kezy
 * @create_time 2019/11/12
 * @description:
 */
public class RepayPlanListAdapter extends RecyclerView.Adapter<RepayPlanListAdapter.PlanViewHolder> {


    private List<LoanDetailInfoResponse.RepaymentPlansBean> mData;
    private Context mContext;


    public RepayPlanListAdapter(Context context, List<LoanDetailInfoResponse.RepaymentPlansBean> list) {
        mData = list;
        mContext = context;
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_repay_plan_item, parent, false);
        PlanViewHolder viewHolder = new PlanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlanViewHolder viewHolder, int position) {

        LoanDetailInfoResponse.RepaymentPlansBean itemData = mData.get(position);

        viewHolder.tvRepay1.setText(CommonUtils.formatNumber("00", itemData.periodNo));
        viewHolder.tvRepay2.setText(itemData.amount);
        viewHolder.tvRepay3.setText(itemData.principal);
        viewHolder.tvRepay4.setText(itemData.LX);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class PlanViewHolder extends RecyclerView.ViewHolder {

        TextView tvRepay1;
        TextView tvRepay2;
        TextView tvRepay3;
        TextView tvRepay4;


        public PlanViewHolder(View view) {
            super(view);
            tvRepay1 = view.findViewById(R.id.tv_repay_1);
            tvRepay2 = view.findViewById(R.id.tv_repay_2);
            tvRepay3 = view.findViewById(R.id.tv_repay_3);
            tvRepay4 = view.findViewById(R.id.tv_repay_4);
        }
    }
}
