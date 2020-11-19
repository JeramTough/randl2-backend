package com.jeramtough.authserver.component.oauth2.clientdetail;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * <pre>
 * Created on 2020/11/17 10:10
 * by @author WeiBoWen
 * </pre>
 */
public class ClientHolder {

    public static boolean hasLogined() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return true;
        }
        return false;
    }

    public static OAuth2Authentication getClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            return oAuth2Authentication;
        }
        return null;
    }
}
