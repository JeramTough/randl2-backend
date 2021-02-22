package com.jeramtough.randl2.adminapp.action.aspect;

import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * Created on 2020/12/7 16:28
 * by @author WeiBoWen
 * </pre>
 */
public abstract class BaseLogAspect {
    protected final WebApplicationContext webApplicationContext;

    protected final ExecutorService executorService;


    protected BaseLogAspect(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;

        executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }


    public WebApplicationContext getWC() {
        return webApplicationContext;
    }
}
