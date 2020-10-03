package com.jeramtough.randl2.common.component.userdetail;

import com.jeramtough.randl2.common.model.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/6/13 2:57
 * by @author JeramTough
 * </pre>
 */
@Component
public class VisitorUser {

    @Value("${spring.security.visitor.name}")
    private String username;

    @Value("${spring.security.visitor.password}")
    private String password;

    public static final Role ROLE = RegisteredUserRole.VisitorRole.get();

    public static final Long UID = -1L;

    public static final Long SURFACE_IMAGE_ID = 2L;

    private SystemUser systemUser = null;

    public SystemUser getSystemUser() {
        if (systemUser == null) {
            synchronized (this) {
                if (systemUser == null) {
                    systemUser = new SystemUser();
                    systemUser.setUid(UID);
                    systemUser.setUsername(username);
                    systemUser.setPassword(password);
                    systemUser.setRole(VisitorUser.ROLE);
                    systemUser.setUserType(UserType.VISITOR);
                    systemUser.setSurfaceImageId(SURFACE_IMAGE_ID);
                }
            }
        }
        return systemUser;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
