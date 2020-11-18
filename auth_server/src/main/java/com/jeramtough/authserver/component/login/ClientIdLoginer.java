package com.jeramtough.authserver.component.login;

import com.jeramtough.authserver.service.MyClientDetailsService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.login.client.ClientLoginer;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.codehaus.janino.IClass;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/17 10:41
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class ClientIdLoginer implements ClientLoginer {

    private final MyClientDetailsService myClientDetailsService;

    public ClientIdLoginer(MyClientDetailsService myClientDetailsService) {
        this.myClientDetailsService = myClientDetailsService;
    }

    @Override
    public MyClientDetails login(Object credentials) throws ApiResponseException {
        String clientId = (String) credentials;
        MyClientDetails clientDetails = (MyClientDetails) myClientDetailsService.loadClientByClientId(clientId);

        Long appId = clientDetails.getOauthClientDetails().getAppId();
        if (appId == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "Randl应用");
        }

        return null;
    }
}
