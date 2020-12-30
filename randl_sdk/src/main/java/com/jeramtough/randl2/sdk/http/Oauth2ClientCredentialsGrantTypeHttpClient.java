package com.jeramtough.randl2.sdk.http;

import com.jeramtough.randl2.sdk.model.http.ApiResponse;
import com.jeramtough.randl2.sdk.model.oauth.AuthorizationGrantType;
import com.jeramtough.randl2.sdk.model.oauth.Oauth2ClientConfig;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/25 22:34
 * by @author WeiBoWen
 * </pre>
 */
public class Oauth2ClientCredentialsGrantTypeHttpClient extends BaseOauth2HttpClient {

    public Oauth2ClientCredentialsGrantTypeHttpClient(
            Oauth2ClientConfig oauth2ClientConfig) {
        super(oauth2ClientConfig);
    }

    public ApiResponse ssoLoginByPassword(String credentials, String password) throws IOException {
        Map<String, Object> params = new HashMap<>(4);
        params.put("credentials", credentials);
        params.put("password", password);
        params.put("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doPost(getOauth2ClientConfig().getSsoLoginUrl(), requestBody);
    }

    public ApiResponse ssoLoginByVerificationCode(String phoneOrEmail,
                                                  String verificationCode) throws
            IOException {
        Map<String, Object> params = new HashMap<>(3);
        params.put("phoneOrEmail", phoneOrEmail);
        params.put("verificationCode", verificationCode);
        params.put("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doTokenPost(requestBody);
    }

    public String getAuthorizeUrl(String token, String state) {
        URLBuilder urlBuilder = getCommonRequestUrl(getOauth2ClientConfig().getUserAuthorizationUri());

        urlBuilder.appendParam("state", state);
        urlBuilder.appendParam("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        urlBuilder.appendParam("authorization", token);
        urlBuilder.appendParam("response_type", "code");

        return urlBuilder.toString();
    }

    public String goToAuthorizePage(String token, String state) throws
            IOException {
        URLBuilder urlBuilder = getCommonRequestUrl(getOauth2ClientConfig().getUserAuthorizationUri());

        urlBuilder.appendParam("state", state);
        urlBuilder.appendParam("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        urlBuilder.appendParam("authorization", token);
        urlBuilder.appendParam("response_type", "code");

        return doGet(urlBuilder);
    }

    public ApiResponse obtainTokenByAuthorizationCode(String authorizationCode) throws IOException {

        Map<String, Object> params = new HashMap<>(3);
        params.put("code", authorizationCode);
        params.put("redirectUris_url", getOauth2ClientConfig().getRedirectUrisUrl());
        params.put("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doTokenPost(requestBody);
    }

}
