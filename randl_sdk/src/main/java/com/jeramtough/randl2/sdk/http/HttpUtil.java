package com.jeramtough.randl2.sdk.http;

import com.jeramtough.randl2.sdk.client.URLBuilder;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Created on 2021/8/17 下午11:29
 * by @author WeiBoWen
 * </pre>
 */
public class HttpUtil {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static volatile OkHttpClient okHttpClient = null;

    private static OkHttpClient getOkHttpClient() {
        synchronized (HttpUtil.class) {
            if (okHttpClient == null) {
                //这里是以毫秒为单位，1000 毫秒 = 1秒
                okHttpClient = new OkHttpClient().newBuilder()
                                                 .connectTimeout(5000,
                                                         TimeUnit.SECONDS)// 设置超时时间
                                                 .readTimeout(5000,
                                                         TimeUnit.SECONDS)// 设置读取超时时间
                                                 .writeTimeout(5000,
                                                         TimeUnit.SECONDS)// 设置写入超时时间
                                                 .build();
            }
        }
        return okHttpClient;
    }

    public static String doPost(Map<String, String> headers, String url,
                                Map<String, Object> params) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();

        RequestBody requestBody = null;
        if (params.size() > 0) {
            params.forEach((key, value) -> {
                builder.addEncoded(key, value.toString());
            });
            requestBody = builder.build();
        }

        Request.Builder builder1 = new Request.Builder();
        builder1.url(url);
        headers.forEach(builder1::header);

        if (requestBody != null) {
            builder1.post(requestBody);
        }

        Request request = builder1.build();
        Response response = getOkHttpClient().newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }

    public static String doPostWithJson(Map<String, String> headers, String url,
                                        String json) throws Exception {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        headers.entrySet()
               .parallelStream()
               .forEach(entry -> {
                   builder.addHeader(entry.getKey(), entry.getValue());
               });
        Request request = builder.build();
        Response response = getOkHttpClient().newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }

    /*public static String doPostWithJson(String url, String json) throws Exception {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = getOkHttpClient().newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }*/

    public static String doGet(Map<String, String> headers, String url,
                               Map<String, Object> params) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();

        URLBuilder urlBuilder=new URLBuilder();
        urlBuilder.url(url);
        params.forEach((key, value) -> {
            builder.addEncoded(key, value.toString());
        });

        url=urlBuilder.build();

        Request.Builder builder1 = new Request.Builder();
        builder1.url(url);
        headers.forEach(builder1::header);
        builder1=builder1.get();


        Request request = builder1.build();
        Response response = getOkHttpClient().newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }
}
