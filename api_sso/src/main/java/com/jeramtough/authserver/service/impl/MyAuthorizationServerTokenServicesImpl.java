package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.service.MyAuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Created on 2020/11/17 22:16
 * by @author WeiBoWen
 * </pre>
 */
public class MyAuthorizationServerTokenServicesImpl extends DefaultTokenServices
        implements MyAuthorizationServerTokenServices {
}
