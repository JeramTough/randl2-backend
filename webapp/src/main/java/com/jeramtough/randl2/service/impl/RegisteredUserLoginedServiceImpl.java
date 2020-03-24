package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials1;
import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials2;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.component.userdetail.login.RegisteredUserLoginer;
import com.jeramtough.randl2.component.userdetail.login.RegisteredUserTokenLoginer;
import com.jeramtough.randl2.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.component.verificationcode.RedisVerificationCodeHolder;
import com.jeramtough.randl2.config.security.AuthTokenConfig;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import com.jeramtough.randl2.dto.SystemUserDto;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import com.jeramtough.randl2.util.JwtTokenUtil;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/3/23 19:07
 * by @author JeramTough
 * </pre>
 */
@Service
public class RegisteredUserLoginedServiceImpl extends BaseServiceImpl<RegisteredUserMapper,
        RegisteredUser, RegisteredUserDto> implements RegisteredUserLoginedService {

    private RedisVerificationCodeHolder verificationCodeHolder;
    private SurfaceImageMapper surfaceImageMapper;
    private AuthTokenConfig authTokenConfig;


    @Autowired
    public RegisteredUserLoginedServiceImpl(
            WebApplicationContext wc,
            MapperFacade mapperFacade,
            RedisVerificationCodeHolder verificationCodeHolder,
            SurfaceImageMapper surfaceImageMapper,
            AuthTokenConfig authTokenConfig) {
        super(wc, mapperFacade);

        this.verificationCodeHolder = verificationCodeHolder;
        this.surfaceImageMapper = surfaceImageMapper;
        this.authTokenConfig = authTokenConfig;
    }

    @Override
    protected RegisteredUserDto toDto(RegisteredUser registeredUser) {
        RegisteredUserDto registeredUserDto = getMapperFacade().map(registeredUser,
                RegisteredUserDto.class);
        return registeredUserDto;
    }

    @Override
    public Map loginByPassword(RegisteredUserCredentials1 credentials) {

        BeanValidator.verifyDto(credentials);
        UserLoginer userLoginer = super.getWC().getBean(RegisteredUserLoginer.class);
        SystemUser systemUser = userLoginer.login(credentials);

        if (systemUser == null) {
            throw new ApiResponseException(1001);
        }

        //processing SystemUserDto
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        String surfaceImage = surfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        //processing token
        String token = JwtTokenUtil.createToken(systemUser, authTokenConfig);

        Map<String, Object> map = new HashMap<>();
        map.put("systemUser", systemUserDto);
        map.put("token", token);
        return map;
    }

    @Override
    public Map<String, Object> loginByVerificationCode(
            RegisteredUserCredentials2 credentials) {
        return null;
    }

    @Override
    public void loginByExistingToken(String token) {

        if (token == null) {
            throw new ApiResponseException(10000);
        }

        TaskResult taskResult = JwtTokenUtil.verifyToken(token,
                authTokenConfig).getTaskResult();
        if (taskResult.isSuccessful()) {
            long uid = taskResult.getLongPayload("uid");
            UserLoginer userLoginer = super.getWC().getBean(RegisteredUserTokenLoginer.class);
            SystemUser systemUser = userLoginer.login(uid);
            UserHolder.afterLogin(systemUser);
        }
        else {
            String errorMessage = taskResult.getMessage();
            throw new ApiResponseException(10001, errorMessage);
        }

    }


}
