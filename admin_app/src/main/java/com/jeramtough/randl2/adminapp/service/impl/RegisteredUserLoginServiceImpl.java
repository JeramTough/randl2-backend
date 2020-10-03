package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.common.model.params.registereduser.LoginByVerificationCodeCredentials;
import com.jeramtough.randl2.common.model.params.registereduser.LoginForVisitorCredentials;
import com.jeramtough.randl2.common.model.params.verificationcode.VerifyVerificationCodeParams;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserHolder;
import com.jeramtough.randl2.adminapp.component.userdetail.login.*;
import com.jeramtough.randl2.adminapp.config.security.AuthTokenConfig;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.adminapp.service.RegisteredUserLoginService;
import com.jeramtough.randl2.common.service.VerificationCodeService;
import com.jeramtough.randl2.common.util.JwtTokenUtil;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/4/25 22:47
 * by @author JeramTough
 * </pre>
 */
@Service
public class RegisteredUserLoginServiceImpl implements RegisteredUserLoginService {

    private final WebApplicationContext webApplicationContext;
    private final AuthTokenConfig authTokenConfig;
    private final MapperFacade mapperFacade;
    private final SourceSurfaceImageMapper sourceSurfaceImageMapper;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public RegisteredUserLoginServiceImpl(
            WebApplicationContext webApplicationContext,
            AuthTokenConfig authTokenConfig, MapperFacade mapperFacade,
            SourceSurfaceImageMapper sourceSurfaceImageMapper,
            VerificationCodeService verificationCodeService) {
        this.webApplicationContext = webApplicationContext;
        this.authTokenConfig = authTokenConfig;
        this.mapperFacade = mapperFacade;
        this.sourceSurfaceImageMapper = sourceSurfaceImageMapper;
        this.verificationCodeService = verificationCodeService;
    }


    @Override
    public void loginByExistingToken(String token) {
        if (token == null) {
            throw new ApiResponseException(ErrorU.CODE_3.C);
        }

        TaskResult taskResult = JwtTokenUtil.verifyToken(token,
                authTokenConfig.getSigningKey(), AuthTokenConfig.ISSUER).getTaskResult();
        if (taskResult.isSuccessful()) {
            long uid = taskResult.getLongPayload("uid");
            UserLoginer userLoginer = getWC().getBean(RegisteredUserUidLoginer.class);
            SystemUser systemUser = userLoginer.login(uid);
            UserHolder.afterLogin(systemUser);
        }
        else {
            String errorMessage = taskResult.getMessage();
            throw new ApiResponseException(ErrorU.CODE_4.C, errorMessage);
        }

    }

    @Override
    public Map<String, Object> loginByPassword(LoginByPasswordCredentials credentials) {
        BeanValidator.verifyParams(credentials);

        UserLoginer userLoginer = getWC().getBean(RegisteredUserLoginer.class);
        SystemUser systemUser = userLoginer.login(credentials);

        if (systemUser == null) {
            throw new ApiResponseException(10002);
        }
        return processingLoginResult(systemUser);
    }

    @Override
    public Map<String, Object> loginByVerificationCode(
            LoginByVerificationCodeCredentials credentials) {
        BeanValidator.verifyParams(credentials);

        VerifyVerificationCodeParams params = new VerifyVerificationCodeParams();
        params.setPhoneOrEmail(credentials.getCredential());
        params.setVerificationCode(credentials.getVerificationCode());
        verificationCodeService.verify(params);

        //执行到这里，说明验证码已经校验成功
        UserLoginer userLoginer = getWC().getBean(
                RegisteredUserByPhoneOrEmailLoginer.class);
        SystemUser systemUser = userLoginer.login(credentials.getCredential());

        if (systemUser == null) {
            throw new ApiResponseException(ErrorU.CODE_303.C);
        }
        return processingLoginResult(systemUser);
    }

    @Override
    public Map<String, Object> loginForVisitor(LoginForVisitorCredentials credentials) {
        BeanValidator.verifyParams(credentials);

//        UserLoginer userLoginer = getWC().getBean(VisitorUserLoginer.class);
        UserLoginer userLoginer = null;
        SystemUser systemUser = userLoginer.login(credentials);

        if (systemUser == null) {
            throw new ApiResponseException(ErrorU.CODE_302.C);
        }
        return processingLoginResult(systemUser);
    }

    protected WebApplicationContext getWC() {
        return webApplicationContext;
    }

    //*************

    private Map<String, Object> processingLoginResult(SystemUser systemUser) {
        //processing SystemUserDto
      /*  SystemUserDto systemUserDto = mapperFacade.map(systemUser, SystemUserDto.class);
        String surfaceImage = sourceSurfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);*/

        //processing token
       /* String token = JwtTokenUtil.createToken(systemUser, authTokenConfig.getSigningKey(),
                AuthTokenConfig.ISSUER,
                authTokenConfig.getJwtTokenValidity());

        Map<String, Object> map = new HashMap<>(2);
        map.put("systemUser", systemUserDto);
        map.put("token", token);*/
        return null;
    }
}
