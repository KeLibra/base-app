package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/21
 * @description: 认证项 请求
 */
public class LoanCertRequest extends BaseBean {
    public int certifyType; //1-基本信息 3-补充信息
    public int instProductId; // 信贷产品id
    public String orderSn; // 商城订单号 （仅收银台入口传入）
    public String callbackUrl; // callback url 回调url

    public LoanCertRequest(int certifyType, int instProductId, String orderSn, String callbackUrl) {
        this.certifyType = certifyType;
        this.instProductId = instProductId;
        this.orderSn = orderSn;
        this.callbackUrl = callbackUrl;
    }
}
