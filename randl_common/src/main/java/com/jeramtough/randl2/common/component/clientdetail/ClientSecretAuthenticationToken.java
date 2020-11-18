package com.jeramtough.randl2.common.component.clientdetail;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <pre>
 * Created on 2020/11/18 13:07
 * by @author WeiBoWen
 * </pre>
 */
public class ClientSecretAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public ClientSecretAuthenticationToken(String clientId, String clientSecret) {
        super(clientId, clientSecret);
    }

    public ClientSecretAuthenticationToken(String clientId, String clientSecret,
                                           Collection<? extends GrantedAuthority> authorities) {
        super(clientId, clientSecret, authorities);
    }
}
