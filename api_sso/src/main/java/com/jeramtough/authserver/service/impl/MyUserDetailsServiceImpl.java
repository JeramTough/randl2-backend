package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.component.login.PasswordGrantTypeByPasswordUserLoginer;
import com.jeramtough.authserver.component.login.PasswordGrantTypeByVerificationCodeUserLoginer;
import com.jeramtough.authserver.component.login.UidUserLoginer;
import com.jeramtough.authserver.service.MyUserDetailsService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.login.user.UserLoginer;
import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import com.jeramtough.randl2.common.model.error.ErrorU;
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

        if (params.getUsername() != null && params.getPassword() != null) {
            return loadUserByPassword(params);
        }
        else if (params.getPhoneOrEmail() != null && params.getVerificationCode() != null) {
            return loadUserByVerificationCode(params);
        }
        else {
            throw new ApiResponseException(ErrorU.CODE_802.C);
        }
    }

    @Override
    public MyUserDetails loadUserById(Long uid) {
        UserLoginer userLoginer = getWC().getBean(UidUserLoginer.class);
        return getUserDetails(userLoginer, uid);
    }

//*********************

    public UserDetails loadUserByPassword(PasswordGrantTypeParams params) throws UsernameNotFoundException {
        UserLoginer userLoginer = getWC().getBean(PasswordGrantTypeByPasswordUserLoginer.class);
        return getUserDetails(userLoginer, params);
    }

    public UserDetails loadUserByVerificationCode(PasswordGrantTypeParams params) throws
            UsernameNotFoundException {
        UserLoginer userLoginer = getWC().getBean(PasswordGrantTypeByVerificationCodeUserLoginer.class);
        return getUserDetails(userLoginer, params);
    }


    private MyUserDetails getUserDetails(UserLoginer userLoginer,
                                         Object params) {
        SystemUser systemUser = userLoginer.login(params);
        MyUserDetails myUserDetails = new MyUserDetails(systemUser);
        return myUserDetails;
    }


}
