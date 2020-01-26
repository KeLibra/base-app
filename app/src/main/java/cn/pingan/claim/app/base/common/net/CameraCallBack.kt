package cn.pingan.claim.app.base.common.net.util

import cn.kezy.libs.common.utils.LogUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 * @author: kezy
 * @create_time 2019/10/22
 * @description:  设备统一处理请示返回
 *
 */

abstract class CameraCallBack<T> : Callback<String> {

    abstract fun onSuccess(t: String)

    abstract fun onFail(code: String, errMsg: String)

    override fun onResponse(call: Call<String>, response: Response<String>) {

        LogUtils.e("-----Net error 111: " + response.body())
        if (response != null && response.isSuccessful && response.body() != null) {
            onSuccess(response.body().toString())
        } else {

            onFail(response.code().toString(), "请求失败，请稍后再试")
        }
    }

    override fun onFailure(call: Call<String>, t: Throwable) {

        if (t != null) {
            LogUtils.e("-----Net error 111: " + t.message + "")
            LogUtils.e("-----Net error 222: " + t.toString() + "")
            LogUtils.e("-----Net error 333: " + t.printStackTrace() + "")
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
 