package cn.vastsky.onlineshop.installment.contract;


import java.util.List;

import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;
import cn.vastsky.onlineshop.installment.base.impl.IBaseView;
import cn.vastsky.onlineshop.installment.model.bean.response.HomePageInfoResponse;

/**
 * Created by airal on 2019/1/12.
 */

public interface HomepageContract {


    interface HomepageContractView extends IBaseView {
        void showHomepageView(HomePageInfoResponse homePageBean);

        // 首页商品 列表view
        void showProductListView(List<HomePageInfoResponse.SubjectBean.ProductBean> productBeanList, int currentPosition);

        void showProductEmptyView(int currentPosition);
    }

    interface HomepageContractPresenter extends IBasePresenter<HomepageContractView> {
        void loadHomepageData();

        // 首页商品列表 data
        void loadProductListData(String categoryId,String type, int currentPosition);

    }
}
