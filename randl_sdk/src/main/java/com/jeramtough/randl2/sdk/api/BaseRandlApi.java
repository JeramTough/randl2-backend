package com.jeramtough.randl2.sdk.api;

import com.alibaba.fastjson2.JSON;
import com.jeramtough.randl2.sdk.model.httpresponse.ApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2021/8/17 下午11:41
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseRandlApi implements RandlApi {

    private String contextUrl;
    private String tokenHeader;

    public BaseRandlApi(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    public BaseRandlApi(String contextUrl, String tokenHeader) {
        this.contextUrl = contextUrl;
        this.tokenHeader = tokenHeader;
    }

    protected Map<String, String> getHeadersWithToken() {
        String token = getTokenHeader();
        if (token == null) {
            throw new IllegalStateException("token没有设置！");
        }
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", token);
        return headerMap;
    }

    protected ApiResponse toApiResponse(String jsonResponse) {
        try {
            ApiResponse apiResponse = JSON.parseObject(jsonResponse, ApiResponse.class);
            if (apiResponse.getMessage()==null){
                apiResponse.setMessage(jsonResponse);
            }
            return apiResponse;
        }
        catch (Exception e) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setIsSuccessful(false);
            apiResponse.setMessage(jsonResponse);
            return apiResponse;
        }
    }


    public String getContextUrl() {
        return contextUrl;
    }

    @Override
    public String getTokenHeader() {
        return tokenHeader;
    }

    @Override
    public void setContextUrl(String contextUrl) {
        this.contextUrl = contextUrl;
    }

    @Override
    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

}
