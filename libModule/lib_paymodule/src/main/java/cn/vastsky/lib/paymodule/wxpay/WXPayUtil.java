package cn.vastsky.lib.paymodule.wxpay;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.vastsky.lib.utils.LogUtils;

/**
 * @author: kezy
 * @create_time 2019/11/18
 * @description:
 */
public class WXPayUtil {

    private IWXAPI iwxapi; //微信支付api

    //静态内部类单例模式
    public static WXPayUtil getInstance() {

        return WXPayUtil.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final WXPayUtil INSTANCE = new WXPayUtil();
    }

    private WXPayUtil() {
    }

    /**
     * 调起微信支付的方法,不需要在客户端签名
     **/
    public void pay(Context context, String appid, final PayReq payReq) {

        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, appid); //初始化微信api
            iwxapi.registerApp(appid); //注册appid  appid可以在开发平台获取
            LogUtils.d("------appid: " + appid);
        }

        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {
                LogUtils.d("-----chx" + "run:  appId:  " + payReq.appId + " ,nonceStr:  " + payReq.nonceStr + " ,sign: " + payReq.sign);
                boolean isOpen = iwxapi.sendReq(payReq);//发送调起微信的请求
                LogUtils.d(" -------- isOpen :  " + isOpen);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void openWX(Context context, String appid) {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, appid); //初始化微信api
            iwxapi.registerApp(appid); //注册appid  appid可以在开发平台获取
            LogUtils.d("------appid: " + appid);
        }

        iwxapi.openWXApp();
    }
}
