package com.jeramtough.ssoserver.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * Created on 2020/11/13 0:14
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
@ComponentScan({
        "com.jeramtough.randl2.common",
        "com.jeramtough.randl2.service",
        "com.jeramtough.randl2.component",
})
public class SpringConfig {
}
