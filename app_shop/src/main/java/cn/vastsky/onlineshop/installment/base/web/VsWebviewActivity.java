package cn.vastsky.onlineshop.installment.base.web;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.libs.common.utils.AppInfoUtils;
import cn.vastsky.onlineshop.installment.R;
import cn.vastsky.onlineshop.installment.base.view.SimpleBaseActivity;
import cn.vastsky.onlineshop.installment.model.bean.event.MessageEvent;


public class VsWebviewActivity extends SimpleBaseActivity {

    public String getH5_url() {
        return h5_url;
    }

    public void setH5_url(String h5_url) {
        this.h5_url = h5_url;
    }

    protected String h5_url;
    String params;

    protected WebView x5WebView;
    protected ProgressBar pbProgress;

    protected boolean isOtherLink = false; // 是否是外部链接， 是的话走特殊处理
    protected String hideNavigation = "0"; // 是否有导航栏  0 有， 1没有， 默认 0

    protected SwipeRefreshLayout srlLayout;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_vs_webview;
    }

    @Override
    protected void initView() {

        registerEventBus();
        AndroidBug5497Workaround.assistActivity(this);

        x5WebView = findViewById(R.id.layout_web);
        srlLayout = findViewById(R.id.srl_layout);
        pbProgress = findViewById(R.id.pb_progress);
        srlLayout.setEnabled(false);

        initWebView();

        if (isOtherLink || "0".equals(hideNavigation)) {
            try {
                findViewById(R.id.rl_web_layout).setFitsSystemWindows(true);
            } catch (Exception e) {
                LogUtils.e("====msg_statusbar_err", "  err: " + e.toString());
            }
        }
    }

    @Override
    protected void loadData() {
        if (h5_url != null) {
            x5WebView.loadUrl(h5_url);
        }
    }


    protected void getIntentData() {

        h5_url = getIntent().getStringExtra("h5_url");
//        h5_url = "http://mall.sit.mizar8.com/debug/main";
//        h5_url = "http://10.102.110.81:8080/debug/appBridge";

        params = getIntent().getStringExtra("params");

        hideNavigation = getIntent().getStringExtra("hideNavigation");

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            isOtherLink = bundle.getBoolean("isOtherLink", false);
        }
        LogUtils.d("===msg_h5_url", "get  isOtherLink: " + isOtherLink + " , h5_url: " + h5_url);
    }

    @Override
    protected boolean onTitleBack() {
        return true;
    }

    @Override
    protected boolean hasTitle() {
        return !"NoTitle".equals(params);
    }

    private void initWebView() {

        initWebSetting();

        x5WebView.addJavascriptInterface(new JavaInterface(this), "NMBridge");

        x5WebView.setWebChromeClient(getWebChromeClient());

        if (isOtherLink) {
            LogUtils.d("===msg_h5_url", "get  isOtherLink: -------- OtherWebViewClient ");
            x5WebView.setWebViewClient(new OtherWebViewClient(this));
        } else {
            LogUtils.d("===msg_h5_url", "get  isOtherLink:  ---------- X5WebChromeClient ");
            x5WebView.setWebViewClient(getWebViewCllient());
        }
    }

    private WebViewClient getWebViewCllient() {
        return new VsWebViewClient(this, loadStatusListener);
    }


    private VsWebViewClient.webLoadStatusListener loadStatusListener = new VsWebViewClient.webLoadStatusListener() {
        @Override
        public void onStatus(int status) {
            switch (status) {
                case VsWebViewClient.webLoadStatusListener.PAGE_START:
                    if (pbProgress != null) {
                        pbProgress.setVisibility(View.VISIBLE);
                        pbProgress.setProgress(0);
                    }
                    break;
                case VsWebViewClient.webLoadStatusListener.PAGE_FINISH:
                    if (pbProgress != null) {
                        pbProgress.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private VsWebChromeClient.onTitleChangedListener listener = new VsWebChromeClient.onTitleChangedListener() {
        @Override
        public void onTitleChanged(String title) {
            if (hasTitle() && mTitleView != null) {
                mTitleView.getTextView().setText(title);
            }
        }

        @Override
        public void onProgressChanged(int progress) {
            if (progress > 0 && progress < 100) {
                pbProgress.setVisibility(View.VISIBLE);
                pbProgress.setProgress(progress);
            } else {
                pbProgress.setVisibility(View.GONE);
            }
        }
    };

    private WebChromeClient getWebChromeClient() {
        return new VsWebChromeClient(listener);
    }

    private void initWebSetting() {

        WebSettings webSetting = x5WebView.getSettings();
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setDisplayZoomControls(false);

        String ua = webSetting.getUserAgentString();
        webSetting.setUserAgentString(ua + ";Nagelan/" + AppInfoUtils.getVersionName());

        LogUtils.d("------------ web ua: " + webSetting.getUserAgentString());
    }


    @Override
    protected void onBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        x5WebView.evaluateJavascript("javascript:!(window.canGoBack && !window.canGoBack(true))", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                LogUtils.d("----------------------- javascript:JSMethod() :  " + value);
                                if (TextUtils.isEmpty(value) || value.equals("true") || value.equals("null")) {
                                    //默认原生返回
                                    webGoBack();
                                }
                            }
                        });
                    } catch (Exception e) {
                        webGoBack();
                    }
                }
            });
        } else {
            webGoBack();
        }
    }

    public void webGoBack() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String lastUrl = "";

                if (x5WebView != null && x5WebView.getUrl() != null) {
                    lastUrl = x5WebView.getUrl();
                }
                LogUtils.d("------------- " + x5WebView.canGoBack() + " , last: " + lastUrl);
                if (x5WebView != null && x5WebView.canGoBack()) {
                    x5WebView.goBack();
                    if (x5WebView.getUrl().equals(lastUrl)) {
                        if (x5WebView.canGoBack()) {
                            x5WebView.goBack();
                        } else {
                            finish();
                        }
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        LogUtils.d("--------------------------- onResume()");
        if (x5WebView != null) {
            x5WebView.loadUrl("javascript:window.onAppear && window.onAppear()");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (x5WebView != null) {
            x5WebView.loadUrl("javascript:window.onDisappear && window.onDisappear()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (x5WebView != null) {
            x5WebView.removeAllViews();
            x5WebView.destroy();
            x5WebView = null;
        }
    }


    public void loadJsCallback(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (x5WebView != null) {
                    x5WebView.loadUrl(s);
                }
            }
        });
    }

    /**
     * 原生提供给 h5 的方法
     */

    public void goBack() {
        onBack();
        return;
    }

    public void setRightText(String rightText, View.OnClickListener listener) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setRightTitle(rightText, listener);
            }
        });
    }

    public void hideNavigation() {
        try {
            findViewById(R.id.rl_web_layout).setFitsSystemWindows(false);
        } catch (Exception e) {
            LogUtils.e("====msg_statusbar_err", "  err: " + e.toString());
        }
    }

    /**
     * 响应 eventbus 消息， 在主线程处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void responseEventBusMainThread(MessageEvent event) {
        if (MessageEvent.TYPE_LOGIN_CALLBACK.equals(event.eventType)) {
            String loginCallback = (String) event.message;
            if (!TextUtils.isEmpty(loginCallback)) {
                x5WebView.loadUrl("javascript:if(window." + loginCallback + "){window." + loginCallback + "('" + "" + "')};");
            }
        }
    }

    @Override
    protected boolean hasErrorPage() {
        return true;
    }

    @Override
    protected void onErrorRefresh() {
        if (x5WebView != null) {
            x5WebView.reload();
            hideEmptyPage();
        }
    }
}
