package com.jeramtough.ssoserver.service.impl;

import com.jeramtough.jtcomponent.http.URLBuilder;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.ssoserver.component.oauth2.OauthAuthorizeResultFactory;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import com.jeramtough.randl2.common.model.params.oauth.OauthAuthorizeResult4AuthorizationCodeGrant;
import com.jeramtough.randl2.common.model.params.oauth.OauthAuthorizeResult4ImplicitGrant;
import com.jeramtough.randl2.common.model.params.oauth.SubmitAuthorizeParams;
import com.jeramtough.randl2.service.oauth.OauthScopeDetailsService;
import com.jeramtough.ssoserver.service.SsoAccessService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.ssoserver.component.oauth2.Oauth2RequestHolder;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * Created on 2021/1/21 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class SsoAccessServiceImpl implements SsoAccessService {

    private final Oauth2RequestHolder oauth2RequestHolder;
    private final UserApprovalHandler userApprovalHandler;
    private final OauthAuthorizeResultFactory oauthAuthorizeResultFactory;
    private final OauthScopeDetailsService scopeDetailsService;
    private final AppSetting appSetting;

    @Autowired
    public SsoAccessServiceImpl(
            Oauth2RequestHolder oauth2RequestHolder,
            OauthAuthorizeResultFactory oauthAuthorizeResultFactory,
            OauthScopeDetailsService scopeDetailsService,
            AppSetting appSetting) {
        this.oauth2RequestHolder = oauth2RequestHolder;
        this.oauthAuthorizeResultFactory = oauthAuthorizeResultFactory;
        this.scopeDetailsService = scopeDetailsService;
        this.appSetting = appSetting;
        userApprovalHandler = new DefaultUserApprovalHandler();
    }

    @Override
    public RedirectView goLoginPage(Map<String, String> parameters) {
        String tempClientId = this.saveAccessInfo(parameters);

        //获取配置的登录页
        String loginPageUrl = appSetting.getLoginPageUrl();

        //重定向
        String where = new URLBuilder()
                .url(loginPageUrl)
                .appendParam("tempClientId", tempClientId)
                .build();
        RedirectView redirectView = new RedirectView(where);
        return redirectView;
    }

    @Override
    public String saveAccessInfo(Map<String, String> parameters) {
        String tempClientId = oauth2RequestHolder.saveRequestParams(parameters);
        return tempClientId;
    }

    @Override
    public Map<String, String> getAccessInfo(String tempClientId) {
        Map<String, String> requestParams = oauth2RequestHolder.getRequestParams(tempClientId);
        if (requestParams == null) {
            throw new ApiResponseException(ErrorU.CODE_803.C);
        }
        return requestParams;
    }

    @Override
    public Object getAuthorizationResult(SubmitAuthorizeParams params) {
        BeanValidator.verifyParams(params);

        String clientScopesStr = scopeDetailsService.getClientScopesStr(params.getScopeIds());

        AuthorizationRequest authorizationRequest = oauth2RequestHolder.getAuthorizationRequest(
                params.getTempClientId(), clientScopesStr);
        if (authorizationRequest == null) {
            throw new ApiResponseException(ErrorU.CODE_803.C);
        }
        if (authorizationRequest.getRedirectUri() == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_1.C, "redirectUri");
        }

        //设置是否同意授权
        Map<String, String> approvalParameters = new HashMap<>(2);
        approvalParameters.put(OAuth2Constants.USER_OAUTH_APPROVAL, params.getIsApproved().toString());
        approvalParameters.put(OAuth2Constants.USER_OAUTH_APPROVAL_2, params.getIsApproved().toString());
        authorizationRequest.setApprovalParameters(approvalParameters);

        //缓存授权结果
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authorizationRequest = userApprovalHandler.updateAfterApproval(authorizationRequest,
                authentication);
        boolean isApproved = userApprovalHandler.isApproved(authorizationRequest, authentication);
        authorizationRequest.setApproved(isApproved);

        //授权码模式和隐式模式，分别返回不同的响应对象
        Set<String> responseTypes = authorizationRequest.getResponseTypes();
        if (responseTypes.contains(OAuth2Constants.RESPONSE_TYPE_4_TOKEN)) {
            OauthAuthorizeResult4ImplicitGrant result4ImplicitGrant =
                    oauthAuthorizeResultFactory.getImplicitGrantResult(authorizationRequest);
            return result4ImplicitGrant;
        }
        else {
            OauthAuthorizeResult4AuthorizationCodeGrant result4AuthorizationCodeGrant =
                    oauthAuthorizeResultFactory.getAuthorizationCodeGrantResult(
                            authorizationRequest, authentication);
            return result4AuthorizationCodeGrant;
        }
    }

    //***********


}
