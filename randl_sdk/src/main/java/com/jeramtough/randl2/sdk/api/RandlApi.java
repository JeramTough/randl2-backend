package com.jeramtough.randl2.sdk.api;

import com.alibaba.fastjson.JSONObject;
import com.jeramtough.randl2.sdk.model.httpresponse.ApiResponse;

import java.util.Map;

/**
 * <pre>
 * Created on 2021/8/17 下午11:13
 * by @author WeiBoWen
 * </pre>
 */
public interface RandlApi {

    void setContextUrl(String contextUrl);

    void setTokenHeader(String tokenHeader);

    String getTokenHeader();

    ApiResponse doGet(String urlPart, Map<String, Object> params) throws Exception;

    ApiResponse doPost(String urlPart, Map<String, Object> params) throws Exception;

    ApiResponse doPostWithJson(String urlPart, JSONObject jsonObject) throws Exception;

}
