package com.jeramtough.authserver.action.controller;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.component.attestation.oauth2.RandlOAuth2RequestFactory;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Created on 2020/11/17 21:22
 * by @author WeiBoWen
 * </pre>
 */
@ApiResponses(value = {
        @ApiResponse(code = ErrorU.CODE_801.C, message = ErrorU.CODE_801.M),
})
public abstract class AbstractOauthController extends MyBaseController {


    private TokenGranter tokenGranter;
    private final ClientDetailsService clientDetailsService;
    private final AuthorizationServerTokenServices tokenServices;
    private final AuthorizationCodeServices authorizationCodeServices;
    private final AuthenticationManager authenticationManager;

    private OAuth2RequestValidator requestValidator;

    private OAuth2RequestFactory oAuth2RequestFactory;


    public AbstractOauthController(
            ClientDetailsService clientDetailsService,
            AuthorizationServerTokenServices tokenServices,
            AuthorizationCodeServices authorizationCodeServices,
            @Qualifier("authenticationManager")
                    AuthenticationManager authenticationManager) {
        this.tokenServices = tokenServices;
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.authorizationCodeServices = authorizationCodeServices;
    }


    @Override
    protected CommonApiResponse<String> handleException(HttpServletRequest request, HttpServletResponse response,
                                                        CommonApiResponse<String> failedApiResponse, Exception e) {
        if (e instanceof InternalAuthenticationServiceException) {
            InternalAuthenticationServiceException internalAuthenticationServiceException = (InternalAuthenticationServiceException) e;
            if (internalAuthenticationServiceException.getCause() instanceof ApiResponseException) {
                ApiResponseException apiResponseException = (ApiResponseException) internalAuthenticationServiceException.getCause();
                return getFailedApiResponse(apiResponseException);
            }
            else {
                ApiResponseException apiResponseException = new ApiResponseException(ErrorU.CODE_801.C,
                        internalAuthenticationServiceException.getMessage());
                return getFailedApiResponse(apiResponseException);

            }
        }
        if (e instanceof OAuth2Exception) {
            ApiResponseException apiResponseException = new ApiResponseException(ErrorU.CODE_801.C,
                    e.getMessage());
            return getFailedApiResponse(apiResponseException);
        }
        return super.handleException(request, response, failedApiResponse, e);
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
        oAuth2RequestFactory = new RandlOAuth2RequestFactory(clientDetailsService);
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

        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                    clientDetails, requestFactory));
        }
        return tokenGranters;
    }
}