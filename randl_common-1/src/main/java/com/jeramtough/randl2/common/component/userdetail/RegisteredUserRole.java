package com.jeramtough.randl2.common.component.userdetail;


import com.jeramtough.randl2.common.model.entity.Role;

/**
 * <pre>
 * Created on 2020/3/23 18:42
 * by @author JeramTough
 * </pre>
 */
public class RegisteredUserRole {

    public static final class VisitorRole {

        public static Role get() {
            Role role = new Role();
            role.setFid(1L);
            role.setName("PRIMARY_VISITOR_USER");
            role.setDescription("普通游客用户");
            return role;
        }
    }

    public static final class PrimaryRole {

        public static Role get() {
            Role role = new Role();
            role.setFid(2L);
            role.setName("PRIMARY_REGISTERED_USER");
            role.setDescription("普通注册用户");
            return role;
        }
    }


}
