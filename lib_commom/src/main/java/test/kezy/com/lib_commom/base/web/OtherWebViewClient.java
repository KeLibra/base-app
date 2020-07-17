package test.kezy.com.lib_commom.base.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.vastsky.lib.utils.LogUtils;


/**
 * @author: kezy
 * @create_time 2019/10/30
 * @description:
 */
public class OtherWebViewClient extends WebViewClient {

    private Context context;

    public OtherWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {

        LogUtils.i("=====msg_webview", " OtherWebViewClient ---- shouldOverrideUrlLoading   url: " + webView.getUrl());
        if (url != null) {

            webView.loadUrl(url);
            return true;
        }
        return super.shouldOverrideUrlLoading(webView, url);
    }


    @Override
    public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
        LogUtils.i("=====msg_webview", " OtherWebViewClient  onPageStarted   url: " + webView.getUrl());
        super.onPageStarted(webView, url, bitmap);
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        LogUtils.i("=====msg_webview", "OtherWebViewClient  onPageFinished   url: " + webView.getUrl());
        super.onPageFinished(webView, url);

        webView.loadUrl("javascript:window.NMBridge.viewAppear && window.NMBridge.viewAppear()");
    }
}
