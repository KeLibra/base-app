package cn.vastsky.onlineshop.installment.service;

import cn.vastsky.onlineshop.installment.model.bean.base.BaseResponse;
import cn.vastsky.onlineshop.installment.model.bean.request.HomeProductListRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.InitConfigResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.HomeProductListResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public interface HomePageService {

    // 首页信息接口
    @POST("/home/content")
    Call<BaseResponse<HomePageInfoResponse>> getHomepageData(@Body Object o);

    // 首页 获取 单产品列表接口
    @POST("/home/category")
    Call<BaseResponse<HomeProductListResponse>> getHomeProductListData(@Body HomeProductListRequest categoryID);


    // 首页 获取 单产品列表接口
    @POST("/init/config")
    Call<BaseResponse<InitConfigResponse>> getInitConfig(@Body Object o);

}
