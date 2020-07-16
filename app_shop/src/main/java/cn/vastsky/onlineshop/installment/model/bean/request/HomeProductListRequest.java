package cn.vastsky.onlineshop.installment.model.bean.request;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/14
 * @description:
 */
public class HomeProductListRequest extends BaseBean {
    public String id;
    public String type;

    public HomeProductListRequest(String id, String type) {
        this.id = id;
        this.type = type;
    }
}
