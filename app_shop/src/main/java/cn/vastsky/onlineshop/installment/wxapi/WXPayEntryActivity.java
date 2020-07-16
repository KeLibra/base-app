//package cn.vastsky.onlineshop.installment.wxapi;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//
//import cn.vastsky.lib.base.utils.LogUtils;
//import cn.vastsky.onlineshop.installment.R;
//import cn.vastsky.onlineshop.installment.common.VsConstants;
//
//
//public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
//
//    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//
//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
//
//        api = WXAPIFactory.createWXAPI(this, VsConstants.APP_ID);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
//
//    @Override
//    public void onReq(BaseReq req) {
//        LogUtils.e("------onReq, error  = " + req.toString());
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        LogUtils.e("------onResp, errCode = " + resp.errCode + " ,errStr: " + resp.errStr + " ,transaction:  " + resp.transaction + " , openId: " + resp.openId);
//
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
////            AlertDialog.Builder builder = new AlertDialog.Builder(this);
////            builder.setTitle("提示");
////            builder.setMessage(resp.errCode + "\n" + resp.errStr);
////            builder.show();
//
////            String resultStr = "";
////            if (resp.errCode == 0) {
////                resultStr = "支付成功";
////            } else {
////                if (TextUtils.isEmpty(resp.errStr)) {
////                    resultStr = "支付失败";
////                } else {
////                    resultStr = resp.errStr;
////                }
////            }
////
////            CommonDialog commonDialog = new CommonDialog(this);
////            commonDialog.setTitle("提示")
////                    .setMessage(resultStr)
////                    .setSingleTextWithListener("确定", new CommonDialog.onSingleBtnListener() {
////                        @Override
////                        public void onClick() {
////                            WXPayEntryActivity.this.finish();
////                        }
////                    })
////                    .show();
//            finish();
//        }
//    }
//}