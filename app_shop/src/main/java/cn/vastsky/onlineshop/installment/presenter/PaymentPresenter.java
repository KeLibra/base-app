package cn.vastsky.onlineshop.installment.presenter;

import org.jetbrains.annotations.NotNull;

import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.onlineshop.installment.base.BasePresenter;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.contract.PaymentContract;
import cn.vastsky.onlineshop.installment.model.bean.response.OrderPayResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PayResultResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PaymentInfoResponse;
import cn.vastsky.onlineshop.installment.service.PaymentModel;

/**
 * @author: kezy
 * @create_time 2019/11/11
 * @description:
 */
public class PaymentPresenter
        extends BasePresenter<PaymentContract.PaymentContractView>
        implements PaymentContract.PaymentContractPresenter {

    public PaymentPresenter(@NotNull PaymentContract.PaymentContractView view) {
        super(view);
    }


    @Override
    public void getPaymentPageData(String orderSn) {
        getView().showLoading("");
        PaymentModel.getPaymentPageInfo(orderSn, new ICallBack<PaymentInfoResponse>() {
            @Override
            public void onSuccess(PaymentInfoResponse paymentInfoResponse) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    if (paymentInfoResponse != null) {
                        getView().hideEmptyPage();
                        getView().showPaymentPageView(paymentInfoResponse);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                getView().showEmptyPage(-1);
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void getPayOrderInfo(long orderId, final int payType) {
        getView().showLoading("");
        PaymentModel.getOrderPayInfo(orderId, payType, new ICallBack<OrderPayResponse>() {
            @Override
            public void onSuccess(OrderPayResponse orderPayResponse) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    if (payType == 1) {
                        getView().callAliPayView(orderPayResponse.orderInfo);
                    } else if (payType == 2) {
                        getView().callWechatPayView(orderPayResponse.orderInfo);
                    } else if (payType == 3) {
                        getView().callLoanPayView();
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void checkPayResultStatus(String orderSn) {
        getView().showLoading("");
        PaymentModel.checkPayStatus(orderSn, new ICallBack<PayResultResponse>() {
            @Override
            public void onSuccess(PayResultResponse payResult) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    // status == 0, 订单状态未支付， 不需要跳页\
                    if (payResult.status != 0) {
                        getView().goPayResultView(payResult);
                    } else {
                        LogUtils.d("---------: " + payResult.status);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                ToastUtil.show(err_msg);
            }
        });
    }
}
