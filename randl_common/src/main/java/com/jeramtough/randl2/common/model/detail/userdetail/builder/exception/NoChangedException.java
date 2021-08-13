package com.jeramtough.randl2.common.model.detail.userdetail.builder.exception;

/**
 * <pre>
 * Created on 2021/1/17 2:19
 * by @author WeiBoWen
 * </pre>
 */
public class NoChangedException extends Exception{

    public NoChangedException() {
        super ("重置失败，账户未做任何修改操作");
    }
}
