package com.jeramtough.authserver.component.userdetail.builder.exception;

/**
 * <pre>
 * Created on 2021/1/17 1:53
 * by @author WeiBoWen
 * </pre>
 */
public class AccountFormatException extends Exception{

    private static final long serialVersionUID = -4871892637717095527L;

    private String field;
    private String message;

    public AccountFormatException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
