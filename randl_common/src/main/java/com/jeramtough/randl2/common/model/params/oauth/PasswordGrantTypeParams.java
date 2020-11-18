package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/11/17 10:24
 * by @author WeiBoWen
 * </pre>
 */
@Component
@Scope(value = "request")
public class PasswordGrantTypeParams {

    private final HttpServletRequest request;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientId;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientSecret;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String username;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String password;

    @Autowired
    public PasswordGrantTypeParams(HttpServletRequest request) {
        this.request = request;

        clientId = this.request.getParameter("client_id");
        clientSecret = this.request.getParameter("client_secret");
        username = this.request.getParameter("username");
        password = this.request.getParameter("password");

        if (clientId != null) {
            clientId = clientId.trim();
        }
        if (username != null) {
            username = username.trim();
        }
        if (password != null) {
            password = password.trim();
        }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
