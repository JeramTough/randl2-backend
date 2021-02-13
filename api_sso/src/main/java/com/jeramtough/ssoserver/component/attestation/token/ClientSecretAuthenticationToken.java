package com.jeramtough.ssoserver.component.attestation.token;

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

    private String clientId;
    private String clientSecret;

    public ClientSecretAuthenticationToken(String clientId, String clientSecret) {
        super(clientId, clientSecret);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public ClientSecretAuthenticationToken(String clientId, String clientSecret,
                                           Collection<? extends GrantedAuthority> authorities) {
        super(clientId, clientSecret, authorities);
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
