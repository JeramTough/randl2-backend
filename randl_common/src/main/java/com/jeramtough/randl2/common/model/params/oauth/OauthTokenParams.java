package com.jeramtough.randl2.common.model.params.oauth;

import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/11/18 21:20
 * by @author WeiBoWen
 * </pre>
 */
@Component
@Scope(value = "request")
public class OauthTokenParams {

    private final HttpServletRequest request;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientId;

    @NotNull(payload = ErrorU.CODE_1.class)
    private String clientSecret;

    @Autowired
    public OauthTokenParams(HttpServletRequest request) {
        this.request = request;
        init();
    }

    void init() {
        this.clientId = request.getParameter(OAuth2Constants.CLIENT_ID);
        if (clientId==null){
            this.clientId = request.getParameter(OAuth2Constants.CLIENT_ID_2);
        }

        this.clientSecret = request.getParameter(OAuth2Constants.CLIENT_SECRET);
        if (this.clientSecret==null){
            this.clientSecret=request.getParameter(OAuth2Constants.CLIENT_SECRET_2);
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
}
