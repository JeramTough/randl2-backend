package com.jeramtough.randl2.sdk.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeramtough.randl2.sdk.model.httpresponse.ApiResponse;
import com.jeramtough.randl2.sdk.model.oauth.Oauth2ClientConfig;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * Created on 2020/11/24 0:29
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseOauth2HttpClient {

    protected static final OkHttpClient okHttpClient;

    private Oauth2ClientConfig oauth2ClientConfig;

    static {
        okHttpClient = new OkHttpClient();
    }

    public BaseOauth2HttpClient(Oauth2ClientConfig oauth2ClientConfig) {
        this.oauth2ClientConfig = oauth2ClientConfig;

    }

    protected RequestBody getCommonRequestBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("client_id", oauth2ClientConfig.getClientId())
                .addFormDataPart("client_secret", oauth2ClientConfig.getClientSecret());
        params.forEach((key, value) -> {
            builder.addFormDataPart(key, value.toString());
        });

        RequestBody requestBody = builder.build();
        return requestBody;
    }

    protected URLBuilder getCommonRequestUrl(String url) {
        URLBuilder urlBuilder = new URLBuilder();
        urlBuilder
                .url(url)
                .appendParam("client_id", oauth2ClientConfig.getClientId())
                .appendParam("client_secret", oauth2ClientConfig.getClientSecret());
        return urlBuilder;

    }

    protected String doGet(URLBuilder urlBuilder) throws IOException {
        Request request = new Request.Builder()
                .url(urlBuilder.toString())
                .get()
                .build();

        Response response = okHttpClient.newCall(request).execute();

        if (response.body() == null) {
            return "404";
        }

        return Objects.requireNonNull(response.body()).string();
    }

    public Oauth2ClientConfig getOauth2ClientConfig() {
        return oauth2ClientConfig;
    }


    public ApiResponse doPost(String url, RequestBody requestBody) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        ApiResponse apiResponse = new ApiResponse();
        if (response.body() == null) {
            apiResponse.setIsSuccessful(false);
            apiResponse.setTimestamp(System.currentTimeMillis());
            apiResponse.setStatusCode(500);
            return apiResponse;
        }
        else {
            String jsonStr = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject.get("responseBody") != null && jsonObject.get(
                    "responseBody").getClass() == String.class) {
                jsonObject.put("responseBody", null);
                jsonStr = jsonObject.toJSONString();
            }
            apiResponse = JSON.parseObject(jsonStr, ApiResponse.class);
            //测底转为Map
            return apiResponse;
        }
    }

    public ApiResponse doTokenPost(RequestBody requestBody) throws IOException {
        return doPost(getOauth2ClientConfig().getAccessTokenUri(), requestBody);
    }


}
