package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 *
 */

public class LoginRequest extends BaseBean {
    public String telephone;
    public String code;

    public LoginRequest(String telephone, String code) {
        this.telephone = telephone;
        this.code = code;
    }
}
