package com.jeramtough.randl2.common.component.attestation.userdetail;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Created on 2020/1/29 23:49
 * by @author JeramTough
 * </pre>
 */
public class UserHolder {

    public static boolean hasLogined() {
        if ((SecurityContextHolder.getContext().getAuthentication() != null)) {
            return true;
        }
        return false;
    }

    public static SystemUser getSystemUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        if (authentication.getDetails() instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            return myUserDetails.getSystemUser();
        }
        /*else if (authentication.getDetails() instanceof OAuth2AuthenticationPlusDetails) {
            OAuth2AuthenticationPlusDetails oAuth2AuthenticationPlusDetails =
                    (OAuth2AuthenticationPlusDetails) authentication.getDetails();
            MyUserDetails myUserDetails = oAuth2AuthenticationPlusDetails.getMyUserDetails();
            return myUserDetails.getSystemUser();
        }*/
        else {
            return null;
        }
    }

    /*public static MyClientDetails getClientDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getDetails() instanceof OAuth2AuthenticationPlusDetails) {
            OAuth2AuthenticationPlusDetails oAuth2AuthenticationPlusDetails =
                    (OAuth2AuthenticationPlusDetails) authentication.getDetails();
            MyClientDetails myClientDetails = oAuth2AuthenticationPlusDetails.getMyClientDetails();
            return myClientDetails;
        }
        else {
            return null;
        }
    }*/

    public static boolean isSuperAdmin() {
        return SuperAdmin.UID.equals(getSystemUser().getUid());
    }

    public static boolean isSuperAdmin(Long uid) {
        return SuperAdmin.UID.equals(uid);
    }

    public static void afterLogin(SystemUser systemUser) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        systemUser.getRoles().parallelStream().forEach(randlRole -> {
            JaasGrantedAuthority jaasGrantedAuthority =
                    new JaasGrantedAuthority("ROLE_" + randlRole.getAlias(),
                            systemUser);
            grantedAuthorityList.add(jaasGrantedAuthority);
        });
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(systemUser.getAccount(),
                        systemUser.getPassword(), grantedAuthorityList);

        MyUserDetails myUserDetails = new MyUserDetails(systemUser);
        token.setDetails(myUserDetails);
        securityContext.setAuthentication(token);
    }

    public static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) securityContext.getAuthentication();
        return token;
    }

    /**
     * 最好还是别用，让用户退出系统重新登录的好
     */
    public static void update(String phoneNumber, String emailAddress) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) securityContext.getAuthentication();

        SystemUser systemUser = getSystemUser();
        if (phoneNumber != null) {
            systemUser.setPhoneNumber(phoneNumber);
        }
        if (emailAddress != null) {
            systemUser.setEmailAddress(emailAddress);
        }

        token.setDetails(systemUser);
    }

    public static void clear() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
    }


}
