package com.jeramtough.randl2.component.login;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.oauth.PasswordGrantTypeParams;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
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
@Component("passwordGrantTypeByPasswordUserLoginer")
public class PasswordGrantTypeByPasswordUserLoginer extends PasswordUserLoginer {

    private final OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    public PasswordGrantTypeByPasswordUserLoginer(PasswordEncoder passwordEncoder,
                                                  RandlRoleMapper randlRoleMapper,
                                                  RandlUserMapper randlUserMapper,
                                                  MapperFacade mapperFacade,
                                                  RandlRoleService randlRoleService,
                                                  OauthClientDetailsService oauthClientDetailsService) {
        super(passwordEncoder, randlRoleMapper, randlUserMapper, mapperFacade, randlRoleService);
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        PasswordGrantTypeParams passwordGrantTypeParams = (PasswordGrantTypeParams) credentials;
        MyClientDetails myClientDetails =
                (MyClientDetails) oauthClientDetailsService.loadClientByClientId(
                        passwordGrantTypeParams.getClientId());

        LoginByPasswordParams loginByPasswordParams = new LoginByPasswordParams();

        //每一个Oauth应用都有必须绑定appId
        loginByPasswordParams.setAppId(myClientDetails.getOauthClientDetails().getAppId());
        loginByPasswordParams.setCredential(passwordGrantTypeParams.getUsername());
        loginByPasswordParams.setPassword(passwordGrantTypeParams.getPassword());

        return super.login(loginByPasswordParams);
    }
}
