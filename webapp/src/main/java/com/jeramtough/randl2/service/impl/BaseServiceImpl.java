package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/1/30 14:41
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    private WebApplicationContext wc;

    public BaseServiceImpl(WebApplicationContext wc) {
        this.wc = wc;
    }

    public WebApplicationContext getWC() {
        return wc;
    }
}
