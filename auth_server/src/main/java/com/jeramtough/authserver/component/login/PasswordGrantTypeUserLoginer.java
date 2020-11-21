package com.jeramtough.authserver.component.login;

import com.jeramtough.authserver.service.MyClientDetailsService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.authserver.component.oauth2.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.params.login.LoginCredentialsParams;
import com.jeramtough.randl2.common.model.params.oauth.PasswordGrantTypeParams;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/17 11:29
 * by @author WeiBoWen
 * </pre>
 */
@Component("passwordGrantTypeUserLoginer")
public class PasswordGrantTypeUserLoginer extends CredentialsUserLoginer{

    private final MyClientDetailsService myClientDetailsService;

    @Autowired
    public PasswordGrantTypeUserLoginer(PasswordEncoder passwordEncoder,
                                        RandlRoleMapper randlRoleMapper,
                                        RandlUserMapper randlUserMapper,
                                        MapperFacade mapperFacade,
                                        RandlRoleService randlRoleService,
                                        MyClientDetailsService myClientDetailsService) {
        super(passwordEncoder, randlRoleMapper, randlUserMapper, mapperFacade, randlRoleService);
        this.myClientDetailsService = myClientDetailsService;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        PasswordGrantTypeParams passwordGrantTypeParams= (PasswordGrantTypeParams) credentials;
        MyClientDetails myClientDetails=
                (MyClientDetails) myClientDetailsService.loadClientByClientId(passwordGrantTypeParams.getClientId());

        LoginCredentialsParams loginCredentialsParams =new LoginCredentialsParams();

        //每一个Oauth应用都有必须绑定appId
        loginCredentialsParams.setAppId(myClientDetails.getOauthClientDetails().getAppId());
        loginCredentialsParams.setCredentials(passwordGrantTypeParams.getUsername());
        loginCredentialsParams.setPassword(passwordGrantTypeParams.getPassword());

        return super.login(loginCredentialsParams);
    }
}
