package cn.vastsky.onlineshop.installment.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import cn.vastsky.onlineshop.installment.common.net.BaseCallBack;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.common.net.ServiceFactory;
import cn.vastsky.onlineshop.installment.model.bean.request.LoginRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.SendAuthCodeRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.UserTokenResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.AppUpdateResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.AuthCodeResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;

/**
 * @author: kezy
 * @create_time 2019/11/24
 * @description:
 */
public class UserModel {

    public static UserService userService = ServiceFactory.createService(UserService.class);

    public static void getAuthCode(String telePhone, ICallBack<AuthCodeResponse> iCallBack) {
        userService.getAuthCode(new SendAuthCodeRequest(telePhone))
                .enqueue(new BaseCallBack<AuthCodeResponse>() {
                    @Override
                    public void onSuccess(@Nullable AuthCodeResponse s) {
                        iCallBack.onSuccess(s);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void doLogin(String telePhone, String smsCode, ICallBack<UserTokenResponse> iCallBack) {
        userService.doLogin(new LoginRequest(telePhone, smsCode))
                .enqueue(new BaseCallBack<UserTokenResponse>() {
                    @Override
                    public void onSuccess(@Nullable UserTokenResponse response) {
                        iCallBack.onSuccess(response);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void getAccountInfo(ICallBack<UserMineResponse> iCallBack) {
        userService.getAccountInfo(new Object())
                .enqueue(new BaseCallBack<UserMineResponse>() {
                    @Override
                    public void onSuccess(@Nullable UserMineResponse response) {
                        iCallBack.onSuccess(response);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }

    public static void checkUpdate(ICallBack<AppUpdateResponse> iCallBack) {
        userService.checkUpdate(new Object())
                .enqueue(new BaseCallBack<AppUpdateResponse>() {
                    @Override
                    public void onSuccess(@Nullable AppUpdateResponse response) {
                        iCallBack.onSuccess(response);
                    }

                    @Override
                    public void onFail(@NotNull String code, @NotNull String errMsg) {
                        iCallBack.onFailure(errMsg);
                    }
                });
    }
}
