package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 *
 */

public class ContractRequest extends BaseBean {
    public String orderSn; //订单编号
    public int instProductId; // 信贷产品id
    public int type;  // 合同类型 1-借款合同 2-分期协议 3-基本信息协议
    public String returnUrl; //"aa" /*返回地址  string Y  合同回调地址*/
}
