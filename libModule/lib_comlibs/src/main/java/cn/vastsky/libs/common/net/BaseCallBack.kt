package cn.vastsky.libs.common.net

import android.text.TextUtils
import cn.vastsky.lib.base.common.config.AppConfigLib
import cn.vastsky.lib.utils.LogUtils
import cn.vastsky.lib.base.common.config.userinfo.LoginFacade
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import cn.vastsky.libs.common.model.bean.base.BaseResponse

/**
 *
 * @author: kezy
 * @create_time 2019/10/22
 * @description:  统一处理请示返回
 *
 */

abstract class BaseCallBack<T> : Callback<BaseResponse<T>> {

    abstract fun onSuccess(t: T?)

    abstract fun onFail(code: String, errMsg: String)

    override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {

        if (response.isSuccessful && response.body() != null && "200".equals(response.body()!!.code)) {
            onSuccess(response.body()!!.data)
        } else {
            if (response.isSuccessful) {
                if (response.body() != null && response.body()!!.code != null && response.body()!!.message != null) {

                    LogUtils.e("-----Net error" + response.body().toString())
                    if (response.body() == null || response.body()!!.message == null) {
                        onFail("-2", "返回数据为空，请稍后重试")
                    } else {
                        if (response.body()!!.code.equals("930001") || response.body()!!.code.equals("930002")) {
                            if (AppConfigLib.getCurrentActivityContext() != null) {
                                LoginFacade.clearUserInfo()
                                LoginFacade.setLogin(false)
                                LoginFacade.goLoginView(AppConfigLib.getCurrentActivityContext())
                            }
                        }
                        onFail(response.body()!!.code, response.body()!!.message)
                    }
                } else {
                    if (response.body() == null || TextUtils.isEmpty(response.body()!!.message)) {
                        onFail(response.code().toString(), "服务器开小差了")
                    } else {
                        onFail(response.code().toString(), response.body()!!.message)
                    }
                }
            } else {
                onFail(response.code().toString(), "请求失败，请稍后再试")
            }
        }
    }

    override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {

        if (t != null) {
            LogUtils.e("-----Net error : " + t.message + "")
            LogUtils.e("-----Net error : " + t.toString() + "")
            LogUtils.e("-----Net error : " + t.printStackTrace() + "")
            if (!t.toString().contains("ConnectException")) {
                onFail("-1", "请求失败，请稍后重试")
            } else {
                onFail("-1", "网络连接失败")
            }
        } else {
            LogUtils.e("-----Net error : ------------- call ： " + call)
            onFail("-1", "网络连接失败")
        }
    }
}
 