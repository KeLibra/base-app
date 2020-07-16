package cn.vastsky.onlineshop.installment.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.vastsky.onlineshop.installment.common.net.BaseCallBack;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.net.ServiceFactory;
import cn.vastsky.onlineshop.installment.model.bean.request.OrderPayRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.PaymentRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.OrderPayResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PayResultResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PaymentInfoResponse;


/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public class PaymentModel {

    public static PaymentService payService = ServiceFactory.createService(PaymentService.class);

    /**
     * 首页信息接口
     *
     * @param iCallBack
     */
    public static void getPaymentPageInfo(String orderSn, final ICallBack<PaymentInfoResponse> iCallBack) {
        payService.getPaymentInfoData(new PaymentRequest(orderSn))
                .enqueue(new BaseCallBack<PaymentInfoResponse>() {
                    @Override
                    public void onSuccess(@Nullable PaymentInfoResponse paymentInfoResponse) {
                        iCallBack.onSuccess(paymentInfoResponse);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void getOrderPayInfo(long orderId, int payType, ICallBack<OrderPayResponse> iCallBack) {
        payService.getOrderPay(new OrderPayRequest(orderId, payType))
                .enqueue(new BaseCallBack<OrderPayResponse>() {
                    @Override
                    public void onSuccess(@Nullable OrderPayResponse orderPayResponse) {
                        iCallBack.onSuccess(orderPayResponse);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void checkPayStatus(String orderSn, ICallBack<PayResultResponse> iCallBack) {

        payService.checkPayStatus(new PaymentRequest(orderSn))
                .enqueue(new BaseCallBack<PayResultResponse>() {
                    @Override
                    public void onSuccess(@Nullable PayResultResponse orderPayResponse) {
                        iCallBack.onSuccess(orderPayResponse);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

}
