package com.jeramtough.randl2.sdk.client;

import com.jeramtough.randl2.sdk.model.httpresponse.ApiResponse;
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
public class Oauth2AuthorizationCodeGrantTypeHttpClient extends BaseOauth2HttpClient {

    public Oauth2AuthorizationCodeGrantTypeHttpClient(
            Oauth2ClientConfig oauth2ClientConfig) {
        super(oauth2ClientConfig);
    }

    public ApiResponse ssoLoginByPassword(String credentials, String password) throws IOException {
        Map<String, Object> params = new HashMap<>(4);
        params.put("credentials", credentials);
        params.put("password", password);
        params.put("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doPost(getOauth2ClientConfig().getSsoLoginUri(), requestBody);
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

    public String getAuthorizeUrl(String ssoToken, String state) {
        URLBuilder urlBuilder = getCommonRequestUrl(getOauth2ClientConfig().getUserAuthorizationUri());

        if ((state != null) && state.trim().length() > 0) {
            urlBuilder.appendParam("state", state);
        }

        if ((ssoToken != null) && ssoToken.trim().length() > 0) {
            urlBuilder.appendParam("authorization", ssoToken);
        }

        urlBuilder.appendParam("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());


        urlBuilder.appendParam("response_type", "code");

        return urlBuilder.toString();
    }

    public String getAuthorizeHtmlPage(String ssoToken, String state) throws
            IOException {
        URLBuilder urlBuilder = getCommonRequestUrl(getOauth2ClientConfig().getUserAuthorizationUri());

        urlBuilder.appendParam("state", state);
        urlBuilder.appendParam("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        urlBuilder.appendParam("authorization", ssoToken);
        urlBuilder.appendParam("response_type", "code");

        return doGet(urlBuilder);
    }

    public ApiResponse obtainTokenByAuthorizationCode(String authorizationCode) throws IOException {

        Map<String, Object> params = new HashMap<>(3);
        params.put("code", authorizationCode);
        params.put("redirectUris_url", getOauth2ClientConfig().getRedirectUri());
        params.put("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doTokenPost(requestBody);
    }

    public ApiResponse obtainTokenByRefreshToken(String refreshToken) throws IOException {
        Map<String, Object> params = new HashMap<>(3);
        params.put("refresh_token", refreshToken);
        params.put("grant_type", AuthorizationGrantType.REFRESH_TOKEN.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doTokenPost(requestBody);
    }

    public String getSsoLoginUrl(String state) {
        URLBuilder urlBuilder = getCommonRequestUrl(getOauth2ClientConfig().getSsoLoginUri());

        urlBuilder.appendParam("state", state);
        urlBuilder.appendParam("grant_type", AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        urlBuilder.appendParam("response_type", "code");
        urlBuilder.appendParam("redirect_uri", getOauth2ClientConfig().getRedirectUri());

        return urlBuilder.toString();
    }
}
