package com.jeramtough.randl2.sdk.exception;

/**
 * <pre>
 * Created on 2021/8/21 下午4:22
 * by @author WeiBoWen
 * </pre>
 */
public class OptionException extends IllegalStateException {

    private int statusCode;
    private String message;

    public OptionException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
