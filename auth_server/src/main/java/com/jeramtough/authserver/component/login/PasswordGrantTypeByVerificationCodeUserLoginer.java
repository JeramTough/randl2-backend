package com.jeramtough.authserver.component.login;

import com.jeramtough.authserver.service.MyClientDetailsService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.params.login.LoginByVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.oauth.PasswordGrantTypeParams;
import com.jeramtough.randl2.service.other.VerificationCodeService;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/17 11:29
 * by @author WeiBoWen
 * </pre>
 */
@Component("passwordGrantTypeByVerificationCodeUserLoginer")
public class PasswordGrantTypeByVerificationCodeUserLoginer extends VerificationCodeUserLoginer {

    private final MyClientDetailsService myClientDetailsService;

    public PasswordGrantTypeByVerificationCodeUserLoginer(PasswordEncoder passwordEncoder,
                                                          RandlUserMapper randlUserMapper,
                                                          VerificationCodeService verificationCodeService,
                                                          MapperFacade mapperFacade,
                                                          RandlRoleService randlRoleService,
                                                          MyClientDetailsService myClientDetailsService) {
        super(passwordEncoder, randlUserMapper, verificationCodeService, mapperFacade, randlRoleService);
        this.myClientDetailsService = myClientDetailsService;
    }


    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        PasswordGrantTypeParams passwordGrantTypeParams = (PasswordGrantTypeParams) credentials;
        MyClientDetails myClientDetails =
                (MyClientDetails) myClientDetailsService.loadClientByClientId(
                        passwordGrantTypeParams.getClientId());

        LoginByVerificationCodeParams loginByVerificationCodeParams=new LoginByVerificationCodeParams();

        //每一个Oauth应用都有必须绑定appId
        loginByVerificationCodeParams.setAppId(myClientDetails.getOauthClientDetails().getAppId());
        loginByVerificationCodeParams.setPhoneOrEmail(passwordGrantTypeParams.getPhoneOrEmail());
        loginByVerificationCodeParams.setVerificationCode(passwordGrantTypeParams.getVerificationCode());

        return super.login(loginByVerificationCodeParams);
    }
}
