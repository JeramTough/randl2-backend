package com.jeramtough.authserver.component.userdetail.builder.exception;

/**
 * <pre>
 * Created on 2021/1/17 2:02
 * by @author WeiBoWen
 * </pre>
 */
public class TransactionTimeoutExcaption extends Exception {

    private static final long serialVersionUID = 3428021212262301357L;


    public TransactionTimeoutExcaption() {
        super("事务Id已失效，或信息已失效，请重新开始流程");
    }
}
