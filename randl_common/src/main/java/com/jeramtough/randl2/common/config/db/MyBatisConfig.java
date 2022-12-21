package com.jeramtough.randl2.common.config.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
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


    /**
     * 返回配置的mybatis插件集合
     *
     * @return mybatis plus 插件集合
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加一个分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new InnerInterceptor() {
        });
        return interceptor;
    }

}
