package cn.vastsky.onlineshop.installment.view.activity;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.config.userinfo.LoginFacade;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.libs.common.utils.ImageLoadUtils;
import cn.vastsky.libs.common.utils.ViewUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.IBaseActivity;
import cn.vastsky.onlineshop.installment.common.dialog.LoanRepayBottomDialog;
import cn.vastsky.onlineshop.installment.common.weight.LoanCertItemView;
import cn.vastsky.onlineshop.installment.contract.LoanDetailContract;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;
import cn.vastsky.onlineshop.installment.model.bean.request.ContractRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.ContractResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.LoanDetailInfoResponse;
import cn.vastsky.onlineshop.installment.presenter.LoanDetailPresenter;
import cn.vastsky.onlineshop.installment.router.CertRouterUtils;

public class LoanDetailActivity extends IBaseActivity<LoanDetailContract.LoanDetailContractPresenter>
        implements LoanDetailContract.LoanDetailContractView {


    @BindView(R.id.iv_product_detail_title_icon)
    ImageView ivProductDetailTitleIcon;
    @BindView(R.id.tv_product_title_txt)
    TextView tvProductTitleTxt;
    @BindView(R.id.tv_product_title_apply_count)
    TextView tvProductTitleApplyCount;
    @BindView(R.id.tv_product_loan_money)
    TextView tvProductLoanMoney;
    @BindView(R.id.tv_product_loan_date)
    TextView tvProductLoanDate;
    @BindView(R.id.rl_loan_detail_title_layout)
    RelativeLayout rlLoanDetailTitleLayout;
    @BindView(R.id.tv_product_item_top)
    TextView tvProductItemTop;
    @BindView(R.id.tv_product_item_part_1_1)
    TextView tvProductItemPart11;
    @BindView(R.id.rl_product_item_part_1)
    RelativeLayout rlProductItemPart1;
    @BindView(R.id.tv_product_item_part_2)
    TextView tvProductItemPart2;
    @BindView(R.id.tv_product_item_part_3)
    TextView tvProductItemPart3;
    @BindView(R.id.tv_loan_detail_info_title)
    TextView tvLoanDetailInfoTitle;
    @BindView(R.id.ll_product_loan_cert_item)
    LinearLayout llProductLoanCertItem;
    @BindView(R.id.tv_loan_detail_fee_desc)
    TextView tvLoanDetailFeeDesc;
    @BindView(R.id.rl_loan_fee_layout)
    LinearLayout rlLoanFeeLayout;
    @BindView(R.id.tv_loan_detail_repay_part_1)
    TextView tvLoanDetailRepayPart1;
    @BindView(R.id.ll_loan_repay_part_1)
    LinearLayout llLoanRepayPart1;
    @BindView(R.id.tv_loan_detail_repay_part_2)
    TextView tvLoanDetailRepayPart2;
    @BindView(R.id.ll_loan_repay_part_2)
    LinearLayout llLoanRepayPart2;
    @BindView(R.id.tv_loan_detail_repay_part_3)
    TextView tvLoanDetailRepayPart3;
    @BindView(R.id.ll_loan_repay_part_3)
    LinearLayout llLoanRepayPart3;
    @BindView(R.id.rl_loan_detail_repay_layout)
    LinearLayout rlLoanDetailRepayLayout;
    @BindView(R.id.ll_loan_detail_layout)
    LinearLayout llLoanDetailLayout;
    @BindView(R.id.tv_product_btn)
    TextView tvProductBtn;
    @BindView(R.id.view_line_title)
    View viewLineTitle;
    @BindView(R.id.rl_loan_cert_layout)
    RelativeLayout rlLoanCertLayout;
    @BindView(R.id.iv_loan_agreement_icon)
    ImageView ivLoanAgreementIcon;
    @BindView(R.id.tv_loan_agreement_text)
    TextView tvLoanAgreementText;
    @BindView(R.id.rl_loan_agreement)
    RelativeLayout rlLoanAgreement;
    @BindView(R.id.tv_loan_err_msg)
    TextView tvLoanErrMsg;
    @BindView(R.id.rl_loan_err_status)
    RelativeLayout rlLoanErrStatus;
    @BindView(R.id.ll_loan_bottom_layout)
    LinearLayout llLoanBottomLayout;
    @BindView(R.id.ll_agreement_check_layout)
    LinearLayout llAgreementCheckLayout;
    @BindView(R.id.tv_product_item_part_1_2)
    TextView tvProductItemPart12;

    boolean isFirstIn = true;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_loan_detail;
    }

    private String payAmount; // 需要支付的金额
    private int instProductId; // 信贷产品id
    private String orderSn = "";

    private int jumpType = 1;

    @Override
    protected void getIntentData() {
        super.getIntentData();

        payAmount = getIntent().getStringExtra("pay_amount");
        instProductId = getIntent().getIntExtra("pay_instProductId", -1);
        orderSn = getIntent().getStringExtra("pay_orderSn");

        jumpType = getIntent().getIntExtra("jump_type", 1);
    }


    @Override
    protected LoanDetailContract.LoanDetailContractPresenter getPresenter() {
        return new LoanDetailPresenter(this);
    }

    @Override
    protected void initView() {

        registerEventBus();

        mTitleView.getTextView().setText("分期介绍");

        initListener();

        ivLoanAgreementIcon.setSelected(isChecked);
    }

    private String contractUrl;
    private String detailUrl; // 订单详情 url
    private boolean isChecked = true;

    private void initListener() {
        rlProductItemPart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanRepayBottomDialog dialog = new LoanRepayBottomDialog(mActivity);
                dialog.setDialogData(repayPlanTotalMoney, plansBeans);
                dialog.show();
            }
        });
        rlLoanAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PageRouter.resolve(mActivity, contractUrl);
            }
        });

        rlLoanErrStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageRouter.resolve(mActivity, detailUrl);
            }
        });

        tvProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    LoanDetailRequest request = new LoanDetailRequest(instProductId, orderSn);
                    CertRouterUtils.getUserCertifyTypeInfo(mActivity, request);
                } else {
                    ToastUtil.show("请先阅读并勾选协议");
                }
            }
        });

        llAgreementCheckLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked = !isChecked;
                ivLoanAgreementIcon.setSelected(isChecked);
                if (isChecked) {
                    tvProductBtn.setEnabled(true);
                } else {
                    tvProductBtn.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected void loadData() {

        if (TextUtils.isEmpty(payAmount) || instProductId == -1) {
            ToastUtil.show("数据异常");
//            dofinish();
            return;
        }

        LoanDetailRequest request = new LoanDetailRequest(payAmount, instProductId, orderSn);
        presenter.getLoanDetailInfo(request);
    }

    private void loadContractData() {
        if (jumpType == 1) {
            ContractRequest conRequest = new ContractRequest();
            conRequest.instProductId = instProductId;
            conRequest.orderSn = orderSn;
            conRequest.type = 2;
            presenter.getContractInfo(conRequest);
        }
    }

    @Override
    protected void onBack() {
        finish();
    }


    private String repayPlanTotalMoney;// 试算还款金额
    private List<LoanDetailInfoResponse.RepaymentPlansBean> plansBeans; // 试算还款计划

    @Override
    public void showLoanDetailView(LoanDetailInfoResponse response) {

        repayPlanTotalMoney = response.totalAmount;
        plansBeans = response.HKmentPlans;
        detailUrl = response.detailUrl;

        // 顶部 part 1数据
        ImageLoadUtils.loadCircleCropImage(this, response.logoPicUrl, ivProductDetailTitleIcon);
        tvProductTitleTxt.setText(response.name);

        tvProductTitleApplyCount.setText(response.applyCount + "人已申请");

        String textStr1 = "￥" + response.amountStr;
        SpannableStringBuilder spannable1 = new SpannableStringBuilder(textStr1);
        //改变 ￥ 大小
        spannable1.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(this, 16)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //改变 金额大小
        spannable1.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(this, 22)), 1, textStr1.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvProductLoanMoney.setText(spannable1); // 原价
//        tvProductLoanMoney.setText(response.amountStr);

        String loanTimeLimit = response.DKTimeLimit + "";
        String loanTimeLimitUnitStr = response.DKTimeLimitUnitStr + "";
        String textStr2 = loanTimeLimit + loanTimeLimitUnitStr;
        SpannableStringBuilder spannable2 = new SpannableStringBuilder(textStr2);
        //改变第0个字体大小
        spannable2.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(this, 22)), 0, loanTimeLimit.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //改变第1-第3个字体大小
        spannable2.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(this, 16)), loanTimeLimit.length(), textStr2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvProductLoanDate.setText(spannable2); // 原价

        tvProductItemPart11.setText("￥" + response.HKAmountPerStageStr);
        tvProductItemPart12.setText(response.stages + "期");
        tvProductItemPart2.setText("￥" + response.HKAmountStr);
        tvProductItemPart3.setText(response.LXRateStr);


        // 中部认证项 数据
        if (response.userCertifyInfos == null || response.userCertifyInfos.size() <= 0) {
            rlLoanCertLayout.setVisibility(View.GONE);
        } else {
            rlLoanCertLayout.setVisibility(View.VISIBLE);

            if (llProductLoanCertItem != null) {
                llProductLoanCertItem.removeAllViews();
            }

            List<LoanDetailInfoResponse.UserCertifyInfosBean> userCertifyList = response.userCertifyInfos;

            for (int i = 0; i < userCertifyList.size(); i++) {
                LoanCertItemView itemView = new LoanCertItemView(mActivity);
                itemView.setIcon(userCertifyList.get(i).icon);
                itemView.setTitle(userCertifyList.get(i).name);
                itemView.setItemStatus(userCertifyList.get(i).status);

                llProductLoanCertItem.addView(itemView);
            }
        }

        // 底部， 说明数据
        if (TextUtils.isEmpty(response.moneyExplanation)) {
            tvLoanDetailFeeDesc.setVisibility(View.GONE);
        } else {
            tvLoanDetailFeeDesc.setVisibility(View.VISIBLE);
            tvLoanDetailFeeDesc.setText(response.moneyExplanation);
        }

        if (TextUtils.isEmpty(response.HKmentMode) && TextUtils.isEmpty(response.pHKment) && TextUtils.isEmpty(response.YQPolicy)) {
            rlLoanDetailRepayLayout.setVisibility(View.GONE);
        } else {
            rlLoanDetailRepayLayout.setVisibility(View.VISIBLE);

            // 还款方式
            if (TextUtils.isEmpty(response.HKmentMode)) {
                llLoanRepayPart1.setVisibility(View.GONE);
            } else {
                llLoanRepayPart1.setVisibility(View.VISIBLE);
                tvLoanDetailRepayPart1.setText(response.HKmentMode);
            }
            // 提前还款
            if (TextUtils.isEmpty(response.pHKment)) {
                llLoanRepayPart2.setVisibility(View.GONE);
            } else {
                llLoanRepayPart2.setVisibility(View.VISIBLE);
                tvLoanDetailRepayPart2.setText(response.pHKment);
            }
            // 逾期政策
            if (TextUtils.isEmpty(response.YQPolicy)) {
                llLoanRepayPart3.setVisibility(View.GONE);
            } else {
                llLoanRepayPart3.setVisibility(View.VISIBLE);
                tvLoanDetailRepayPart3.setText(response.YQPolicy);
            }
        }

        /**
         *  1 普通进入， 2 商详进入
         *  只有普通进入， 才展示底部状态
         */
        if (jumpType == 1) {
            // 最底部按钮 和协议处理
            // //状态 1-确认申请 2-审批中 3-审批被拒
            llLoanBottomLayout.setVisibility(View.VISIBLE);
            switch (response.status) {
                case 1:
                    tvProductBtn.setEnabled(true);
                    rlLoanErrStatus.setVisibility(View.GONE);
                    loadContractData();
                    break;
                case 2:
                    tvProductBtn.setEnabled(false);
                    tvProductBtn.setText("已有订单正在审批");
                    tvProductBtn.setTextColor(Color.parseColor("#999999"));
                    rlLoanErrStatus.setVisibility(View.VISIBLE);
                    tvLoanErrMsg.setText("您当前有订单在申请此分期，加急审批中···");
                    rlLoanAgreement.setVisibility(View.GONE);
                    break;
                case 3:
                    tvProductBtn.setEnabled(false);
                    tvProductBtn.setText("资质不足暂无法申请");
                    tvProductBtn.setTextColor(Color.parseColor("#999999"));
                    rlLoanErrStatus.setVisibility(View.VISIBLE);
                    tvLoanErrMsg.setText("您的资质暂不符合申请条件");
                    rlLoanAgreement.setVisibility(View.GONE);
                    break;
            }
        } else {
            llLoanBottomLayout.setVisibility(View.GONE);
            rlLoanAgreement.setVisibility(View.GONE);
        }
    }

    @Override
    public void getContract(List<ContractResponse.ContractsBean> contractsBeans) {
        if (contractsBeans != null && contractsBeans.size() > 0) {
            rlLoanAgreement.setVisibility(View.VISIBLE);
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
            stringBuilder.append("我已阅读并同意");
            int index = stringBuilder.length();
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.color_9b9b9b));
            stringBuilder.setSpan(colorSpan, 0, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            for (ContractResponse.ContractsBean item : contractsBeans) {
                if (item.contractName != null) {
                    stringBuilder.append("《");
                    stringBuilder.append(item.contractName);
                    stringBuilder.append("》");
                }
            }
            for (final ContractResponse.ContractsBean item : contractsBeans) {
                if (item.contractName != null && item.contractUrl != null) {
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            PageRouter.resolve(getContext(), item.contractUrl);
                        }
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            //去掉可点击文字的下划线
                            ds.setUnderlineText(false);
                        }
                    };
                    //文本可点击，有点击事件
                    int startIndex = index;
                    int endIndex = index + item.contractName.length() + 2;
                    //endIndex = endIndex<stringBuilder.length()?endIndex:stringBuilder.length()-1;
                    stringBuilder.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    index = endIndex;
                }
            }
            // 设置此方法后，点击事件才能生效
            tvLoanAgreementText.setMovementMethod(LinkMovementMethod.getInstance());
            tvLoanAgreementText.setText(stringBuilder);
        } else {
            rlLoanAgreement.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginFacade.isLogin()) {
            if (!isFirstIn) {
                loadData();
            }
        }

        isFirstIn = false;
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
