package cn.vastsky.libs.common.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import cn.vastsky.lib.router.PageRouter;
import cn.vastsky.libs.common.base.web.VsWebviewActivity;


/**
 * Created by chenchen19 on 2017/5/21.
 * web handle
 */
public class WebHandler implements PageRouter.ProtocolHandler {

    private static final String HTTP_SCHEME = "http";

    private static final String HTTPS_SCHEME = "https";

    private static final String FILE_SCHEME = "file";

    @Override
    public String getName() {
        return "web_handle";
    }

    @Override
    public boolean handle(Context context, Uri uri, Object data) {
        if (uri == null || TextUtils.isEmpty(uri.toString())) {
            return false;
        }
        if (TextUtils.equals(HTTP_SCHEME, uri.getScheme()) || TextUtils.equals(HTTPS_SCHEME, uri.getScheme()) || TextUtils.equals(FILE_SCHEME, uri.getScheme())) {

            Intent intent = new Intent(context, VsWebviewActivity.class);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.putExtra("h5_url", uri.toString());
            if (data instanceof Bundle) {
                intent.putExtras((Bundle) data);
            }
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}