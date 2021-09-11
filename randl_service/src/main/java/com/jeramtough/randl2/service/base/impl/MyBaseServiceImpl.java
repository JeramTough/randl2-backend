package com.jeramtough.randl2.service.base.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeramtough.jtweb.service.impl.JtBaseServiceImpl;
import com.jeramtough.randl2.service.base.MyBaseService;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/10/25 16:43
 * by @author WeiBoWen
 * </pre>
 */
public abstract class MyBaseServiceImpl<M extends BaseMapper<T>, T, D>
        extends JtBaseServiceImpl<M, T, D> implements MyBaseService<T, D> {


    public MyBaseServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

}
