package cn.vastsky.onlineshop.installment.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.SimpleBaseActivity;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;
import cn.vastsky.onlineshop.installment.model.bean.response.PayResultResponse;
import cn.vastsky.onlineshop.installment.service.PaymentModel;

public class PayResultPageActivity extends SimpleBaseActivity {

    ImageView ivPayResultIcon;
    TextView tvPayResultStatusBig;
    TextView tvPayResultStatusSmall;
    ImageView ivPayResultTips;
    TextView tvPayResultTips;
    TextView tvPayResultTipsDesc;
    TextView tvPayResultBtn;

    private PayResultResponse payResult;
    private String jumpUrl;

    private String orderSn;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_pay_result_page;
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        payResult = (PayResultResponse) getIntent().getExtras().get("pay_result");
    }

    @Override
    protected void initView() {

        ivPayResultIcon = findViewById(R.id.iv_pay_result_icon);
        tvPayResultStatusBig = findViewById(R.id.tv_pay_result_status_big);
        tvPayResultStatusSmall = findViewById(R.id.tv_pay_result_status_small);
        ivPayResultTips = findViewById(R.id.iv_pay_result_tips);
        tvPayResultTips = findViewById(R.id.tv_pay_result_tips);
        tvPayResultTipsDesc = findViewById(R.id.tv_pay_result_tips_desc);
        tvPayResultBtn = findViewById(R.id.tv_pay_result_btn);


        EventBus.getDefault().post(new MessageEvent(MessageEvent.TYPE_FINISH_PAYMENT));

        mTitleView.getTextView().setText("支付结果");

        tvPayResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(mActivity, jumpUrl);
            }
        });
    }


    private void loadDataView() {
        jumpUrl = payResult.orderDetailUrl;
        /**
         *  1 正在付款  2 分期审批中  3 分期付款成功  4 付款成功
         */
        switch (payResult.status) {
            case 1:
                showPayingView();
                break;
            case 2:
                showApplyingView();
                break;
            case 3:
                showApplySuccView();
                break;
            case 4:
                showPaySuccView();
                break;
            default:
                showErrorPage(-1);
                break;
        }
    }

    @Override
    protected void loadData() {

        if (payResult == null) {
            orderSn = getIntent().getStringExtra("orderSn");
            if (TextUtils.isEmpty(orderSn)) {
                ToastUtil.show("数据异常");
                return;
            }
            PaymentModel.checkPayStatus(orderSn, new ICallBack<PayResultResponse>() {
                @Override
                public void onSuccess(PayResultResponse payResultResponse) {
                    payResult = payResultResponse;

                    loadDataView();
                }

                @Override
                public void onFailure(String err_msg) {
                    ToastUtil.show(err_msg);
                }
            });
        } else {
            loadDataView();
        }
    }

    /**
     * 4 付款成功
     */
    private void showPaySuccView() {
        tvPayResultBtn.setText("查看并确认卡密");
        ivPayResultIcon.setBackgroundResource(R.drawable.icon_pay_result_succ);
        tvPayResultStatusBig.setText("付款成功");
        tvPayResultStatusSmall.setVisibility(View.GONE);
    }

    /**
     * 3 分期付款成功
     */
    private void showApplySuccView() {
        tvPayResultBtn.setText("查看并确认卡密");
        ivPayResultIcon.setBackgroundResource(R.drawable.icon_pay_result_succ);
        tvPayResultStatusBig.setText("分期付款成功");
        tvPayResultStatusSmall.setVisibility(View.GONE);
    }

    /**
     * 2 分期审批中
     */
    private void showApplyingView() {
        tvPayResultBtn.setText("查看审核结果");
        ivPayResultIcon.setBackgroundResource(R.drawable.icon_pay_result_wait);
        tvPayResultStatusBig.setText("分期审批中");
        tvPayResultStatusSmall.setVisibility(View.VISIBLE);
        tvPayResultStatusSmall.setText("30分钟内完成审批, 请留意通知");
    }

    /**
     * 1 正在付款
     */
    private void showPayingView() {
        tvPayResultBtn.setText("查看订单");
        ivPayResultIcon.setBackgroundResource(R.drawable.icon_pay_result_wait);
        tvPayResultStatusBig.setText("付款处理中");
        tvPayResultStatusSmall.setVisibility(View.VISIBLE);
        tvPayResultStatusSmall.setText("当前网络状况较差, 正在为您处理");

    }


    @Override
    protected void onBack() {
        finish();
    }
}
