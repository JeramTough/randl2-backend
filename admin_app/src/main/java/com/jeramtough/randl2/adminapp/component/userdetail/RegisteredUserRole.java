package com.jeramtough.randl2.adminapp.component.userdetail;


import com.jeramtough.randl2.common.model.entity.RandRole;

/**
 * <pre>
 * Created on 2020/3/23 18:42
 * by @author JeramTough
 * </pre>
 */
public class RegisteredUserRole {

    public static final class VisitorRole {

        public static RandRole get() {
            RandRole randRole = new RandRole();
            randRole.setFid(1L);
            randRole.setName("PRIMARY_VISITOR_USER");
            randRole.setDescription("普通游客用户");
            return randRole;
        }
    }

    public static final class PrimaryRole {

        public static RandRole get() {
            RandRole randRole = new RandRole();
            randRole.setFid(2L);
            randRole.setName("PRIMARY_REGISTERED_USER");
            randRole.setDescription("普通注册用户");
            return randRole;
        }
    }


}
