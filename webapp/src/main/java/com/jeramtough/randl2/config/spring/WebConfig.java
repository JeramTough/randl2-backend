package com.jeramtough.randl2.config.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * Created on 2020/1/25 22:17
 * by @author JeramTough
 * </pre>
 */
@Configuration
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.jeramtough.randl2.dao.mapper")
@ComponentScan(basePackages = "com.jeramtough.jtweb.springconfig")
public class WebConfig {
}
