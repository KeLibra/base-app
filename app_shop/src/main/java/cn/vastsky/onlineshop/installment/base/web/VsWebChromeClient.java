package cn.vastsky.onlineshop.installment.base.web;


import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;

import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.onlineshop.installment.model.bean.WebDeviceInfo;


/**
 * @author: kezy
 * @create_time 2019/10/30
 * @description:
 */
public class VsWebChromeClient extends WebChromeClient {

    private onTitleChangedListener listener;

    public VsWebChromeClient(onTitleChangedListener listener) {
        this.listener = listener;
    }

    public interface onTitleChangedListener {
        void onTitleChanged(String title);

        void onProgressChanged(int progress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (listener != null) {
            if (!TextUtils.isEmpty(title) && !view.getUrl().contains(title)) {
                //设置页面title
                listener.onTitleChanged(title);
            }
        }
    }

    @Override
    public void onProgressChanged(WebView webView, int newProgress) {
        super.onProgressChanged(webView, newProgress);
        if (listener != null) {
            listener.onProgressChanged(newProgress);
        }
        LogUtils.i("=====msg_webview", "onProgressChanged   url: " + webView.getUrl());

        if (webView != null) {
            loadDeviceInfo(webView);
        }
    }

    private void loadDeviceInfo(WebView webView) {
        try {

            WebDeviceInfo info = new WebDeviceInfo();
            Gson gson = new Gson();
            String deviceInfo = gson.toJson(info);

            webView.loadUrl("javascript:if(window.NMBridge && typeof window.NMBridge.deviceInfo==='undefined'){window.NMBridge.deviceInfo=" + deviceInfo + "}");

            LogUtils.d("====msg_web", "nm bridge: " + deviceInfo);
        } catch (Exception e) {
            LogUtils.e("==msg_web_load_err", e.getMessage());
        }
    }
}
