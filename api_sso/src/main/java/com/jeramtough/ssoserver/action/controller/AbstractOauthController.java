package com.jeramtough.ssoserver.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    private final TokenGranter tokenGranter;
    private final ClientDetailsService clientDetailsService;
    private final AuthorizationServerTokenServices tokenServices;
    private final AuthorizationCodeServices authorizationCodeServices;
    private final AuthenticationManager authenticationManager;

    private OAuth2RequestValidator requestValidator;

    private final OAuth2RequestFactory oAuth2RequestFactory;


    public AbstractOauthController(
            TokenGranter tokenGranter,
            @Qualifier("oauthClientDetailsServiceImpl")
            ClientDetailsService clientDetailsService,
            AuthorizationServerTokenServices tokenServices,
            AuthorizationCodeServices authorizationCodeServices,
            AuthenticationManager authenticationManager,
            OAuth2RequestFactory oAuth2RequestFactory) {
        this.tokenGranter = tokenGranter;
        this.tokenServices = tokenServices;
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.authorizationCodeServices = authorizationCodeServices;
        this.oAuth2RequestFactory = oAuth2RequestFactory;
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
        return oAuth2RequestFactory;
    }

}
