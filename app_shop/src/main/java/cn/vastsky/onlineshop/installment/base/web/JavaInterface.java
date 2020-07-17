package cn.vastsky.onlineshop.installment.base.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.vastsky.lib.base.common.config.AppConfigLib;
import cn.vastsky.lib.base.common.store.Store;
import cn.vastsky.lib.utils.LogUtils;
import cn.vastsky.lib.utils.ToastUtil;
import cn.vastsky.lib.base.common.config.userinfo.LoginFacade;
import cn.vastsky.lib.router.PageRouter;
import cn.vastsky.onlineshop.installment.common.net.util.MD5;
import cn.vastsky.onlineshop.installment.model.bean.base.BaseRequest;

/**
 * Created by airal on 2018/9/17.
 */
@SuppressLint("JavascriptInterface")
public class JavaInterface {
    private VsWebviewActivity activity;

    private Gson gson;

    public JavaInterface(VsWebviewActivity activity) {
        this.activity = activity;
        if (gson == null) {
            gson = new Gson();
        }
    }


    /**
     * 打开新的webview
     */
    @JavascriptInterface
    public void openWebView(final String params) {
//        {
//            url: "aaaa";
//            isOtherLink : false;  // true 是， false 不是
//        }
        LogUtils.d("---------- openWebView ： " + params);
        try {
            JSONObject jsonObject = new JSONObject(params);
            String url = jsonObject.get("url").toString();
            boolean isOtherLink = jsonObject.optBoolean("isOtherLink", false);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isOtherLink", isOtherLink);
            PageRouter.resolve(activity, url, bundle);

            LogUtils.d("====msg_openWebView", " isOtherLink： " + isOtherLink);
        } catch (Exception e) {
            LogUtils.e("====msg_openWebView_err", e.getMessage());
        }
    }

    /**
     * 关闭webview
     */
    @JavascriptInterface
    public void close() {
        LogUtils.d("---------- close ---------- ");
        activity.dofinish();
    }

    /**
     * 关闭webview
     */
    @JavascriptInterface
    public void close(String params) {
        LogUtils.d("---------- close ---------- " + params);
        activity.dofinish();
    }


    /**
     * 异步上报
     */
    @JavascriptInterface
    public void getUserInfo(String param) {
        LogUtils.d("---------- getUserInfo ---------- " + param);
        try {
            JSONObject jsonObject = new JSONObject(param);
            String callBackName = jsonObject.get("callback").toString();

            JSONObject userInfo = new JSONObject();
            userInfo.put("token", LoginFacade.getToken());
            userInfo.put("phone", LoginFacade.getPhone());
            LogUtils.d("---- getUserInfo ------ callBackName ---------- " + "javascript:if(window." + callBackName + "){window." + callBackName + "('" + userInfo.toString() + "')};");
            activity.loadJsCallback("javascript:if(window." + callBackName + "){window." + callBackName + "('" + userInfo.toString() + "')};");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /**
     * 回退，如页面无法回退，关闭webview
     */
    @JavascriptInterface
    public void goBack(String params) {
        LogUtils.d("---------- goBack ---------- " + params);
        activity.webGoBack();
    }

    /**
     * 通过APP得到验签签名
     */
    @JavascriptInterface
    public void sign(String param) {
        LogUtils.d("---------- sign ---------- " + param);
        try {
            JSONObject object = new JSONObject(param);
            String baseParams = object.optString("baseParams");
            String bizParams = object.optString("bizParams");
            String timestamp = object.optString("timestamp");
            String callback = object.optString("callback");

            String params = baseParams + bizParams + timestamp + BaseRequest.NEW_KEY;
            String md5Signed = MD5.sign(baseParams + bizParams + timestamp, BaseRequest.NEW_KEY);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sign", md5Signed);
            LogUtils.d("---------- sign ---- callback ------ " + "javascript:if(window." + callback + "){window." + callback + "('" + jsonObject.toString() + "')};");
            activity.loadJsCallback("javascript:if(window." + callback + "){window." + callback + "('" + jsonObject.toString() + "')};");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录
     */
    @JavascriptInterface
    public void login(String param) {

        LogUtils.d("---------- login ---------- " + param);
        try {
            JSONObject object = new JSONObject(param);
            String callback = object.optString("callback");
            Bundle bundle = new Bundle();
            bundle.putString("loginCallback", callback);
            LoginFacade.goLoginView(activity, bundle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知app登录失效（app仅清除登录态，不弹出登录）
     */
    @JavascriptInterface
    public void loginInvalid(String param) {

        LogUtils.d("---------- login ---------- " + param);
        try {
            LoginFacade.clearUserInfo();
            JSONObject object = new JSONObject(param);
            String callback = object.optString("callback");
            activity.loadJsCallback("javascript:if(window." + callback + "){window." + callback + "('" + "" + "')};");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取缓存 (该缓存APP与H5共用)
     */
    @JavascriptInterface
    public void getDiskCache(String param) {
        LogUtils.d("---------- getDiskCache ---------- " + param);
        try {
            JSONObject object = new JSONObject(param);
            String key = object.optString("key");
            String callback = object.optString("callback");

            String valueStr = Store.sp(AppConfigLib.getContext()).getString(key, "");

            JSONObject cache = new JSONObject();
            cache.put(key, valueStr);
            LogUtils.d("---------- getDiskCache --- callback ------- " + "javascript:if(window." + callback + "){window." + callback + "('" + cache + "')};");
            activity.loadJsCallback("javascript:if(window." + callback + "){window." + callback + "('" + cache + "')};");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置缓存(该缓存APP与H5共用)
     */
    @JavascriptInterface
    public void setDiskCache(String param) {
        LogUtils.d("---------- setDiskCache ---------- " + param);
        try {
            JSONObject object = new JSONObject(param);
            String key = object.optString("key");
            String value = object.optString("value");
            String callback = object.optString("callback");

            LogUtils.d("---------- setDiskCache ------key ---- " + key + " -- value -- " + value);
            Store.sp(AppConfigLib.getContext()).putString(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 展示 loading框
     */
    @JavascriptInterface
    public void showLoading(String params) {
        LogUtils.d("---------- showLoading ---------- ");
//        activity.showProgressDialog("");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showLoading("");
            }
        });
    }

    /**
     * 关闭 loading框
     */
    @JavascriptInterface
    public void hideLoading(String params) {
        LogUtils.d("---------- hideLoading ---------- ");
//        activity.dismissProgressDialog();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.hideLoading();
            }
        });
    }


    /**
     * 显示没有网络空展示页面
     */
    @JavascriptInterface
    public void showError(String params) {
        LogUtils.d("---------- showError ---------- ");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showEmptyPage(-1);
            }
        });
    }

    /**
     * 关掉没有网络空展示页面
     */
    @JavascriptInterface
    public void hideError(String params) {
        LogUtils.d("---------- hideError ---------- ");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.hideEmptyPage();
            }
        });
    }

    /**
     * show toast
     */
    @JavascriptInterface
    public void showToast(String param) {
        LogUtils.d("---------- showToast ---------- " + param);
        JSONObject object = null;
        try {
            object = new JSONObject(param);
            String text = object.optString("text");
            if (Looper.myLooper() == Looper.getMainLooper()) {
                // UI线程
                ToastUtil.showNewToast(text);
            } else {
                // 非UI线程
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showNewToast(text);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置导航右上角点击按钮, 若text为""，则移除按钮
     */
    @JavascriptInterface
    public void setNavRightItem(String param) {
        LogUtils.d("---------- setNavRightItem ---------- " + param);
        JSONObject object = null;
        try {
            object = new JSONObject(param);
            String text = object.optString("text");
            String action = object.optString("action");

            activity.setRightText(text, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.d("---------- setNavRightItem ---------- " + "javascript:if(window." + action + "){window." + action + "()};");
                    activity.loadJsCallback("javascript:if(window." + action + "){window." + action + "('" + "" + "')};");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏 原生 导航栏
     */
    @JavascriptInterface
    public void hideNavigation(String param) {
        LogUtils.d("----------  hideNavigation  ---------- " + param);
        activity.hideNavigation();
    }



    /**
     * 跳应用市场的方法
     */
    @JavascriptInterface
    public void goAppMarket(String param) {
        LogUtils.d("----------  goAppMarket  ---------- " + param);
        try {
            Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show("您的手机没有安装Android应用市场");
            e.printStackTrace();
        }
    }

    /**
     * 跳应用市场的方法
     */
    @JavascriptInterface
    public void detectUpdate(String param) {

    }
}
