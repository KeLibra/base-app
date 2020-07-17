package cn.vastsky.lib.paymodule.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import cn.vastsky.lib.paymodule.alipay.bean.PayResult;

/**
 * @author: kezy
 * @create_time 2019/11/18
 * @description:
 */
public class AlipayUtil {


    private static final int SDK_PAY_FLAG = 1;

    //静态内部类单例模式
    public static AlipayUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final AlipayUtil INSTANCE = new AlipayUtil();
    }

    private AlipayUtil() {
    }

    public void pay(final Activity activity, final String orderInfo, final boolean isShowPayLoading, final AlipayCallBack callBack) {
        @SuppressLint("HandlerLeak") final Handler mHandler = new Handler() {
            @SuppressWarnings("unused")
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                        if (callBack != null) {
                            callBack.callBack(payResult);
                        }
                    }
                }
            }
        };

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, isShowPayLoading);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
