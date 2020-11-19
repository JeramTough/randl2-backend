package com.jeramtough.authserver.component.oauth2.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <pre>
 * Created on 2020/11/18 23:26
 * by @author WeiBoWen
 * </pre>
 */
public class PasswordGrantTypeClientAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = -4546901693569372741L;


    public PasswordGrantTypeClientAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PasswordGrantTypeClientAuthenticationToken(Object principal, Object credentials,
                                                      Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
