package cn.vastsky.onlineshop.installment.contract;


import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;
import cn.vastsky.onlineshop.installment.base.impl.IBaseView;
import cn.vastsky.onlineshop.installment.model.bean.response.PayResultResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PaymentInfoResponse;

/**
 * Created by airal on 2019/1/12.
 */

public interface PaymentContract {


    interface PaymentContractView extends IBaseView {
        void showPaymentPageView(PaymentInfoResponse paymentInfoResponse);

        void callAliPayView(String orderInfo);

        void callWechatPayView(String orderInfo);

        void callLoanPayView();

        void goPayResultView(PayResultResponse payResult);
    }

    interface PaymentContractPresenter extends IBasePresenter<PaymentContractView> {

        void getPaymentPageData(String orderSn);

        void getPayOrderInfo(long orderId, int payType);

        void checkPayResultStatus(String orderSn);
    }
}
