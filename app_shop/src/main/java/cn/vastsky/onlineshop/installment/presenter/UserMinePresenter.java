package cn.vastsky.onlineshop.installment.presenter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.onlineshop.installment.base.BasePresenter;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.contract.UserMineContract;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;
import cn.vastsky.onlineshop.installment.service.UserModel;

/**
 * @author: kezy
 * @create_time 2019/11/25
 * @description:
 */
public class UserMinePresenter
        extends BasePresenter<UserMineContract.UserMineContractView>
        implements UserMineContract.UserMineContractPresenter {
    public UserMinePresenter(@NotNull UserMineContract.UserMineContractView view) {
        super(view);
    }

    @Override
    public void getUserMinePageInfo() {
        getView().showLoading("");
        UserModel.getAccountInfo(new ICallBack<UserMineResponse>() {
            @Override
            public void onSuccess(@Nullable UserMineResponse response) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().hideEmptyPage();
                    if (response != null) {
                        getView().showUserMinePageView(response);
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
}
