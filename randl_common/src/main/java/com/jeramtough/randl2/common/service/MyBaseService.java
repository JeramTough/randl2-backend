package com.jeramtough.randl2.common.service;

import com.jeramtough.jtweb.service.BaseDtoService;

/**
 * <pre>
 * Created on 2020/10/25 16:47
 * by @author WeiBoWen
 * </pre>
 */
public interface MyBaseService<T, D> extends BaseDtoService<T, D> {

    String updateByParams(Object params);
}
