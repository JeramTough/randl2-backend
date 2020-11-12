package com.jeramtough.randl2.common.config.db;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * <pre>
 * Created on 2020/2/17 14:49
 * by @author JeramTough
 * </pre>
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {

   /* @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                "127.0.0.1"
                , 6379);
        configuration.setPassword("123456");
        return new LettuceConnectionFactory(configuration);
    }*/

    @Bean
    public RedisTemplate<String, Object> injectRedisTemplate(
            LettuceConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new <String, Object>RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        //json存储格式
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer =
                new GenericFastJsonRedisSerializer();
        //string存储格式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
