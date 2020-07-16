package cn.vastsky.onlineshop.installment.common.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.common.weight.MaxHeightRecyclerView;
import cn.vastsky.onlineshop.installment.model.bean.response.LoanDetailInfoResponse;
import cn.vastsky.onlineshop.installment.view.adapter.RepayPlanListAdapter;

/**
 * @author: kezy
 * @create_time 2019/11/21
 * @description:
 */
public class LoanRepayBottomDialog extends BaseFullScreenDialog {

    private Context context;

    TextView tvDialogTitle;
    ImageView ivDialogClose;
    TextView tvDialogTips;
    RelativeLayout rlDialogTips;
    MaxHeightRecyclerView rvDialogList;
    TextView tvDialogTotal;
    RelativeLayout rlDialogBg;

    public LoanRepayBottomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public LoanRepayBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected int getLayoutResId() {
        return R.layout.dialog_loan_repay_bottom;
    }

    private List<LoanDetailInfoResponse.RepaymentPlansBean> planList;

    private RepayPlanListAdapter listAdapter;

    protected void onViewCreated() {
        tvDialogTitle = findViewById(R.id.tv_dialog_title);
        ivDialogClose = findViewById(R.id.iv_dialog_close);
        tvDialogTips = findViewById(R.id.tv_dialog_tips);
        rlDialogTips = findViewById(R.id.rl_dialog_tips);
        rvDialogList = findViewById(R.id.rv_dialog_list);
        tvDialogTotal = findViewById(R.id.tv_dialog_total);
        rlDialogBg = findViewById(R.id.bm_dialog_bg);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvDialogList.setLayoutManager(layoutManager);
        planList = new ArrayList<>();
        listAdapter = new RepayPlanListAdapter(getContext(), planList);
        rvDialogList.setAdapter(listAdapter);

        ivDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setDialogData(String totalAmount, List<LoanDetailInfoResponse.RepaymentPlansBean> plansBeans) {

        tvDialogTotal.setText("总计需还  " + totalAmount);

        if (plansBeans != null) {
            LogUtils.d("------------ plans beans: " + plansBeans.size());
            planList.clear();
            planList.addAll(plansBeans);
            listAdapter.notifyDataSetChanged();
        }
    }
}


