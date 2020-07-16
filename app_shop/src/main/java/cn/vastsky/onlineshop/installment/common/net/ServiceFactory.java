package cn.vastsky.onlineshop.installment.common.net;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.vastsky.onlineshop.installment.common.config.UrlConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


/**
 *
 */
public class ServiceFactory {

    /**
     * 需要实现retrofit的接口  必须含有BASE_URL 注解
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static <S> S createService(Class<S> serviceClass) {
        String baseUrl = UrlConfig.BaseUrl;

        return build(baseUrl).build().create(serviceClass);
    }


    private static OkHttpClient client;

    private static OkHttpClient getClient() {
        if (client == null) {
            // 自定义拦截器 拦截log输出
            LogInterceptor loggingInterceptor = new LogInterceptor();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(8, TimeUnit.SECONDS);
            builder.writeTimeout(8, TimeUnit.SECONDS);
            builder.connectTimeout(5, TimeUnit.SECONDS);
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("authentication", "").build();
                    return chain.proceed(request);
                }
            });
            client = builder.build();
        }
        return client;
    }


    private static Retrofit.Builder build(String baseurl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(Base64GsonConvertFactory.create());
        builder.baseUrl(baseurl);
        builder.client(getClient());
        return builder;
    }
}
