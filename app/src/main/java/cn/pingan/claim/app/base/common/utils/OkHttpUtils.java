package cn.pingan.claim.app.base.common.utils;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.kezy.libs.common.utils.LogUtils;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author: Kezy
 * @create_time 2020/1/27 0027  14:34
 * @copyright kezy
 * @description:
 */
public class OkHttpUtils {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final byte[] LOCKER = new byte[0];
    private static OkHttpUtils instance;
    private OkHttpClient okHttpClient;

    private OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//10秒连接超时
                .writeTimeout(10, TimeUnit.SECONDS)//10m秒写入超时
                .readTimeout(10, TimeUnit.SECONDS)//10秒读取超时
                .build();
    }

    public static OkHttpUtils getInstance() {
//        if (instance == null) {
//            synchronized (LOCKER) {
//                if (instance == null) {
//                    instance = new OkHttpUtils();
//                }
//            }
//        }
        if (instance == null) {
            instance = new OkHttpUtils();
        }
        return instance;
    }

    public String doGet(String url) {
        if (isBlankUrl(url)) {
            return null;
        }
        Request request = getRequestForGet(url);
        return commonRequest(request);
    }

    public String doGet(String url, HashMap<String, String> params) {
        if (isBlankUrl(url)) {
            return null;
        }
        Request request = getRequestForGet(url, params);
        return commonRequest(request);
    }

    public String doPostJson(String url, String json) {
        if (isBlankUrl(url)) {
            return null;
        }
        Request request = getRequestForPostJson(url, json);
        return commonRequest(request);
    }

    public String doPostForm(String url, Map<String, String> params) {
        if (isBlankUrl(url)) {
            return null;
        }
        Request request = getRequestForPostForm(url, params);
        return commonRequest(request);
    }

    private Boolean isBlankUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            LogUtils.info("url is not blank");
            return true;
        } else {
            return false;
        }
    }

    private String commonRequest(Request request) {
        String re = "";
        try {
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response.isSuccessful()) {
                re = response.body().string();
                LogUtils.i("request [url]:" + request.url().toString() + " \n[request]: " + re);
            } else {
                LogUtils.i("request failure [url]:" + request.url().toString() + " \n[response]: " + response.message());
            }
        } catch (Exception e) {
            LogUtils.e("request execute failure", e);
        }
        return re;
    }

    private Request getRequestForPostJson(String url, String json) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }


    private Request getRequestForPostForm(String url, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addEncoded(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return request;
    }

    private Request getRequestForGet(String url, HashMap<String, String> params) {
        Request request = new Request.Builder()
                .url(getUrlStringForGet(url, params))
                .build();
        return request;
    }

    private Request getRequestForGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    private String getUrlStringForGet(String url, HashMap<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(url);
        urlBuilder.append("?");
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    urlBuilder.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (Exception e) {
                    urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
        }
        return urlBuilder.toString();
    }
}
