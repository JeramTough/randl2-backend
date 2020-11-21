package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.component.login.PasswordGrantTypeUserLoginer;
import com.jeramtough.authserver.component.login.UidUserLoginer;
import com.jeramtough.authserver.service.MyUserDetailsService;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.login.user.UserLoginer;
import com.jeramtough.randl2.common.component.userdetail.MyUserDetails;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.model.params.oauth.PasswordGrantTypeParams;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import com.jeramtough.randl2.service.randl.RandlUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/11/17 8:36
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class MyUserDetailsServiceImpl extends BaseServiceImpl implements MyUserDetailsService {

    private final RandlUserService randlUserService;
    private final RandlRoleService randlRoleService;

    @Autowired
    public MyUserDetailsServiceImpl(WebApplicationContext wc, RandlUserService randlUserService,
                                    RandlRoleService randlRoleService) {
        super(wc);
        this.randlUserService = randlUserService;
        this.randlRoleService = randlRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        PasswordGrantTypeParams params = getWC().getBean(PasswordGrantTypeParams.class);
        BeanValidator.verifyParams(params);

        UserLoginer userLoginer = getWC().getBean(PasswordGrantTypeUserLoginer.class);


        return getUserDetails(userLoginer,params);
    }

    @Override
    public MyUserDetails loadUserById(Long uid) {
        UserLoginer userLoginer = getWC().getBean(UidUserLoginer.class);
        return getUserDetails(userLoginer,uid);
    }

//*********************

    private MyUserDetails getUserDetails(UserLoginer userLoginer,
                                         Object params) {
        SystemUser systemUser = userLoginer.login(params);
        MyUserDetails myUserDetails = new MyUserDetails(systemUser);
        return myUserDetails;
    }


}
