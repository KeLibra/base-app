package cn.vastsky.onlineshop.installment.contract;

import cn.vastsky.onlineshop.installment.base.impl.IBasePresenter;
import cn.vastsky.onlineshop.installment.base.impl.IBaseView;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;

/**
 * @author: kezy
 * @create_time 2019/11/25
 * @description:
 */
public interface UserMineContract {
    interface UserMineContractView extends IBaseView {
        void showUserMinePageView(UserMineResponse response);
    }

    interface UserMineContractPresenter extends IBasePresenter<UserMineContract.UserMineContractView> {
        void getUserMinePageInfo();
    }
}
