package cn.vastsky.onlineshop.installment.service;

import cn.vastsky.onlineshop.installment.model.bean.base.BaseResponse;
import cn.vastsky.onlineshop.installment.model.bean.request.LoginRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.SendAuthCodeRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.UserTokenResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.AppUpdateResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.AuthCodeResponse;
import cn.vastsky.onlineshop.installment.model.bean.response.UserMineResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: kezy
 * @create_time 2019/11/24
 * @description:
 */
public interface UserService {

    /**
     * 获取验证码接口
     */
    @POST("/sso/getAuthCode")
    Call<BaseResponse<AuthCodeResponse>> getAuthCode(@Body SendAuthCodeRequest request);

    /**
     * 登陆接口
     */
    @POST("/sso/login")
    Call<BaseResponse<UserTokenResponse>> doLogin(@Body LoginRequest request);

    /**
     * 我的 页面
     */
    @POST("member/account")
    Call<BaseResponse<UserMineResponse>> getAccountInfo(@Body Object request);

    /**
     * app 更新接口
     */
    @POST("version/update/check")
    Call<BaseResponse<AppUpdateResponse>> checkUpdate(@Body Object request);
}
