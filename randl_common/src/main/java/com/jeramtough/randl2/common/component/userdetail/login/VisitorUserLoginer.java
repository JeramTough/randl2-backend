package com.jeramtough.randl2.common.component.userdetail.login;

import com.jeramtough.randl2.common.model.params.registereduser.LoginForVisitorCredentials;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.VisitorUser;
import com.jeramtough.randl2.common.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/3/22 21:00
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class VisitorUserLoginer extends BaseRegisteredUserLoginer implements UserLoginer {

    private final VisitorUser visitorUser;

    @Autowired
    protected VisitorUserLoginer(
            PasswordEncoder passwordEncoder, MapperFacade mapperFacade,
            RegisteredUserMapper registeredUserMapper, RoleMapper roleMapper,
            VisitorUser visitorUser) {
        super(passwordEncoder, mapperFacade, registeredUserMapper, roleMapper);
        this.visitorUser = visitorUser;
    }

    @Override
    public SystemUser login(Object credentials) {
        LoginForVisitorCredentials loginForVisitorCredentials = (LoginForVisitorCredentials) credentials;
        if (visitorUser.getSystemUser().getUsername().equals(
                loginForVisitorCredentials.getCredential())) {
            boolean passwordIsRight =
                    passwordEncoder.matches(loginForVisitorCredentials.getPassword(),
                            visitorUser.getSystemUser().getPassword());
            if (passwordIsRight) {
                return visitorUser.getSystemUser();
            }
        }
        return null;
    }


}
