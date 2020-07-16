package cn.vastsky.onlineshop.installment.model.bean.response;

import java.util.List;

import cn.vastsky.libs.common.base.bean.BaseBean;

/**
 * @author: kezy
 * @create_time 2019/11/15
 * @description:
 */
public class HomeProductListResponse extends BaseBean {

    public String id;
    public String title;
    public List<HomePageInfoResponse.SubjectBean.ProductBean> product;
}
