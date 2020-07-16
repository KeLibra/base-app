package cn.vastsky.onlineshop.installment.model.bean.response;

import cn.vastsky.libs.common.base.bean.BaseBean;


/**
 * @author: kezy
 * @create_time 2019/11/16
 * @description:
 */
public class PayResultResponse extends BaseBean {

    /**
     * orderId : 111
     * orderSn :
     * payType : 3
     * status : 2
     * orderDetailUrl: url
     */

    public int orderId;  //订单ID
    public String orderSn;  //订单编号
    public int payType;  //0 未支付 1 支付宝 2 微信 3 分期
    public int status; // 0 待付款 1 正在付款  2 分期审批中  3 分期付款成功  4 付款成功
    public String orderDetailUrl; // 订单详情的页面url

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
