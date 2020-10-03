package com.jeramtough.randl2.adminapp.component.userdetail;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasGrantedAuthority;
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
        if ((SecurityContextHolder.getContext().getAuthentication() != null) &&
                (SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof SystemUser)) {
            return true;
        }
        return false;
    }

    public static SystemUser getSystemUser() {
        return (SystemUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public static boolean isSuperAdmin() {
        return SuperAdmin.UID.equals(getSystemUser().getUid());
    }

    public static boolean isAdminUser() {
        return UserType.ADMIN.equals(getSystemUser().getUserType());
    }

    public static void afterLogin(SystemUser systemUser) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(
                new JaasGrantedAuthority("ROLE_" + systemUser.getRandRole().getName(),
                        systemUser));
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(systemUser.getUsername(),
                        systemUser.getPassword(), grantedAuthorityList);
        token.setDetails(systemUser);
        securityContext.setAuthentication(token);
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
