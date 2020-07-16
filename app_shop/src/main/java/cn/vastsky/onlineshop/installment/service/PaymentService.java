package cn.vastsky.onlineshop.installment.service;

import cn.vastsky.onlineshop.installment.model.bean.base.BaseResponse;
import cn.vastsky.onlineshop.installment.model.bean.request.OrderPayRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.PaymentRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.OrderPayResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PayResultResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.PaymentInfoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public interface PaymentService {


    // 收银台接口
    @POST("/cashier/orderSn")
    Call<BaseResponse<PaymentInfoResponse>> getPaymentInfoData(@Body PaymentRequest request);

    // 获取 订单info
    @POST("/cashier/pay")
    Call<BaseResponse<OrderPayResponse>> getOrderPay(@Body OrderPayRequest request);

    // 查询支付结果
    @POST("/order/statusCheck")
    Call<BaseResponse<PayResultResponse>> checkPayStatus(@Body PaymentRequest request);

}
