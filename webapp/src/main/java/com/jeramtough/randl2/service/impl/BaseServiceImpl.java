package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/1/30 14:41
 * by @author JeramTough
 * </pre>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    private WebApplicationContext wc;
    private MapperFacade mapperFacade;

    public BaseServiceImpl(WebApplicationContext wc,
                           MapperFacade mapperFacade) {
        this.wc = wc;
        this.mapperFacade = mapperFacade;
    }

    public WebApplicationContext getWC() {
        return wc;
    }

    public MapperFacade getMapperFacade() {
        return mapperFacade;
    }
}
