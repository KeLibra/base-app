package cn.vastsky.onlineshop.installment.presenter;

import org.jetbrains.annotations.NotNull;

import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.base.BasePresenter;
import cn.vastsky.onlineshop.installment.contract.HomepageContract;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.HomeProductListResponse;
import cn.vastsky.onlineshop.installment.service.HomePageModel;

/**
 * @author: kezy
 * @create_time 2019/11/11
 * @description:
 */
public class HomepagePresenter
        extends BasePresenter<HomepageContract.HomepageContractView>
        implements HomepageContract.HomepageContractPresenter {

    public HomepagePresenter(@NotNull HomepageContract.HomepageContractView view) {
        super(view);
    }

    @Override
    public void loadHomepageData() {
        getView().showLoading("");
        HomePageModel.getHomepageData(new ICallBack<HomePageInfoResponse>() {
            @Override
            public void onSuccess(HomePageInfoResponse homePageInfoBean) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().hideEmptyPage();
                    if (homePageInfoBean != null) {
                        getView().showHomepageView(homePageInfoBean);
                    } else {
                        getView().showEmptyPage(2019);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                getView().hideLoading();
                getView().showEmptyPage(-1);
                ToastUtil.show(err_msg);
            }
        });
    }

    @Override
    public void loadProductListData(String categoryId, String type, int currentPosition) {

        HomePageModel.getHomeProductListData(categoryId, type, new ICallBack<HomeProductListResponse>() {
            @Override
            public void onSuccess(HomeProductListResponse homeProductListResponse) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    if (homeProductListResponse != null && homeProductListResponse.product != null && homeProductListResponse.product.size() > 0) {
                        getView().showProductListView(homeProductListResponse.product, currentPosition);
                    } else {
                        getView().showProductEmptyView(currentPosition);
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                LogUtils.e("========msg err " + err_msg);
                ToastUtil.show(err_msg);
                getView().hideLoading();
                getView().showProductEmptyView(currentPosition);
            }
        });
    }
}
