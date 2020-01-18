package cn.kezy.libs.common.router;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;

import cn.kezy.libs.common.base.LibConstants;
import cn.kezy.libs.common.config.userinfo.LoginFacade;
import cn.kezy.libs.common.utils.LogUtils;


/**
 */

public class PageRouter {

    private static ArrayList<ProtocolHandler> handlers = new ArrayList<>();


    public interface ProtocolHandler {
        String getName();

        boolean handle(Context context, Uri uri, Object data);
    }

    public static void register(ProtocolHandler in) {
        if (in != null) {
            handlers.add(in);
        }
    }

    /**
     * 解析String类型的url，会经过h5+file+途牛协议+openUrl解析
     *
     * @param context context
     * @param url     String picUrl
     * @return 是否解析通过
     */
    public static boolean resolve(Context context, String url) {
        return !TextUtils.isEmpty(url) && resolve(context, Uri.parse(url));
    }

    public static boolean resolveNeedLogin(Context context, String url) {
        return resolveNeedLogin(context, url, null);
    }

    public static boolean resolveNeedLogin(Context context, String url, Object data) {
        if (!TextUtils.isEmpty(url)) {
            return resolve(context, Uri.parse(url).buildUpon().appendQueryParameter("isNeedLogin", "1").build(), data);
        } else {
            return false;
        }
    }

    /**
     * 解析String类型的url
     *
     * @param context context
     * @param url     String picUrl
     * @param data    其他数据
     * @return 是否解析通过
     */
    public static boolean resolve(Context context, String url, Object data) {
        return !TextUtils.isEmpty(url) && resolve(context, Uri.parse(url), data);
    }

    /**
     * 解析URI类型协议，会经过h5+file+途牛协议+openUrl解析
     *
     * @param context context
     * @param uri     URI
     * @return 是否解析通过
     */
    public static boolean resolve(Context context, Uri uri) {
        return resolve(context, uri, null);
    }

    /**
     * @param context context
     * @param uri     URI
     * @param data    额外参数
     * @return 是否解析 通过
     */
    public static boolean resolve(Context context, Uri uri, Object data) {
        if (uri == null || context == null) {
            return false;
        }
        if (isNeedLogin(context, uri, data)) {
            return true;
        }
        for (ProtocolHandler handler : handlers) {
            if (handler.handle(context, uri, data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context    context
     * @param uri        URI
     * @param data       额外参数
     * @param skipHandle 可以设置哪些handle不需要参与本次解析，目前仅用于用Webview跳转
     * @return 是否解析 通过
     */
    public static boolean resolve(Context context, Uri uri, Object data, ArrayList<String> skipHandle) {
        if (uri == null || context == null) {
            return false;
        }
        if (isNeedLogin(context, uri, data)) {
            return true;
        }
        for (ProtocolHandler handler : handlers) {
            if (skipHandle != null && skipHandle.contains(handler.getName())) {
                LogUtils.i("", "==  skip  handle[" + handler.getName() + "]");
                continue;
            }
            if (handler.handle(context, uri, data)) {
                return true;
            }
        }
        return false;
    }

    static {
//        register(new WebHandler());
    }

    public static boolean isNeedLogin(Context context, Uri uri, Object data) {
        LogUtils.d("====msg_log", "----------- isNeedLogin: " + uri);
        if (uri == null) {
            return false;
        } else {
            String isNeedLogin = "";

            try {
                isNeedLogin = uri.getQueryParameter("isNeedLogin");
            } catch (Exception var7) {
                LogUtils.e("PageRouter", var7.getMessage());
                return false;
            }

            if (!LoginFacade.isLogin() && "1".equals(isNeedLogin)) {
                Bundle bundle = new Bundle();
                bundle.putString(LibConstants.Key.KEY_LOGIN_SUCC_CALLBACK_URL, uri.toString());
                if (data instanceof Bundle) {
                    bundle.putBundle("bundle", (Bundle) data);
                }
                LoginFacade.goLoginView(context, bundle);
                LogUtils.d("====msg_log", "----------- do login view");
                return true;
            }

            return false;
        }
    }
}
