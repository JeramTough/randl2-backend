package com.jeramtough.randl2.adminapp.action.runner;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.component.apiinfo.core.ApiInfoRecorder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2022/12/22 上午9:40
 * by @author WeiBoWen
 * </pre>
 */
@Component
@Order(value = 1)
public class BootAfterRunner implements ApplicationRunner {



    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApiInfoRecorder apiInfoRecorder=ApiInfoRecorder.getInstance();
        L.arrive();
    }
}
