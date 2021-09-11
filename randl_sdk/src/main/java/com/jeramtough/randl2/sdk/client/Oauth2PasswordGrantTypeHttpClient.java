package com.jeramtough.randl2.sdk.client;

import com.alibaba.fastjson.JSON;
import com.jeramtough.randl2.sdk.exception.OptionException;
import com.jeramtough.randl2.sdk.model.httpresponse.ApiResponse;
import com.jeramtough.randl2.sdk.model.httpresponse.TokenBody;
import com.jeramtough.randl2.sdk.model.oauth.AuthorizationGrantType;
import com.jeramtough.randl2.sdk.model.oauth.Oauth2ClientConfig;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/24 0:08
 * by @author WeiBoWen
 * </pre>
 */
public class Oauth2PasswordGrantTypeHttpClient extends BaseOauth2HttpClient {

    public Oauth2PasswordGrantTypeHttpClient(Oauth2ClientConfig oauth2ClientConfig) {
        super(oauth2ClientConfig);
    }

    public TokenBody obtainTokenByPassword(String credentials, String password) throws
            IOException {

        Map<String, Object> params = new HashMap<>(3);
        params.put("username", credentials);
        params.put("password", password);
        params.put("grant_type", AuthorizationGrantType.PASSWORD.getValue());
        RequestBody requestBody = getCommonRequestBody(params);

        ApiResponse apiResponse = doTokenPost(requestBody);
        if (apiResponse.isSuccessful()) {
            TokenBody tokenBody = JSON.parseObject(apiResponse.getResponseBody().toString(),
                    TokenBody.class);
            return tokenBody;
        }
        else {
            throw new OptionException(apiResponse.getStatusCode(), apiResponse.getMessage());
        }
    }


    public ApiResponse obtainTokenByVerificationCode(String phoneOrEmail,
                                                     String verificationCode) throws
            IOException {
        Map<String, Object> params = new HashMap<>(3);
        params.put("phoneOrEmail", phoneOrEmail);
        params.put("verificationCode", verificationCode);
        params.put("grant_type", AuthorizationGrantType.PASSWORD.getValue());
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

    //************


}
