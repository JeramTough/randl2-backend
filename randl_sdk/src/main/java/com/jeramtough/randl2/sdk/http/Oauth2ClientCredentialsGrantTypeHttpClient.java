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


    /**
     * 覆盖配置的clientId和clientSecret
     */
    public ApiResponse ssoLoginByClientSecret(String clientId,
                                              String clientSecret) throws
            IOException {
        Map<String, Object> params = new HashMap<>(2);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grantType", AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doTokenPost(requestBody);
    }

    /**
     * 使用配置的clientId和clientSecret
     */
    public ApiResponse ssoLoginByClientSecret() throws
            IOException {
        Map<String, Object> params = new HashMap<>(1);
        params.put("grantType", AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        return doTokenPost(requestBody);
    }


}
