package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/15
 * @description:
 */
public class OrderPayRequest extends BaseBean {
    public long orderId; // 订单id
    public int payType; // 0 未支付 1 支付宝 2 微信 3 分期

    public OrderPayRequest(long orderId, int payType) {
        this.orderId = orderId;
        this.payType = payType;
    }
}
