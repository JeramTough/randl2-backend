package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.jeramtough.randl2.common.model.params.registereduser.LoginForVisitorCredentials;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.VisitorUser;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
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
            RandlUserMapper randlUserMapper, RandlRoleMapper randlRoleMapper,
            VisitorUser visitorUser) {
        super(passwordEncoder, mapperFacade, randlUserMapper, randlRoleMapper);
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
