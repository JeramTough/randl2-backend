package com.jeramtough.authserver.action.interceptor;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

import java.io.IOException;

/**
 * <pre>
 * Created on 2020/11/17 14:10
 * by @author WeiBoWen
 * </pre>
 */
public class AuthWebResponseExceptionTranslator extends BaseInterceptor implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        if (e instanceof ApiResponseException) {

        }
//        ResponseEntity<OAuth2Exception> entity=new ResponseEntity<OAuth2Exception>();
        return null;
    }

    //*****************************

    private ResponseEntity<CommonApiResponse<String>> handleOAuth2Exception(ApiResponseException e) throws
            IOException {

        int status = 500;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        CommonApiResponse<String> commonApiResponse = getFailedApiResponse(e);

        ResponseEntity<CommonApiResponse<String>> response = new ResponseEntity<>(commonApiResponse,
                headers, HttpStatus.valueOf(status));
        return response;

    }

}
