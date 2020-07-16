package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/15
 * @description:
 */
public class PaymentRequest extends BaseBean {

    public String orderSn;

    public PaymentRequest(String orderSn) {
        this.orderSn = orderSn;
    }
}
