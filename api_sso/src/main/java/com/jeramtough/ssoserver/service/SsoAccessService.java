package com.jeramtough.ssoserver.service;

import com.jeramtough.randl2.common.model.params.oauth.OauthAuthorizeResult4AuthorizationCodeGrant;
import com.jeramtough.randl2.common.model.params.oauth.SubmitAuthorizeParams;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

/**
 * <pre>
 * Created on 2021/1/21 11:05
 * by @author WeiBoWen
 * </pre>
 */
public interface SsoAccessService {

    RedirectView goLoginPage(Map<String, String> parameters);

    /**
     * 保存oauth2客户端信息并返回临时Id
     */
    String saveAccessInfo(Map<String, String> parameters);

    Map<String, String> getAccessInfo(String tempClientId);

    Object getAuthorizationResult(SubmitAuthorizeParams params);


}
