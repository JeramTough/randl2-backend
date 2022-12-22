package com.jeramtough.randl2.adminapp.action.runner;

import com.jeramtough.randl2.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final SystemService systemService;

    @Autowired
    public BootAfterRunner(SystemService systemService) {
        this.systemService = systemService;
    }

    @Override
    public void run(ApplicationArguments args) {
        //登记系统所有api信息到数据库
        systemService.registerSystemApiInfo();
    }
}
