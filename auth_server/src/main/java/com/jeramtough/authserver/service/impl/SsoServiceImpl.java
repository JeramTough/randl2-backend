package com.jeramtough.authserver.service.impl;

import com.jeramtough.authserver.component.login.CredentialsUserLoginer;
import com.jeramtough.authserver.service.SsoService;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.token.MyJwtTokenHolder;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.userdetail.UserHolder;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.model.dto.OauthTokenDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.LoginCredentialsParams;
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

    @Autowired
    public SsoServiceImpl(WebApplicationContext webApplicationContext, RandlAppMapper randlAppMapper) {
        super(webApplicationContext);
        this.randlAppMapper = randlAppMapper;
    }

    @Override
    public Map<String, Object> login(LoginCredentialsParams params) {
        BeanValidator.verifyParams(params);

        if (randlAppMapper.selectById(params.getAppId()) == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "Randl应用");
        }

        CredentialsUserLoginer userLoginer = (CredentialsUserLoginer) getWC().getBean("credentialsUserLoginer");
        SystemUser systemUser = userLoginer.login(params);

        UserHolder.afterLogin(systemUser);

        OauthTokenDto oauthTokenDto = getWC().getBean(MyJwtTokenHolder.class).getToken(systemUser);

        Map<String, Object> resultMap = new HashMap<>(2);
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        resultMap.put("systemUser", systemUserDto);
        resultMap.put("tokenBody", oauthTokenDto);
        return resultMap;
    }

    @Override
    public String logout() {
        UserHolder.clear();
        return "退出登录成功";
    }
}
