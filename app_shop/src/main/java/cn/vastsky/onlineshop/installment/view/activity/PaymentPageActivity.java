package cn.vastsky.onlineshop.installment.view.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.paymodule.alipay.AlipayCallBack;
import cn.vastsky.lib.paymodule.alipay.AlipayUtil;
import cn.vastsky.lib.paymodule.alipay.bean.PayResult;
import cn.vastsky.lib.paymodule.wxpay.WXPayUtil;
import cn.vastsky.libs.common.config.userinfo.LoginFacade;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.libs.common.utils.ViewUtils;
import cn.vastsky.libs.common.view.dialog.CommonDialog;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.IBaseActivity;
import cn.vastsky.onlineshop.installment.common.utils.CommonUtils;
import cn.vastsky.onlineshop.installment.contract.PaymentContract;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;
import cn.vastsky.onlineshop.installment.model.bean.response.PayResultResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PaymentInfoResponse;
import cn.vastsky.onlineshop.installment.presenter.PaymentPresenter;
import cn.vastsky.onlineshop.installment.view.adapter.PayPageLoanListAdapter;
import cn.vastsky.onlineshop.installment.view.adapter.base.OnItemClickListener;

public class PaymentPageActivity extends IBaseActivity<PaymentContract.PaymentContractPresenter> implements PaymentContract.PaymentContractView {

    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.iv_pay_time_icon)
    ImageView ivPayTimeIcon;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.rl_payment_info_layout)
    RelativeLayout rlPaymentInfoLayout;
    @BindView(R.id.iv_pay_title)
    ImageView ivPayTitle;
    @BindView(R.id.tv_pay_fenqi_title)
    TextView tvPayFenqiTitle;
    @BindView(R.id.view_line_top)
    View viewLineTop;
    @BindView(R.id.rlv_loan_list)
    RecyclerView rlvLoanList;
    @BindView(R.id.rl_loan_list_layout)
    RelativeLayout rlLoanListLayout;
    @BindView(R.id.iv_pay_wechat)
    ImageView ivPayWechat;
    @BindView(R.id.rl_payment_wechat_layout)
    RelativeLayout rlPaymentWechatLayout;
    @BindView(R.id.iv_pay_alipay)
    ImageView ivPayAlipay;
    @BindView(R.id.rl_payment_alipay_layout)
    RelativeLayout rlPaymentAlipayLayout;
    @BindView(R.id.tv_payment_btn)
    TextView tvPaymentBtn;
    @BindView(R.id.rl_payment_warn_layout)
    RelativeLayout rlPaymentWarnLayout;
    @BindView(R.id.view_line_bottom)
    View viewLineBottom;
    @BindView(R.id.iv_pay_wechat_status)
    ImageView ivPayWechatStatus;
    @BindView(R.id.iv_pay_alipay_status)
    ImageView ivPayAlipayStatus;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected PaymentContract.PaymentContractPresenter getPresenter() {
        return new PaymentPresenter(this);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();

        orderSn = getIntent().getStringExtra("order_sn");

//        orderSn = "201911181133230101000001";
    }

    @Override
    protected void initView() {

        registerEventBus();

        initRecyclerView();

        mTitleView.getTextView().setText("收银台");
        initClickListener();
    }

    private void initClickListener() {

        tvPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (payType == 1 || payType == 2) {
                    needCheckResult = true;
                } else {
                    needCheckResult = false;
                }
                presenter.getPayOrderInfo(orderId, payType);
            }
        });

        rlPaymentWechatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWechatPay();
            }
        });

        rlPaymentAlipayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAlipay();
            }
        });
    }

    PayPageLoanListAdapter loanListAdapter;

    List<PaymentInfoResponse.PayChannelBean.LoanItemBean> loanList = new ArrayList<>();

    private int payType = 0; // 0 未支付 1 支付宝 2 微信 3 分期
    private long orderId;
    private String payAmount; // 需要支付的金额
    private int instProductId = 0; // 信贷产品id
    private String orderSn = "";
    private boolean needCheckResult = false; // 是否需要检查支付结果

    private String orderDetailUrl; // 订单详情 url

    private boolean isFirstCome = true; // 是否是第一次进入页面

    private void initRecyclerView() {
        rlvLoanList.setLayoutManager(new LinearLayoutManager(getContext()));
        loanListAdapter = new PayPageLoanListAdapter(getActivity(), loanList);
        rlvLoanList.setAdapter(loanListAdapter);
        loanListAdapter.setOnitemClickLintener(new OnItemClickListener() {
            @Override
            public void onItemClicked(@NotNull View view, int index) {
                selectLoanPay(index);
            }
        });

        loanListAdapter.setOnLoanClickListner(new PayPageLoanListAdapter.loanItemClickListener() {
            @Override
            public void onLoanItemClick(View view, int index) {
                Intent intent = new Intent(PaymentPageActivity.this, LoanDetailActivity.class);
                intent.putExtra("pay_amount", payAmount);
                intent.putExtra("pay_instProductId", loanList.get(index).productId);
                intent.putExtra("pay_orderSn", orderSn);
                intent.putExtra("jump_type", 1);
                startActivity(intent);
            }
        });
    }

    private void selectLoanPay(int index) {
        ivPayAlipayStatus.setBackgroundResource(R.drawable.icon_pay_uncheck);
        ivPayWechatStatus.setBackgroundResource(R.drawable.icon_pay_uncheck);
        loanListAdapter.setSelectedIndex(index);
        tvPaymentBtn.setText("立即申请分期");

        instProductId = loanList.get(index).productId;

        payType = 3;
    }

    private void selectWechatPay() {
        ivPayWechatStatus.setBackgroundResource(R.drawable.icon_pay_checked);
        ivPayAlipayStatus.setBackgroundResource(R.drawable.icon_pay_uncheck);
        loanListAdapter.setSelectedIndex(-1);

        tvPaymentBtn.setText("立即付款");
        instProductId = 0;
        payType = 2;
    }

    private void selectAlipay() {
        ivPayAlipayStatus.setBackgroundResource(R.drawable.icon_pay_checked);
        ivPayWechatStatus.setBackgroundResource(R.drawable.icon_pay_uncheck);
        loanListAdapter.setSelectedIndex(-1);
        tvPaymentBtn.setText("立即付款");
        instProductId = 0;
        payType = 1;
    }

    @Override
    protected void loadData() {
        presenter.getPaymentPageData(orderSn);
    }


    @Override
    protected void onBack() {
        showDialogView();
        return;
    }

    @Override
    protected boolean onTitleBack() {
        return true;
    }

    private CommonDialog dialog;

    private void showDialogView() {
        if (dialog == null) {
            dialog = new CommonDialog(this);
            dialog.setMessage("选了这么久，确定要放弃吗？")
                    .setPositive("再想想")
                    .setNegtive("放弃")
                    .setOnClickBtnListener(new CommonDialog.OnClickBtnListener() {
                        @Override
                        public void onPositiveClick() {
                            dialog.dismiss();
                        }

                        @Override
                        public void onNegtiveClick() {
                            PageRouter.resolve(mActivity, orderDetailUrl);
                            dofinish();
                        }
                    });
        }
        dialog.show();
    }

    @Override
    public void showPaymentPageView(PaymentInfoResponse paymentInfo) {

        orderId = paymentInfo.orderId;
        payAmount = paymentInfo.payAmount;
        orderDetailUrl = paymentInfo.orderDetailUrl;

        String textStr = "￥" + paymentInfo.payAmount;
        SpannableStringBuilder spannable = new SpannableStringBuilder(textStr);
        //改变第0个字体大小
        spannable.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(this, 14)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //改变第1-第3个字体大小
        spannable.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(this, 19)), 1, textStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPayMoney.setText(spannable);

        initTimerView(paymentInfo.timeout);

        PaymentInfoResponse.PayChannelBean payChannelBean = paymentInfo.payChannel;
        if (payChannelBean != null) {
            if (payChannelBean.alipay == 1) {
                rlPaymentAlipayLayout.setVisibility(View.VISIBLE);
            } else {
                rlPaymentAlipayLayout.setVisibility(View.GONE);
            }

            if (payChannelBean.weixin == 1) {
                if (CommonUtils.isWeixinAvilible(mActivity)) {
                    rlPaymentWechatLayout.setVisibility(View.VISIBLE);
                } else {
                    LogUtils.e("--------------- err : " + "微信未安装");
                    rlPaymentWechatLayout.setVisibility(View.GONE);
                }
            } else {
                rlPaymentWechatLayout.setVisibility(View.GONE);
            }

            if (payChannelBean.alipay == 1 && payChannelBean.weixin == 1) {
                viewLineBottom.setVisibility(View.VISIBLE);
            } else {
                viewLineBottom.setVisibility(View.GONE);
            }

            if (payChannelBean.hasProduct == 1) {
                rlPaymentWarnLayout.setVisibility(View.GONE);
            } else {
                rlPaymentWarnLayout.setVisibility(View.VISIBLE);
            }

            List<PaymentInfoResponse.PayChannelBean.LoanItemBean> loanItemBeanList = payChannelBean.productList;

            if (loanItemBeanList != null && loanItemBeanList.size() > 0) {
                rlLoanListLayout.setVisibility(View.VISIBLE);

                if (loanList != null || loanList.size() > 0) {
                    loanList.clear();
                }
                loanList.addAll(loanItemBeanList);
                loanListAdapter.notifyDataSetChanged();

                if (payChannelBean.hasProduct == 1) {
                    if (isFirstCome) {
                        selectLoanPay(0);
                    } else {
                        if (instProductId != 0) {
                            for (int i = 0; i < loanList.size(); i++) {
                                if (instProductId == loanList.get(i).productId) {
                                    selectLoanPay(i);
                                }
                            }
                        } else {
                            if (payType != 1 && payType != 2) {
                                selectLoanPay(0);
                            }
                        }
                    }
                } else {
                    if (isFirstCome) {
                        if (rlPaymentWechatLayout.getVisibility() == View.VISIBLE) {
                            selectWechatPay();
                        } else if (rlPaymentAlipayLayout.getVisibility() == View.VISIBLE) {
                            selectAlipay();
                        }
                    }
                }
            } else {
                rlLoanListLayout.setVisibility(View.GONE);
                if (isFirstCome) {
                    if (rlPaymentWechatLayout.getVisibility() == View.VISIBLE) {
                        selectWechatPay();
                    } else if (rlPaymentAlipayLayout.getVisibility() == View.VISIBLE) {
                        selectAlipay();
                    }
                }
            }
        }
        isFirstCome = false;
    }

    private long timeOut;
    private CountDownTimer downTimer;

    private void initTimerView(long time) {

        try {
            if (downTimer == null) {
                timeOut = time;
                downTimer = new CountDownTimer(timeOut * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (timeOut > 0) {
                            if (tvPayTime != null) {
                                tvPayTime.setText(CommonUtils.secondToTime(timeOut));
                                timeOut--;
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        if (tvPayTime != null) {
                            tvPayTime.setText("00:00:00");
                            downTimer.cancel();
                        }
                    }
                };
                downTimer.start();
            }
        } catch (Exception e) {
            LogUtils.e("======== err: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void callAliPayView(String orderInfo) {
        AlipayUtil.getInstance().pay(this, orderInfo, true, new AlipayCallBack() {
            @Override
            public void callBack(PayResult resultSet) {
                LogUtils.d("=====msg pay result :" + resultSet.toString());
            }
        });
    }


    @Override
    public void callWechatPayView(String orderInfo) {

        try {
            JSONObject object = new JSONObject(orderInfo);
            if (object != null && !object.has("retcode")) {
                PayReq payReq = new PayReq();
                payReq.appId = object.optString("appid"); //
                payReq.partnerId = object.optString("partnerid");
                payReq.prepayId = object.optString("prepay_id"); //
                payReq.nonceStr = object.optString("nonce_str"); //
                payReq.packageValue = object.optString("package");
                payReq.timeStamp = object.optString("timestamp"); //
                payReq.sign = object.optString("sign"); //

                WXPayUtil.getInstance().pay(PaymentPageActivity.this, object.optString("appid"), payReq);
//                WXPayUtil.getInstance().openWX(PaymentPageActivity.this, object.optString("appid"));
            } else {
                LogUtils.d("PAY_GET" + "返回错误 :" + object.getString("retmsg"));
            }

        } catch (JSONException e) {
            LogUtils.e("===== error: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void callLoanPayView() {
        Intent intent = new Intent(this, LoanDetailActivity.class);
        intent.putExtra("pay_amount", payAmount);
        intent.putExtra("pay_instProductId", instProductId);
        intent.putExtra("pay_orderSn", orderSn);
        startActivity(intent);
    }

    @Override
    public void goPayResultView(PayResultResponse payResult) {
        needCheckResult = false;
        Intent intent = new Intent(this, PayResultPageActivity.class);
        intent.putExtra("pay_result", payResult);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (needCheckResult && LoginFacade.isLogin()) {
            presenter.checkPayResultStatus(orderSn);
        }
        if (LoginFacade.isLogin() && !isFirstCome) {
            loadData();
        }
    }

    /**
     * 响应 eventbus 消息， 在主线程处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void responseEventBusMainThread(MessageEvent event) {
        if (event != null && MessageEvent.TYPE_FINISH_PAYMENT.equals(event.eventType)) {
            dofinish();
        }
    }
}
