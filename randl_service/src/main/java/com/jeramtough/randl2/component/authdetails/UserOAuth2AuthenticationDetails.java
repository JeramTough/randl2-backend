package com.jeramtough.randl2.component.authdetails;

import com.jeramtough.randl2.service.details.MyUserDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2021/8/9 下午3:01
 * by @author WeiBoWen
 * </pre>
 */
public class UserOAuth2AuthenticationDetails extends OAuth2AuthenticationDetails {


    private MyUserDetailsService myUserDetailsService;

    /**
     * Records the access token value and remote address and will also set the session Id if a session already exists
     * (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public UserOAuth2AuthenticationDetails(HttpServletRequest request) {
        super(request);
    }
}
