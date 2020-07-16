package cn.vastsky.onlineshop.installment.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.net.URLEncoder;

import cn.vastsky.lib.base.utils.JsonUtils;
import cn.vastsky.lib.base.utils.LogUtils;
import cn.vastsky.lib.base.utils.ToastUtil;
import cn.vastsky.libs.common.router.PageRouter;
import cn.vastsky.libs.common.view.dialog.CommonDialog;
import cn.vastsky.onlineshop.installment.base.web.VsWebviewActivity;
import cn.vastsky.onlineshop.installment.common.VsConstants;
import cn.vastsky.onlineshop.installment.common.net.ICallBack;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanCertRequest;
import cn.vastsky.onlineshop.installment.model.bean.request.LoanDetailRequest;
import cn.vastsky.onlineshop.installment.model.bean.response.CertifyTpyeResponse;
import cn.vastsky.onlineshop.installment.service.LoanModel;
import cn.vastsky.onlineshop.installment.view.activity.LoanIdCardCertActivity;
import cn.vastsky.onlineshop.installment.view.activity.PayResultPageActivity;

/**
 * @author: kezy
 * @create_time 2019/11/24
 * @description:
 */
public class CertRouterUtils {


    /**
     * // 1-基本信息(h5)
     * 2-身份证认证
     * 3-补充信息(h5)
     * 4-认证完结
     * 5-申请失败（弹窗）
     * //其他类型且webCertify为true，统一按h5认证处理
     *
     * @param activity
     * @param request
     */
    public static void getUserCertifyTypeInfo(Activity activity, LoanDetailRequest request) {

        LoanModel.getUserCertifyInfo(request, new ICallBack<CertifyTpyeResponse>() {
            @Override
            public void onSuccess(CertifyTpyeResponse response) {
                if (response != null) {
                    if (response.webCertify) {
                        goH5Cert(activity, response);
                        if (activity instanceof LoanIdCardCertActivity || activity instanceof VsWebviewActivity) {
                            activity.finish();
                        }
                    } else if (response.certifyType == 2) {
                        goIdCardCert(activity, request.instProductId, request.orderSn);
                        if (activity instanceof LoanIdCardCertActivity || activity instanceof VsWebviewActivity) {
                            activity.finish();
                        }
                    } else if (response.certifyType == 4) {
                        // 认证完结
                        Intent intent = new Intent(activity, PayResultPageActivity.class);
                        intent.putExtra("orderSn", response.orderSn);
                        activity.startActivity(intent);
                        if (activity instanceof LoanIdCardCertActivity || activity instanceof VsWebviewActivity) {
                            activity.finish();
                        }
                    } else if (response.certifyType == 5) {
                        // 申请失败(弹窗)
                        CommonDialog dialog = new CommonDialog(activity);
                        dialog.setMessage("抱歉, 暂不满足申请资质, 您可以尝试其他分期产品或者选择现金支付～")
                                .setSingleBtnText("我知道了")
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(String err_msg) {
                ToastUtil.show(err_msg);
            }
        });
    }

    public static void goH5Cert(Activity activity, CertifyTpyeResponse response) {
        String callBackUrl = VsConstants.RouterCommon.SCHEME_NAGELAN + VsConstants.RouterCommon.PAGE_LOCAL_BASE + VsConstants.RouterCommon.PATH_THIRD_CERT_CALLBACK
                + getParmas(JsonUtils.obj2JsonStr(response));
        LogUtils.d("---------------------- callback Url: " + callBackUrl);
        LoanCertRequest request = new LoanCertRequest(response.certifyType, response.instProductId, response.orderSn, callBackUrl);
        getCertDestinationInfo(activity, request);
    }

    private static String getParmas(String value) {
        return "?parameters=" + URLEncoder.encode(value);
    }

    public static void getCertDestinationInfo(Context context, LoanCertRequest request) {

        LoanModel.getCertDestinationInfo(request, new ICallBack<String>() {
            @Override
            public void onSuccess(String url) {
                PageRouter.resolve(context, url);
            }

            @Override
            public void onFailure(String err_msg) {
                ToastUtil.show(err_msg);
            }
        });
    }

    public static void goIdCardCert(Activity activity, int instProductId, String orderSn) {
        Intent intent = new Intent(activity, LoanIdCardCertActivity.class);
        intent.putExtra("instProductId", instProductId);
        intent.putExtra("orderSn", orderSn);
        activity.startActivity(intent);
    }
}
