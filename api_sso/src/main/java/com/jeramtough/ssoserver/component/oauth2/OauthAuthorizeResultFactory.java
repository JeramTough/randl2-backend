package com.jeramtough.ssoserver.component.oauth2;

import com.alibaba.fastjson.JSON;
import com.jeramtough.jtcomponent.http.URLBuilder;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import com.jeramtough.randl2.common.model.params.oauth.OauthAuthorizeResult4AuthorizationCodeGrant;
import com.jeramtough.randl2.common.model.params.oauth.OauthAuthorizeResult4ImplicitGrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * Created on 2021/2/13 12:30
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class OauthAuthorizeResultFactory {

    private final RandlOAuth2RequestFactory randlOAuth2RequestFactory;
    private final MyTokenGranter myTokenGranter;
    private final AuthorizationCodeServices authorizationCodeServices;

    private final Object implicitLock = new Object();

    @Autowired
    public OauthAuthorizeResultFactory(
            RandlOAuth2RequestFactory randlOauth2RequestFactory,
            MyTokenGranter myTokenGranter,
            AuthorizationCodeServices authorizationCodeServices) {
        this.randlOAuth2RequestFactory = randlOauth2RequestFactory;
        this.myTokenGranter = myTokenGranter;
        this.authorizationCodeServices = authorizationCodeServices;
    }

    /**
     * 得到授权码模式的授权结果
     */
    public OauthAuthorizeResult4AuthorizationCodeGrant getAuthorizationCodeGrantResult(
            AuthorizationRequest authorizationRequest,
            Authentication authUser) {

        try {
            if (authorizationRequest.isApproved()) {
                return getSuccessfulResult4AuthorizationCodeGrant(authorizationRequest,
                        generateCode(authorizationRequest, authUser));
            }
            else {
                OAuth2Exception oAuth2Exception = new OAuth2Exception("User has refused authorization");
                return getUnsuccessfulResult4AuthorizationCodeGrant(authorizationRequest, oAuth2Exception);
            }
        }
        catch (OAuth2Exception e) {
            return getUnsuccessfulResult4AuthorizationCodeGrant(authorizationRequest, e);
        }
    }

    /**
     * 得到简化模式的授权结果
     * We can grant a token and return it with implicit approval. which is
     * the ResultForImplicitGrant
     */
    public OauthAuthorizeResult4ImplicitGrant getImplicitGrantResult(AuthorizationRequest authorizationRequest) {
        try {
            TokenRequest tokenRequest = randlOAuth2RequestFactory.createTokenRequest(authorizationRequest,
                    "implicit");
            OAuth2Request storedOauth2Request = randlOAuth2RequestFactory.createOAuth2Request(
                    authorizationRequest);

            OAuth2AccessToken accessToken = getAccessTokenForImplicitGrant(tokenRequest, storedOauth2Request);
            if (accessToken == null) {
                throw new UnsupportedResponseTypeException("Unsupported response type: token");
            }

            if (storedOauth2Request.isApproved()) {
                return getSuccessfulResult4ImplicitGrant(authorizationRequest, accessToken);
            }
            else {
                OAuth2Exception oAuth2Exception = new OAuth2Exception("User has refused authorization");
                return getUnsuccessfulResult4ImplicitGrant(authorizationRequest, oAuth2Exception);
            }

        }
        catch (OAuth2Exception e) {
            return getUnsuccessfulResult4ImplicitGrant(authorizationRequest, e);
        }
    }


    /**
     * 生成授权码
     */
    private String generateCode(AuthorizationRequest authorizationRequest, Authentication authentication)
            throws AuthenticationException {
        OAuth2Request storedOauth2Request = randlOAuth2RequestFactory.createOAuth2Request(
                authorizationRequest);
        OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOauth2Request, authentication);
        String code = authorizationCodeServices.createAuthorizationCode(combinedAuth);
        return code;
    }

    /**
     * 生成令牌
     */
    private OAuth2AccessToken getAccessTokenForImplicitGrant(TokenRequest tokenRequest,
                                                             OAuth2Request storedOauth2Request) {
        OAuth2AccessToken accessToken;
        // These 1 method calls have to be atomic, otherwise the ImplicitGrantService can have a race condition where
        // one thread removes the token request before another has a chance to redeem it.
        synchronized (this.implicitLock) {
            accessToken = myTokenGranter.grant("implicit",
                    new ImplicitTokenRequest(tokenRequest, storedOauth2Request));
        }
        return accessToken;
    }

    /**
     * 返回授权码模式成功结果
     */
    private OauthAuthorizeResult4AuthorizationCodeGrant getSuccessfulResult4AuthorizationCodeGrant(
            AuthorizationRequest authorizationRequest,
            String authorizationCode) {

        if (authorizationCode == null) {
            throw new IllegalStateException("No authorization code found in the current request scope.");
        }


        OauthAuthorizeResult4AuthorizationCodeGrant result = new OauthAuthorizeResult4AuthorizationCodeGrant();
        result.setCode(authorizationCode);
        result.setIsApproved(true);

        String state = authorizationRequest.getState();
        if (!StringUtils.isEmpty(state)) {
            result.setState(state);
        }

        URLBuilder urlBuilder = new URLBuilder(authorizationRequest.getRedirectUri());
        urlBuilder.appendParam("code", result.getCode());
        urlBuilder.appendParam("isApproved", result.getIsApproved() ? "1" : "0");
        if (!StringUtils.isEmpty(result.getState())) {
            urlBuilder.appendParamEncode("state", result.getState());
        }

        String redirectUri = urlBuilder.build();
        result.setRedirectUri(redirectUri);

        return result;
    }

    /**
     * 返回授权码模式失败结果
     */
    private OauthAuthorizeResult4AuthorizationCodeGrant getUnsuccessfulResult4AuthorizationCodeGrant(
            AuthorizationRequest authorizationRequest,
            OAuth2Exception failure) {

        if (authorizationRequest == null || authorizationRequest.getRedirectUri() == null) {
            // we have no redirect for the user. very sad.
            throw new UnapprovedClientAuthenticationException("Authorization failure, and no redirect URI.",
                    failure);
        }

        OauthAuthorizeResult4AuthorizationCodeGrant result = new OauthAuthorizeResult4AuthorizationCodeGrant();
        result.setIsApproved(false);

        String state = authorizationRequest.getState();
        if (!StringUtils.isEmpty(state)) {
            result.setState(state);
        }

        result.setError(failure.getOAuth2ErrorCode());
        result.setErrorDescription(failure.getMessage());

        if (failure.getAdditionalInformation() != null) {
            result.setAdditionalInfo(JSON.toJSONString(failure.getAdditionalInformation()));
        }

        URLBuilder urlBuilder = new URLBuilder(authorizationRequest.getRedirectUri());
        urlBuilder.appendParam("isApproved", result.getIsApproved() ? "1" : "0");

        if (!StringUtils.isEmpty(result.getState())){
            urlBuilder.appendParamEncode("state", result.getState());
        }
        if (!StringUtils.isEmpty(result.getErrorDescription())){
            urlBuilder.appendParamEncode("errorDescription", result.getErrorDescription());
        }


        String redirectUri = urlBuilder.build();
        result.setRedirectUri(redirectUri);

        return result;

    }


    private OauthAuthorizeResult4ImplicitGrant getSuccessfulResult4ImplicitGrant(
            AuthorizationRequest authorizationRequest,
            OAuth2AccessToken accessToken) {

        if (accessToken == null) {
            throw new InvalidRequestException("An implicit grant could not be made");
        }

        OauthAuthorizeResult4ImplicitGrant result = new OauthAuthorizeResult4ImplicitGrant();
        result.setIsApproved(true);
        result.setAccessToken(accessToken.getValue());
        result.setTokenType(accessToken.getTokenType());

        String state = authorizationRequest.getState();
        if (state != null) {
            result.setState(state);
        }

        Date expiration = accessToken.getExpiration();
        if (expiration != null) {
            long expiresIn = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            result.setExpiresIn(expiresIn);
        }

        //以生成令牌的scope为准
        String originalScope = authorizationRequest.getRequestParameters().get(OAuth2Constants.SCOPE);
        if (originalScope == null || !OAuth2Utils.parseParameterList(originalScope).equals(
                accessToken.getScope())) {
            result.setScope(OAuth2Utils.formatParameterList(accessToken.getScope()));
        }

        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        if (additionalInformation.size() > 0) {
            result.setAdditionalInfo(JSON.toJSONString(additionalInformation));
        }

        String redirectUri = authorizationRequest.getRedirectUri();
        URLBuilder urlBuilder = new URLBuilder(redirectUri);
        urlBuilder.appendParam("accessToken", result.getAccessToken());
        urlBuilder.appendParam("isApproved", result.getIsApproved() ? "1" : "0");
        urlBuilder.appendParam("tokenType", result.getTokenType());
        urlBuilder.appendParam("expiresIn", result.getExpiresIn().toString());
        if (!StringUtils.isEmpty(result.getState())) {
            urlBuilder.appendParamEncode("state", result.getState());
        }
        if (!StringUtils.isEmpty(result.getScope())) {
            urlBuilder.appendParam("scope", result.getScope());
        }
        if (!StringUtils.isEmpty(result.getAdditionalInfo())) {
            urlBuilder.appendParamEncode("additionalInfo", result.getAdditionalInfo());
        }

        // Do not include the refresh token (even if there is one)
        return result;
    }

    /**
     * 返回简化隐式模式失败结果
     */
    private OauthAuthorizeResult4ImplicitGrant getUnsuccessfulResult4ImplicitGrant(
            AuthorizationRequest authorizationRequest,
            OAuth2Exception failure) {

        if (authorizationRequest == null || authorizationRequest.getRedirectUri() == null) {
            // we have no redirect for the user. very sad.
            throw new UnapprovedClientAuthenticationException("Authorization failure, and no redirect URI.",
                    failure);
        }

        OauthAuthorizeResult4ImplicitGrant result = new OauthAuthorizeResult4ImplicitGrant();
        result.setIsApproved(false);

        String state = authorizationRequest.getState();
        if (!StringUtils.isEmpty(state)) {
            result.setState(state);
        }

        result.setError(failure.getOAuth2ErrorCode());
        result.setErrorDescription(failure.getMessage());

        if (failure.getAdditionalInformation() != null) {
            result.setAdditionalInfo(JSON.toJSONString(failure.getAdditionalInformation()));
        }

        URLBuilder urlBuilder = new URLBuilder(authorizationRequest.getRedirectUri());
        urlBuilder.appendParam("isApproved", result.getIsApproved() ? "1" : "0");
        if ((!StringUtils.isEmpty(result.getState()))) {
            urlBuilder.appendParamEncode("state", result.getState());
        }
        if (!StringUtils.isEmpty(result.getErrorDescription())) {
            urlBuilder.appendParamEncode("errorDescription", result.getErrorDescription());
        }

        String redirectUri = urlBuilder.build();
        result.setRedirectUri(redirectUri);

        return result;
    }

}
