/**
 * Copyright 2020 bejson.com
 */
package com.jeramtough.randl2.sdk.model.httpresponse;


import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;

/**
 * Auto-generated: 2020-11-24 8:38:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ApiResponse implements Serializable {

    private boolean isSuccessful;
    private JSONObject responseBody;
    private int statusCode;
    private long timestamp;
    private String message;

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

    @Override
    public String toString() {
        return "ApiResponse{" +
                "isSuccessful=" + isSuccessful +
                ", responseBody=" + responseBody +
                ", statusCode=" + statusCode +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}