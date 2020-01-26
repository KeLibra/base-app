package cn.pingan.claim.app.base.common.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import cn.kezy.libs.common.utils.LogUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class Base64GsonConvertFactory extends Converter.Factory {
    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static Base64GsonConvertFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static Base64GsonConvertFactory create(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        return new Base64GsonConvertFactory(gson);
    }

    private final Gson gson;

    private Base64GsonConvertFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonBase64ResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    final static class GsonBase64ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonBase64ResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
//			byte[] bytes = Base64.decode(value.bytes(), Base64.DEFAULT);
//			value.close();
            String json = new String(value.bytes());
            LogUtils.w("-------msg: ResponseBody value : " + json);
//            LogUtils.d("-------msg: adapter.fromJson(json) : " + adapter.fromJson(json));

//            if (getJson(adapter, json)) {
//                return (T) json;
//            } else {
//                return adapter.fromJson(json);
//            }
//            return (T) json;
            try {
                if (adapter.fromJson(json) instanceof String) {
                    return (T) json;
                } else {
                    return adapter.fromJson(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return (T) json;
            }
        }
    }


    final static class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;

        GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
//            BaseRequest<T> request = new BaseRequest<>(value);
//            String requestStr = gson.toJson(request);
//
//            LogUtils.d("====gson_request", requestStr);
            LogUtils.d("====gson_request", gson.toJson(value));

            return RequestBody.create(MEDIA_TYPE, gson.toJson(value));
        }
    }
}
