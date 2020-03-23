package com.jeramtough.randl2.component.userdetail;

import com.jeramtough.randl2.dao.entity.Role;

/**
 * <pre>
 * Created on 2020/3/23 18:42
 * by @author JeramTough
 * </pre>
 */
public class RegisteredUserRole {

    private Long fid;

    private String name;

    private String description;

    public static Role get() {
        Role role = new Role();
        role.setFid(-1L);
        role.setName("REGISTERED_USER");
        role.setDescription("普通注册用户");
        return role;
    }
}
