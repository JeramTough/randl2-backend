package com.jeramtough.authserver.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * <pre>
 * Created on 2020/11/15 0:38
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
public class AuthClientConfig {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthClientConfig(DataSource dataSource,
                            PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean(name = "jdbcClientDetailsService")
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService service = new JdbcClientDetailsService(dataSource);
        service.setPasswordEncoder(passwordEncoder);
        return service;
    }

}
