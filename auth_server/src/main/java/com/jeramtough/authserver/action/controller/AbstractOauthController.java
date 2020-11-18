package com.jeramtough.authserver.action.controller;

import com.jeramtough.randl2.common.action.controller.MyBaseController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Created on 2020/11/17 21:22
 * by @author WeiBoWen
 * </pre>
 */
public abstract class AbstractOauthController extends MyBaseController {


    private TokenGranter tokenGranter;
    private final ClientDetailsService clientDetailsService;
    private final AuthorizationServerTokenServices tokenServices;
    private final AuthorizationCodeServices authorizationCodeServices;
    private final AuthenticationManager clientAuthenticationManager;

    private OAuth2RequestValidator requestValidator;

    private OAuth2RequestFactory oAuth2RequestFactory;


    public AbstractOauthController(
            ClientDetailsService clientDetailsService,
            AuthorizationServerTokenServices tokenServices,
            AuthorizationCodeServices authorizationCodeServices,
            @Qualifier("clientAuthenticationManager")
            AuthenticationManager clientAuthenticationManager) {
        this.tokenServices = tokenServices;
        this.clientAuthenticationManager = clientAuthenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.authorizationCodeServices = authorizationCodeServices;
    }


    public TokenGranter getTokenGranter() {
        if (tokenGranter == null) {
            tokenGranter = new TokenGranter() {
                private CompositeTokenGranter delegate;

                @Override
                public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                    if (delegate == null) {
                        delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                    }
                    return delegate.grant(grantType, tokenRequest);
                }
            };
        }
        return tokenGranter;
    }

    public ClientDetailsService getClientDetailsService() {
        return clientDetailsService;
    }

    public OAuth2RequestValidator getOAuth2RequestValidator() {
        if (requestValidator != null) {
            return requestValidator;
        }
        requestValidator = new DefaultOAuth2RequestValidator();
        return requestValidator;
    }


    public OAuth2RequestFactory getOAuth2RequestFactory() {
        if (oAuth2RequestFactory != null) {
            return oAuth2RequestFactory;
        }
        oAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsService);
        return oAuth2RequestFactory;
    }

    private List<TokenGranter> getDefaultTokenGranters() {
        ClientDetailsService clientDetails = this.clientDetailsService;
        AuthorizationServerTokenServices tokenServices = this.tokenServices;
        AuthorizationCodeServices authorizationCodeServices = this.authorizationCodeServices;
        OAuth2RequestFactory requestFactory = getOAuth2RequestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        tokenGranters.add(
                new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
                        requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
        tokenGranters.add(implicit);
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));

        if (clientAuthenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(clientAuthenticationManager, tokenServices,
                    clientDetails, requestFactory));
        }
        return tokenGranters;
    }
}
