package com.jeramtough.randl2.component.userdetail;

import com.jeramtough.randl2.dao.entity.AdminUser;
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
    public static SystemUser getSystemUser() {
        return (SystemUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public static boolean isSuperAdmin() {
        return SuperAdmin.UID.equals(getSystemUser().getUid());
    }

    public static void afterLogin(SystemUser systemUser) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(
                new JaasGrantedAuthority("ROLE_" + systemUser.getRole().getName(),
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
}
