package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.component.login.PasswordUserLoginer;
import com.jeramtough.authserver.component.login.VerificationCodeUserLoginer;
import com.jeramtough.authserver.service.SsoService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.login.user.UserLoginer;
import com.jeramtough.randl2.common.component.token.MyJwtTokenHolder;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.model.dto.OauthTokenDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.login.LoginByVerificationCodeParams;
import com.jeramtough.randl2.service.other.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/15 1:05
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class SsoServiceImpl extends BaseServiceImpl implements SsoService {

    private final RandlAppMapper randlAppMapper;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public SsoServiceImpl(WebApplicationContext webApplicationContext, RandlAppMapper randlAppMapper,
                          VerificationCodeService verificationCodeService) {
        super(webApplicationContext);
        this.randlAppMapper = randlAppMapper;
        this.verificationCodeService = verificationCodeService;
    }

    @Override
    public Map<String, Object> login(LoginByPasswordParams params) {
        BeanValidator.verifyParams(params);

        if (randlAppMapper.selectById(params.getAppId()) == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "Randl应用");
        }

        UserLoginer userLoginer = (PasswordUserLoginer) getWC().getBean("passwordUserLoginer");
        return login(userLoginer, params);
    }

    @Override
    public String logout() {
        UserHolder.clear();
        return "退出登录成功";
    }

    @Override
    public Map<String, Object> loginByVerificationCode(LoginByVerificationCodeParams params) {
        BeanValidator.verifyParams(params);

        if (randlAppMapper.selectById(params.getAppId()) == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "Randl应用");
        }

        UserLoginer userLoginer = (VerificationCodeUserLoginer) getWC().getBean("verificationCodeUserLoginer");
        return login(userLoginer, params);
    }

    //*******************

    private Map<String, Object> login(UserLoginer userLoginer, Object params) {
        SystemUser systemUser = userLoginer.login(params);

        UserHolder.afterLogin(systemUser);

        OauthTokenDto oauthTokenDto = getWC().getBean(MyJwtTokenHolder.class).getToken(systemUser);

        Map<String, Object> resultMap = new HashMap<>(2);
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        resultMap.put("systemUser", systemUserDto);
        resultMap.put("tokenBody", oauthTokenDto);
        return resultMap;
    }
}
