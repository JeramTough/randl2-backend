package com.jeramtough.randl2.adminapp.config.web;

import com.jeramtough.jtweb.annotation.EnableJtweb;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * Created on 2020/1/25 22:17
 * by @author JeramTough
 * </pre>
 */
@ComponentScan(basePackages = "com.jeramtough.randl2.common")
@Configuration
@EnableJtweb
public class WebConfig {

}
