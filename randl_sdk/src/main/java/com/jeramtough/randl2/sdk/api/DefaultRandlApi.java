package com.jeramtough.randl2.sdk.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeramtough.randl2.sdk.http.HttpUtil;
import com.jeramtough.randl2.sdk.model.httpresponse.ApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2021/8/17 下午11:17
 * by @author WeiBoWen
 * </pre>
 */
public class DefaultRandlApi extends BaseRandlApi implements RandlApi {


    public DefaultRandlApi(String contextUrl) {
        super(contextUrl);
    }

    public DefaultRandlApi(String contextUrl, String token) {
        super(contextUrl, token);
    }

    @Override
    public ApiResponse doGet(String urlPart, Map<String, Object> params) throws Exception {
        if (params == null) {
            params = new HashMap<>(0);
        }
        String url = getContextUrl() + urlPart;
        String jsonResponse = HttpUtil.doGet(getHeadersWithToken(), url, params);
        return toApiResponse(jsonResponse);
    }

    @Override
    public ApiResponse doPost(String urlPart, Map<String, Object> params) throws Exception {
        if (params == null) {
            params = new HashMap<>(0);
        }
        String url = getContextUrl() + urlPart;

        String jsonResponse = HttpUtil.doPost(getHeadersWithToken(), url, params);
        return toApiResponse(jsonResponse);
    }

    @Override
    public ApiResponse doPostWithJson(String urlPart, JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }

        String url = getContextUrl() + urlPart;

        String jsonResponse = HttpUtil.doPostWithJson(getHeadersWithToken(), url,
                jsonObject.toJSONString());
        return toApiResponse(jsonResponse);
    }
}
