package com.jeramtough.randl2.component.userdetail;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * <pre>
 * Created on 2020/1/29 23:49
 * by @author JeramTough
 * </pre>
 */
public class UserHolder {
    public static SystemUser getSystemUser() {
        return (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
