package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.bean.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.bean.registereduser.LoginByVerificationCodeCredentials;
import com.jeramtough.randl2.bean.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.bean.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.bean.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.bean.verificationcode.VerifyVerificationCodeParams;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.component.userdetail.login.RegisteredUserByPhoneOrEmailLoginer;
import com.jeramtough.randl2.component.userdetail.login.RegisteredUserLoginer;
import com.jeramtough.randl2.component.userdetail.login.RegisteredUserUidLoginer;
import com.jeramtough.randl2.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.config.security.AuthTokenConfig;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dao.mapper.SurfaceImageMapper;
import com.jeramtough.randl2.dto.PersonalInfoDto;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import com.jeramtough.randl2.dto.SystemUserDto;
import com.jeramtough.randl2.service.PersonalInfoService;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import com.jeramtough.randl2.service.SurfaceImageService;
import com.jeramtough.randl2.service.VerificationCodeService;
import com.jeramtough.randl2.util.JwtTokenUtil;
import ma.glasnost.orika.MapperFacade;
import org.codehaus.janino.IClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final SurfaceImageMapper surfaceImageMapper;
    private final AuthTokenConfig authTokenConfig;
    private final VerificationCodeService verificationCodeService;
    private final PersonalInfoService personalInfoService;
    private final SurfaceImageService surfaceImageService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisteredUserLoginedServiceImpl(
            WebApplicationContext wc,
            MapperFacade mapperFacade,
            SurfaceImageMapper surfaceImageMapper,
            AuthTokenConfig authTokenConfig,
            VerificationCodeService verificationCodeService,
            PersonalInfoService personalInfoService,
            SurfaceImageService surfaceImageService,
            PasswordEncoder passwordEncoder) {
        super(wc, mapperFacade);

        this.surfaceImageMapper = surfaceImageMapper;
        this.authTokenConfig = authTokenConfig;
        this.verificationCodeService = verificationCodeService;
        this.personalInfoService = personalInfoService;
        this.surfaceImageService = surfaceImageService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected RegisteredUserDto toDto(RegisteredUser registeredUser) {
        RegisteredUserDto registeredUserDto = getMapperFacade().map(registeredUser,
                RegisteredUserDto.class);
        return registeredUserDto;
    }

    @Override
    public Map<String, Object> loginByPassword(LoginByPasswordCredentials credentials) {
        BeanValidator.verifyDto(credentials);

        UserLoginer userLoginer = super.getWC().getBean(RegisteredUserLoginer.class);
        SystemUser systemUser = userLoginer.login(credentials);

        if (systemUser == null) {
            throw new ApiResponseException(10002);
        }
        return processingLoginResult(systemUser);
    }

    @Override
    public Map<String, Object> loginByVerificationCode(
            LoginByVerificationCodeCredentials credentials) {
        BeanValidator.verifyDto(credentials);

        VerifyVerificationCodeParams params = new VerifyVerificationCodeParams();
        params.setPhoneOrEmail(credentials.getCredential());
        params.setVerificationCode(credentials.getVerificationCode());
        verificationCodeService.verify(params);

        //执行到这里，说明验证码已经校验成功
        UserLoginer userLoginer = super.getWC().getBean(
                RegisteredUserByPhoneOrEmailLoginer.class);
        SystemUser systemUser = userLoginer.login(credentials.getCredential());

        if (systemUser == null) {
            throw new ApiResponseException(10003);
        }
        return processingLoginResult(systemUser);
    }

    @Override
    public PersonalInfoDto getPersonalInfo() {
        SystemUser systemUser = UserHolder.getSystemUser();
        return personalInfoService.getPersonalInfoDtoByUidWithoutSurfaceImage(
                systemUser.getUid());
    }

    @Override
    public String updatePersonalInfo(UpdatePersonalInfoParams params) {
        Long uid = UserHolder.getSystemUser().getUid();
        params.setUid(uid);
        personalInfoService.updatePersonalInfo(params);
        return "更新成功";
    }

    @Override
    public String updateSurfaceImageByBase64(UploadSurfaceImageParams params) {
        Long uid = UserHolder.getSystemUser().getUid();
        UpdateSurfaceImageParams updateSurfaceImageParams = new UpdateSurfaceImageParams();
        updateSurfaceImageParams.setSurfaceImage(params.getSurfaceImage());
        updateSurfaceImageParams.setUid(uid);
        surfaceImageService.updateSurfaceImageByBase64(updateSurfaceImageParams);
        return "更新成功";
    }

    @Override
    public String resetPassword(ResetPasswordParams params) {
        BeanValidator.verifyDto(params);
        SystemUser systemUser = UserHolder.getSystemUser();
        boolean isPass = passwordEncoder.matches(params.getOldPassword(),
                systemUser.getPassword());
        if (!isPass) {
            throw new ApiResponseException(10004);
        }

        if (!params.getNewPassword().equals(params.getRepeatedNewPassword())) {
            throw new ApiResponseException(10005);
        }

        systemUser.setPassword(passwordEncoder.encode(params.getNewPassword()));
        UserHolder.afterLogin(systemUser);
        getBaseMapper().updatePassword(systemUser.getUid(), systemUser.getPassword());
        return "更新成功";
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
            UserLoginer userLoginer = super.getWC().getBean(RegisteredUserUidLoginer.class);
            SystemUser systemUser = userLoginer.login(uid);
            UserHolder.afterLogin(systemUser);
        }
        else {
            String errorMessage = taskResult.getMessage();
            throw new ApiResponseException(10001, errorMessage);
        }

    }

    //*************

    private Map<String, Object> processingLoginResult(SystemUser systemUser) {
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

}
