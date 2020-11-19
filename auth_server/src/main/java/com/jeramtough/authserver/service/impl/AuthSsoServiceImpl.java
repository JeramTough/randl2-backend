package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.component.login.CredentialsUserLoginer;
import com.jeramtough.authserver.service.AuthSsoService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.UserHolder;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/11/15 1:05
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class AuthSsoServiceImpl extends BaseServiceImpl implements AuthSsoService {

    private final RandlAppMapper randlAppMapper;

    @Autowired
    public AuthSsoServiceImpl(WebApplicationContext webApplicationContext, RandlAppMapper randlAppMapper) {
        super(webApplicationContext);
        this.randlAppMapper = randlAppMapper;
    }

    @Override
    public SystemUserDto login(LoginCredentials params) {
        BeanValidator.verifyParams(params);

        if (randlAppMapper.selectById(params.getAppId()) == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "Randl应用");
        }

        CredentialsUserLoginer userLoginer = (CredentialsUserLoginer) getWC().getBean("credentialsUserLoginer");
        SystemUser systemUser = userLoginer.login(params);

        UserHolder.afterLogin(systemUser);

        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        return systemUserDto;
    }

    @Override
    public String logout() {
        UserHolder.clear();
        return "退出登录成功";
    }
}
