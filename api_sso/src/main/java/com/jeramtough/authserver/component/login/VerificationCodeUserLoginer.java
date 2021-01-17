package com.jeramtough.authserver.component.login;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.login.user.BaseUserLoginer;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.login.LoginByVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.verificationcode.ConsumeVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.verificationcode.GetVerifiedResultParams;
import com.jeramtough.randl2.service.resource.VerificationCodeService;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * Created on 2020/11/23 0:10
 * by @author WeiBoWen
 * </pre>
 */
@Component("verificationCodeUserLoginer")
public class VerificationCodeUserLoginer extends BaseUserLoginer {

    private final VerificationCodeService verificationCodeService;
    private final RandlRoleService randlRoleService;

    @Autowired
    public VerificationCodeUserLoginer(PasswordEncoder passwordEncoder,
                                       RandlUserMapper randlUserMapper,
                                       VerificationCodeService verificationCodeService,
                                       MapperFacade mapperFacade,
                                       RandlRoleService randlRoleService) {
        super(passwordEncoder, randlUserMapper, mapperFacade);
        this.verificationCodeService = verificationCodeService;
        this.randlRoleService = randlRoleService;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        LoginByVerificationCodeParams params = (LoginByVerificationCodeParams) credentials;

        //先进行验证码消费
        ConsumeVerificationCodeParams consumeVerificationCodeParams = new ConsumeVerificationCodeParams();
        consumeVerificationCodeParams.setPhoneOrEmail(params.getPhoneOrEmail());
        consumeVerificationCodeParams.setVerificationCode(params.getVerificationCode());
        verificationCodeService.consumeVerificationCode(consumeVerificationCodeParams);

        //再获取验证码校验结果
        GetVerifiedResultParams getVerifiedResultParams = new GetVerifiedResultParams();
        getVerifiedResultParams.setPhoneOrEmail(params.getPhoneOrEmail());
        boolean mustBeSuccess = verificationCodeService.getVerifiedResult(getVerifiedResultParams);

        //流程走到这里，验证码校验一定已经成功了
        SystemUser systemUser = null;
        if (mustBeSuccess) {
            RandlUser randlUser = getRandlUserMapper().selectByPhoneNumberOrEmailAddress(params.getPhoneOrEmail());
            checkRandlUser(randlUser);

            //获取用户角色信息
            List<RandlRole> randlRoleList = randlRoleService.getRoleListByAppIdAndUid
                    (params.getAppId(), randlUser.getUid());

            systemUser = getMapperFacade().map(randlUser, SystemUser.class);
            systemUser.setRoles(randlRoleList);

        }
        Objects.requireNonNull(systemUser);
        return systemUser;
    }
}
