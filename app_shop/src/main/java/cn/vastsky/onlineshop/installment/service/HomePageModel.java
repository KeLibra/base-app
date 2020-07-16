package cn.vastsky.onlineshop.installment.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.vastsky.onlineshop.installment.common.net.BaseCallBack;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.net.ServiceFactory;
import cn.vastsky.onlineshop.installment.model.bean.request.HomeProductListRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.InitConfigResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.HomeProductListResponse;


/**
 * @author: kezy
 * @create_time 2019/10/22
 * @description:
 */
public class HomePageModel {

    public static HomePageService homeService = ServiceFactory.createService(HomePageService.class);

    /**
     * 首页信息接口
     *
     * @param iCallBack
     */
    public static void getHomepageData(final ICallBack<HomePageInfoResponse> iCallBack) {
        homeService.getHomepageData(new Object())
                .enqueue(new BaseCallBack<HomePageInfoResponse>() {
                    @Override
                    public void onSuccess(@Nullable HomePageInfoResponse homePageInfoBean) {
                        iCallBack.onSuccess(homePageInfoBean);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }


    /**
     * 首页信息接口
     *
     * @param iCallBack
     */
    public static void getHomeProductListData(String categoryID, String type, final ICallBack<HomeProductListResponse> iCallBack) {
        homeService.getHomeProductListData(new HomeProductListRequest(categoryID, type))
                .enqueue(new BaseCallBack<HomeProductListResponse>() {
                    @Override
                    public void onSuccess(@Nullable HomeProductListResponse homePageInfoBean) {
                        iCallBack.onSuccess(homePageInfoBean);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void getInitConfig(ICallBack<InitConfigResponse> iCallBack) {
        homeService.getInitConfig(new Object())
                .enqueue(new BaseCallBack<InitConfigResponse>() {
                    @Override
                    public void onSuccess(@Nullable InitConfigResponse response) {
                        iCallBack.onSuccess(response);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }


}
