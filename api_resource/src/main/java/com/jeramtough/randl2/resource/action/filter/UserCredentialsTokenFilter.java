package com.jeramtough.randl2.resource.action.filter;

import com.jeramtough.randl2.common.action.filter.BaseUrlMatchingFilter;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import com.jeramtough.randl2.common.model.detail.authdetail.OAuth2AuthenticationPlusDetails;
import com.jeramtough.randl2.service.details.MyUserDetailsService;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <pre>
 *   除了客户端模式，其他授权模式都包含用户信息
 *
 * Created on 2020/11/17 22:32
 * by @author WeiBoWen
 * </pre>
 */
public class UserCredentialsTokenFilter extends BaseUrlMatchingFilter {

    private final MyUserDetailsService myUserDetailsService;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final TokenStore tokenStore;

    public UserCredentialsTokenFilter(
            MyUserDetailsService myUserDetailsService,
            OauthClientDetailsService oauthClientDetailsService,
            TokenStore tokenStore) {
        super("/user/**");
        this.myUserDetailsService = myUserDetailsService;
        this.oauthClientDetailsService = oauthClientDetailsService;
        this.tokenStore = tokenStore;
    }

    @Override
    public void doFilterContinue(ServletRequest servletRequest,
                                 ServletResponse servletResponse,
                                 FilterChain filterChain) throws
            ServletException, IOException {

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2Authentication)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        OAuth2Authentication oAuth2Authentication =
                (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();

        //clientId
        String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
        MyClientDetails myClientDetails =
                (MyClientDetails) oauthClientDetailsService.loadClientByClientId(clientId);

        //获取令牌的用户名
        String account = oAuth2Authentication.getUserAuthentication().getPrincipal().toString();
        Long appId = myClientDetails.getOauthClientDetails().getAppId();
        MyUserDetails myUserDetails = myUserDetailsService.loadUserByAccount(account, appId);

        //替换默认的OAuth2AuthenticationDetails
        OAuth2AuthenticationPlusDetails oAuth2AuthenticationPlusDetails =
                new OAuth2AuthenticationPlusDetails((HttpServletRequest) servletRequest,
                        myUserDetails,
                        myClientDetails);
        oAuth2Authentication.setDetails(oAuth2AuthenticationPlusDetails);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

