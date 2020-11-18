package com.jeramtough.randl2.common.config.db;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * Created on 2020/2/9 0:20
 * by @author JeramTough
 * </pre>
 */
@Configuration
@MapperScan("com.jeramtough.randl2.common.mapper")
public class MyBatisConfig {


}
