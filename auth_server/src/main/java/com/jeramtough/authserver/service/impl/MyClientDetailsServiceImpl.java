package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.service.MyClientDetailsService;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.authserver.component.oauth2.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/11/16 10:00
 * by @author WeiBoWen
 * </pre>
 */
@Service("myClientDetailsService")
public class MyClientDetailsServiceImpl extends BaseServiceImpl implements MyClientDetailsService {

    private final OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    public MyClientDetailsServiceImpl(
            WebApplicationContext wc,
            OauthClientDetailsService oauthClientDetailsService) {
        super(wc);
        this.oauthClientDetailsService = oauthClientDetailsService;
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.getOneByClientId(clientId);
        ClientDetails clientDetails = new MyClientDetails(oauthClientDetails);
        return clientDetails;
    }
}
