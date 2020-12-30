/**
 * Copyright 2020 bejson.com
 */
package com.jeramtough.randl2.sdk.model.http;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Auto-generated: 2020-11-24 8:38:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ApiResponse {

    private boolean isSuccessful;
    private JSONObject responseBody;
    private int statusCode;
    private long timestamp;

    public void setIsSuccessful(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setResponseBody(JSONObject responseBody) {
        this.responseBody = responseBody;
    }

    public JSONObject getResponseBody() {
        return responseBody;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

}