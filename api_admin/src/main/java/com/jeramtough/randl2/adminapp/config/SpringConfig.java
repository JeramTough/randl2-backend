package com.jeramtough.randl2.adminapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * <pre>
 * Created on 2020/11/13 0:14
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
@ComponentScan(
        value = {
                "com.jeramtough.randl2.common",
                "com.jeramtough.randl2.service",
                "com.jeramtough.randl2.component",
        },
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = {})})
public class SpringConfig {

}
