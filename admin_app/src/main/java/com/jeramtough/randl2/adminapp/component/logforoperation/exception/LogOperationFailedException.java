package com.jeramtough.randl2.adminapp.component.logforoperation.exception;

/**
 * <pre>
 * Created on 2020-08-06 19:52
 * by @author JeramTough
 * </pre>
 */
public class LogOperationFailedException extends RuntimeException {

    public LogOperationFailedException(String message) {
        super(message);
    }
}
