package com.jeramtough.randl2.adminapp.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 * Created on 2020/1/25 22:17
 * by @author JeramTough
 * </pre>
 */
@ComponentScan(basePackages = "com.jeramtough.randl2.common")
@Configuration
@Import({com.jeramtough.jtweb.springconfig.JtSpringConfig.class,
        com.jeramtough.jtweb.springconfig.JsonConfig.class,
      })
//@EnableJtweb
public class WebConfig {

}
