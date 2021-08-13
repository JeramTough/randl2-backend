package com.jeramtough.randl2.component.login.client;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/17 10:41
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class ClientIdLoginer implements ClientLoginer {

    private final OauthClientDetailsService oauthClientDetailsService;

    public ClientIdLoginer(OauthClientDetailsService oauthClientDetailsService) {
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @Override
    public MyClientDetails login(Object credentials) throws ApiResponseException {
        String clientId = (String) credentials;
        MyClientDetails clientDetails = (MyClientDetails) oauthClientDetailsService.loadClientByClientId(clientId);

        Long appId = clientDetails.getOauthClientDetails().getAppId();
        if (appId == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "Randl应用");
        }

        return null;
    }
}
