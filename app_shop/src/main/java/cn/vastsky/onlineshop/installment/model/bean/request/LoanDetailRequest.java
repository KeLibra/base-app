package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/21
 * @description: 信贷产品详情页面 请求 request
 */
public class LoanDetailRequest extends BaseBean {
    public String amount; //金额
    public int instProductId; // 信贷产品id
    public String orderSn; // 商城订单号 （仅收银台入口传入）

    /**
     * 获取 信贷产品详情时候使用
     *
     * @param amount
     * @param instProductId
     * @param orderSn
     */
    public LoanDetailRequest(String amount, int instProductId, String orderSn) {
        this.amount = amount;
        this.instProductId = instProductId;
        this.orderSn = orderSn;
    }

    /**
     * 获取认证步骤时候使用
     *
     * @param instProductId
     * @param orderSn
     */
    public LoanDetailRequest(int instProductId, String orderSn) {
        this.instProductId = instProductId;
        this.orderSn = orderSn;
    }


    /**
     * 获取 人脸识别结果时 使用
     *
     * @param orderSn
     */
    public LoanDetailRequest(String orderSn) {
        this.orderSn = orderSn;
    }
}
