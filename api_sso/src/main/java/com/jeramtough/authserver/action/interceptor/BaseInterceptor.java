package com.jeramtough.authserver.action.interceptor;

import com.jeramtough.jtweb.component.apiresponse.ApiResponseFactory;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;

/**
 * <pre>
 * Created on 2020/11/17 15:24
 * by @author WeiBoWen
 * </pre>
 */
public class BaseInterceptor {

    public <T> CommonApiResponse<T> getSuccessfulApiResponse(T responseBody) {
        return ApiResponseFactory.getSuccessfulApiResponse(responseBody);
    }

    public CommonApiResponse<String> getFailedApiResponse(Exception e) {
        CommonApiResponse<String> failedApiResponse = ApiResponseFactory.getFailedResponse(e);
        return failedApiResponse;
    }

}
