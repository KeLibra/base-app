package cn.vastsky.onlineshop.installment.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.Stack;

import cn.vastsky.lib.base.utils.JsonUtils;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.manager.AppActivityManager;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.onlineshop.installment.base.web.VsWebviewActivity;
import cn.vastsky.onlineshop.installment.common.VsConstants;
import cn.vastsky.onlineshop.installment.common.utils.UrlEncoderUtils;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanCertRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.view.activity.LoanDetailActivity;
import cn.vastsky.onlineshop.installment.view.activity.PaymentPageActivity;

import static cn.vastsky.onlineshop.installment.common.VsConstants.RouterCommon.PATH_CLOSE_WEB;
import static cn.vastsky.onlineshop.installment.common.VsConstants.RouterCommon.PATH_LOAN_DETAIL;
import static cn.vastsky.onlineshop.installment.common.VsConstants.RouterCommon.PATH_PAY_MENT;
import static cn.vastsky.onlineshop.installment.common.VsConstants.RouterCommon.PATH_THIRD_CERT_CALLBACK;
import static cn.vastsky.onlineshop.installment.common.VsConstants.RouterCommon.PATH_WEB;
import static cn.vastsky.onlineshop.installment.common.VsConstants.RouterCommon.SCHEME_NAGELAN;

/**
 * @author: kezy
 * @create_time 2019/11/20
 * @description:
 */
public class PagerHandler implements PageRouter.ProtocolHandler {


//    const val SCHEME = "bj"


    @Override
    public String getName() {
        return "PagerHandler";
    }

    @Override
    public boolean handle(Context context, Uri uri, Object data) {
        LogUtils.d("---------------- : " + uri);

        if (context == null || uri == null || TextUtils.isEmpty(uri.toString())) {
            LogUtils.e("-------context == null--------- : " + (context == null) + "  ----uri == null-----  " + (uri == null) + " -- text empty --  " + (TextUtils.isEmpty(uri.toString())));
            return false;
        }

        LogUtils.d("-------scheme --------- : " + uri.getScheme());

        if (VsConstants.RouterCommon.SCHEME_HTTPS.equals(uri.getScheme()) || SCHEME_NAGELAN.equals(uri.getScheme())) {
            Intent intent = new Intent();
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            LogUtils.d("====handle", "---- path: " + uri.getPath());

            switch (uri.getPath()) {
                case PATH_PAY_MENT:

                    return goPaymentPage(context, intent, uri);

                case PATH_LOAN_DETAIL:

                    return goLoanDetailPage(context, intent, uri);

                case PATH_CLOSE_WEB:
                    ((Activity) context).finish();
                    return true;

                case PATH_THIRD_CERT_CALLBACK:

                    try {
                        String callbackParams = uri.getQueryParameter("parameters");
                        LogUtils.d("----------- ex  callbackParams " + callbackParams);

                        if (UrlEncoderUtils.hasUrlEncoded(callbackParams)) {
                            callbackParams = URLDecoder.decode(callbackParams);
                            LogUtils.d("----------- last  callbackParams " + callbackParams);
                        }
                        LoanCertRequest certRequest = JsonUtils.jsonStr2JsonObj(callbackParams, LoanCertRequest.class);
                        LoanDetailRequest request = new LoanDetailRequest(certRequest.instProductId, certRequest.orderSn);
                        LogUtils.d("---------------- request: " + JsonUtils.obj2JsonStr(request));
                        CertRouterUtils.getUserCertifyTypeInfo((Activity) context, request);

                        return true;
                    } catch (Exception e) {
                        LogUtils.e("------------------ msg err: " + e.toString());
                        e.printStackTrace();
                        return false;
                    }
                case PATH_WEB:
                    return goWebviewPage(context, uri);

                default:
                    return false;
            }
        }

        return false;
    }

    /**
     * 跳转到 webview 页面
     *
     * @param context
     * @param uri
     */
    private boolean goWebviewPage(Context context, Uri uri) {

        try {
            String webParams = uri.getQueryParameter("parameters");
            String jumpUrl = uri.getQueryParameter("url");
            String notNewPage = uri.getQueryParameter("notNewPage");
            String hideNavigation = uri.getQueryParameter("hideNavigation");
            String pop = uri.getQueryParameter("pop");

            LogUtils.d("==== parameters" + "---- parameters: " + webParams);
            LogUtils.d("==== jumpUrl" + "---- jumpUrl: " + jumpUrl);
            LogUtils.d("==== notNewPage" + "---- notNewPage: " + notNewPage);
            LogUtils.d("==== hideNavigation" + "---- hideNavigation: " + hideNavigation);
            LogUtils.d("==== pop" + "---- pop: " + pop);

            LogUtils.d("----------- ex  webParams " + webParams);
            if (UrlEncoderUtils.hasUrlEncoded(webParams)) {
                webParams = URLDecoder.decode(webParams);
                LogUtils.d("----------- last  payParams " + webParams);
            }

            LogUtils.d("----------- ex  jumpUrl " + jumpUrl);
            if (UrlEncoderUtils.hasUrlEncoded(jumpUrl)) {
                jumpUrl = URLDecoder.decode(jumpUrl);
                LogUtils.d("----------- last  jumpUrl " + jumpUrl);
            }

            LogUtils.d("----------- ex  notNewPage " + notNewPage);
            if (UrlEncoderUtils.hasUrlEncoded(notNewPage)) {
                notNewPage = URLDecoder.decode(notNewPage);
                LogUtils.d("----------- last  notNewPage " + notNewPage);
            }

            LogUtils.d("----------- ex  hideNavigation " + hideNavigation);
            if (UrlEncoderUtils.hasUrlEncoded(hideNavigation)) {
                hideNavigation = URLDecoder.decode(hideNavigation);
                LogUtils.d("----------- last  hideNavigation " + hideNavigation);
            }

            Intent intent = new Intent(context, VsWebviewActivity.class);

            intent.putExtra("parameters", webParams);
            intent.putExtra("h5_url", jumpUrl);
            intent.putExtra("notNewPage", notNewPage);
            intent.putExtra("hideNavigation", hideNavigation);

            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            if ("1".equals(pop)) {

                Stack<VsWebviewActivity> activityStack = AppActivityManager.getInstance().getActivityStack(VsWebviewActivity.class);
                LogUtils.e("-------------------------------- activity size: " + activityStack.size());
                for (VsWebviewActivity a : activityStack) {
                    LogUtils.d("-------------------------------- activity: " + a);
                    LogUtils.d("-------------------------------- activity a.getH5_url(): " + a.getH5_url());
                    if (jumpUrl.equals(a.getH5_url())) {
                        LogUtils.d("----- activity jumpUrl: " + jumpUrl);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LogUtils.d("---------------------------- 清掉  栈顶 activity");
                        AppActivityManager.getInstance().killActivity(a);
                    }
                }
                context.startActivity(intent);
            } else {
                context.startActivity(intent);
            }

            return true;
        } catch (Exception e) {
            LogUtils.e("--------------- err: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 跳转到 收银台页面
     *
     * @param context
     * @param intent
     * @param uri
     * @return
     */
    private boolean goPaymentPage(Context context, Intent intent, Uri uri) {

        String payParams = uri.getQueryParameter("parameters");
        LogUtils.d("----------- ex  payParams " + payParams);

        if (UrlEncoderUtils.hasUrlEncoded(payParams)) {
            payParams = URLDecoder.decode(payParams);
            LogUtils.d("----------- last  payParams " + payParams);
        }

        try {
            JSONObject payObject = new JSONObject(payParams);
            String orderSn = payObject.optString("orderSn");

            if (TextUtils.isEmpty(orderSn)) {
                return false;
            }

            intent.setClass(context, PaymentPageActivity.class);
            intent.putExtra("order_sn", orderSn);
            context.startActivity(intent);
            return true;

        } catch (JSONException e) {
            LogUtils.e("---------------- err: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 跳转到 信贷产品详情页面
     *
     * @param context
     * @param intent
     * @param uri
     * @return
     */
    private boolean goLoanDetailPage(Context context, Intent intent, Uri uri) {

        String payParams = uri.getQueryParameter("parameters");
        LogUtils.d("----------- ex  payParams " + payParams);

        if (UrlEncoderUtils.hasUrlEncoded(payParams)) {
            payParams = URLDecoder.decode(payParams);
            LogUtils.d("----------- last  payParams " + payParams);
        }

        try {
            JSONObject payObject = new JSONObject(payParams);
            int type = payObject.optInt("type");
            int instProductId = payObject.optInt("instProductId", -1);
            String amount = payObject.optString("amount");

            if (instProductId == -1 || TextUtils.isEmpty(amount)) {
                ToastUtil.show("数据异常，请刷新后再试");
                return false;
            }
            intent.setClass(context, LoanDetailActivity.class);
            intent.putExtra("jump_type", type);
            intent.putExtra("pay_instProductId", instProductId);
            intent.putExtra("pay_amount", amount);
            context.startActivity(intent);
            return true;

        } catch (JSONException e) {
            LogUtils.e("---------------- err: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }
}
