package cn.vastsky.onlineshop.installment.base.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.vastsky.lib.utils.LogUtils;
import cn.vastsky.lib.router.PageRouter;
import cn.vastsky.lib.utils.ViewUtils;
import cn.vastsky.lib.base.common.view.defaultview.DefaultErrorPageView;
import cn.vastsky.lib.base.common.view.impl.IErrorPageView;


/**
 * @author: kezy
 * @create_time 2019/10/30
 * @description:
 */
public class VsWebViewClient extends WebViewClient {

    private Context context;
    private webLoadStatusListener loadStatusListener;

    public VsWebViewClient(Context context, webLoadStatusListener loadStatusListener) {
        this.context = context;
        this.loadStatusListener = loadStatusListener;
    }

    public interface webLoadStatusListener {
        int PAGE_START = 0; // 开始加载
        int PAGE_FINISH = 1; // 完成加载

        void onStatus(int status);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtils.e("=================----------- err：" + description);
        showErrorWebPage(view, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {

        LogUtils.i("=====msg_webview", "shouldOverrideUrlLoading   url: " + webView.getUrl());
        if (url != null) {
            if (url.startsWith("http")) {

            } else if ((!url.startsWith("file:")) && url.endsWith(".pdf")) {
                showPdfWebView(webView, url);
            } else {
                LogUtils.d("=====msg_webview", "shouldOverrideUrlLoading 222   url: " + url);
                return PageRouter.resolve(context, url);
            }
        }
        return super.shouldOverrideUrlLoading(webView, url);
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
        LogUtils.i("=====msg_webview", "onPageStarted   url: " + webView.getUrl());

        loadStatusListener.onStatus(webLoadStatusListener.PAGE_START);
        if (webErrorPage != null) {
            webView.removeView(webErrorPage);
        }

        if (url != null) {
            if ((!url.startsWith("file:")) && url.endsWith(".pdf")) {
                showPdfWebView(webView, url);
            }
        }
        super.onPageStarted(webView, url, bitmap);
    }

    @Override
    public void onPageFinished(WebView webView, String url) {

        loadStatusListener.onStatus(webLoadStatusListener.PAGE_FINISH);

        LogUtils.i("=====msg_webview", "onPageFinished   url: " + webView.getUrl());
        super.onPageFinished(webView, url);

        webView.loadUrl("javascript:window.NMBridge.viewAppear && window.NMBridge.viewAppear()");
    }

    private void showPdfWebView(WebView view, String pdfUrl) {

        Log.e("====msg_web_pdf", " ------ 加载pdf文件  " + pdfUrl);


        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        /**
         * 简单来说，该项设置决定了JavaScript能否访问来自于任何源的文件标识的URL。
         * 比如我们把PDF.js放在本地assets里，然后通过一个URL跳转访问来自于任何URL的PDF，所以这里我们需要将其设置为true。
         * 而一般情况下，为了安全起见，是需要将其设置为false的。
         */
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        //支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);//不显示缩放按钮

        view.loadUrl("file:///android_asset/pdf.html?" + pdfUrl);

    }

    DefaultErrorPageView webErrorPage;

    private void showErrorWebPage(WebView view, String failingUrl) {
        if (view != null) {
            view.removeAllViews();
            int width = ViewUtils.getScreenWidth();
            int height = ViewUtils.getScreenHeight();

            if (webErrorPage == null) {
                webErrorPage = new DefaultErrorPageView(context);
            }

            webErrorPage.setOnRefreshListener(new IErrorPageView.OnRefreshListener() {
                @Override
                public void onRefreshBtnClick() {

                    LogUtils.e("---------- webErrorPage.setOnRefreshListener : " + failingUrl);
                    view.loadUrl(failingUrl);
                }
            });

            view.addView(webErrorPage, width, height);

        }
    }
}
