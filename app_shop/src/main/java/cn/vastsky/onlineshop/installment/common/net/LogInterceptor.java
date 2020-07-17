package cn.vastsky.onlineshop.installment.common.net;

import android.util.Base64;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import cn.vastsky.lib.utils.LogUtils;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author: zhongyangke
 * @create_time 2019/5/14
 * @copyright blackfish
 * @description: 自定义 retrofit log 拦截器
 */
public class LogInterceptor implements Interceptor {

    private final static String LOG_TAG = "====msg_okhttp_";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        String method = request.method();
        String url = request.url().toString();

        long t1 = System.nanoTime();//请求发起的时间

        RequestBody requestBody = request.body();
        StringBuilder sb = new StringBuilder("[Request] Body [");

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
                if (isPlaintext(buffer)) {
                    if (charset != null) {
                        sb.append(buffer.readString(charset));
                    }
                    sb.append(" (Content-Type = ").append(contentType.toString()).append(",").append(requestBody.contentLength()).append("-byte body)");
                } else {
                    sb.append(" (Content-Type = ").append(contentType.toString()).append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
                }
            }
            sb.append("]");
        } else {
            sb.append("]");
        }

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();//收到响应的时间

        /*这里不能直接使用response.body().string()的方式输出日志
        因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        个新的response给应用层处理
        */
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        LogUtils.d(LOG_TAG + " \n\t" + String.format(Locale.getDefault(), "[Received] for [url = %s] in %.1fms", url, (t2 - t1) / 1e6d)
                + "\n\t" + String.format(Locale.getDefault(), "%s", sb.toString())
//                + "\n\t" + "[Response]" + String.format(" Json:  %s", deCodeBase64(responseBody.string())));
                + "\n\t" + "[Response]" + String.format(" Json:  %s", responseBody.string()));
        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }

    //  base 64解密
    public String deCodeBase64(String strBase64) {
        String deCode64Str = "";
        try {
            deCode64Str = new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));
        } catch (Exception e) {
            deCode64Str = e.toString();
        }
        return deCode64Str;
    }
}
