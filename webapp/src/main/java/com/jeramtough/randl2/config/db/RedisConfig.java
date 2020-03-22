package com.jeramtough.randl2.config.db;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
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

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                "127.0.0.1"
                , 6379);
        configuration.setPassword("123456");
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate injectRedisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer=
                new GenericFastJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
