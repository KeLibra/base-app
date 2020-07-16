package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 *
 */

public class SendAuthCodeRequest extends BaseBean {
    public String telephone;

    public SendAuthCodeRequest(String telephone) {
        this.telephone = telephone;
    }
}
