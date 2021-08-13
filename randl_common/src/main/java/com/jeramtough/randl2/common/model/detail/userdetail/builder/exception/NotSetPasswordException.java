package com.jeramtough.randl2.common.model.detail.userdetail.builder.exception;

/**
 * <pre>
 * Created on 2021/1/17 2:44
 * by @author WeiBoWen
 * </pre>
 */
public class NotSetPasswordException extends Exception{

    private static final long serialVersionUID = -1400220913707750187L;

    public NotSetPasswordException() {
        super("没有设置密码");
    }
}
