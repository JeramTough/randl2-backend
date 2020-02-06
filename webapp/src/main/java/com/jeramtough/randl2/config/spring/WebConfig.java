package com.jeramtough.randl2.config.spring;

import org.apache.catalina.filters.CorsFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        //匹配所有接口
                        .addMapping("/**")
                        //匹配到的接口都允许跨域
                        .allowedOrigins("*")
                        .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                        .allowCredentials(true);
            }
        };
    }


}
