package com.jeramtough.ssoserver.component.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Created on 2021/1/31 15:17
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class MyTokenGranter implements TokenGranter {

    private final ClientDetailsService clientDetailsService;
    private final AuthorizationServerTokenServices tokenServices;
    private final AuthorizationCodeServices authorizationCodeServices;
    private final AuthenticationManager authenticationManager;
    private final RandlOAuth2RequestFactory randlOauth2RequestFactory;

    private CompositeTokenGranter delegate;

    @Autowired
    public MyTokenGranter(
            @Qualifier("oauthClientDetailsServiceImpl")
                    ClientDetailsService clientDetailsService,
            AuthorizationServerTokenServices tokenServices,
            AuthorizationCodeServices authorizationCodeServices,
            @Qualifier("authenticationManager")
                    AuthenticationManager authenticationManager,
            RandlOAuth2RequestFactory randlOauth2RequestFactory) {
        this.clientDetailsService = clientDetailsService;
        this.tokenServices = tokenServices;
        this.authorizationCodeServices = authorizationCodeServices;
        this.authenticationManager = authenticationManager;
        this.randlOauth2RequestFactory = randlOauth2RequestFactory;
    }

    @Override
    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        if (delegate == null) {
            delegate = new CompositeTokenGranter(getDefaultTokenGranters());
        }
        return delegate.grant(grantType, tokenRequest);
    }

    //******************

    private List<TokenGranter> getDefaultTokenGranters() {
        ClientDetailsService clientDetails = this.clientDetailsService;
        AuthorizationServerTokenServices tokenServices = this.tokenServices;
        AuthorizationCodeServices authorizationCodeServices = this.authorizationCodeServices;
        OAuth2RequestFactory requestFactory = this.randlOauth2RequestFactory;

        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        tokenGranters.add(
                new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
                        requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
        tokenGranters.add(implicit);
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));

        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                    clientDetails, requestFactory));
        }
        return tokenGranters;
    }
}
