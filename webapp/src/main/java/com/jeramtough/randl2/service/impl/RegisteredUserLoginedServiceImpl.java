package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.bean.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.bean.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.bean.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.dto.PersonalInfoDto;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import com.jeramtough.randl2.service.PersonalInfoService;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import com.jeramtough.randl2.service.SurfaceImageService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/3/23 19:07
 * by @author JeramTough
 * </pre>
 */
@Service
public class RegisteredUserLoginedServiceImpl extends BaseServiceImpl<RegisteredUserMapper,
        RegisteredUser, RegisteredUserDto> implements RegisteredUserLoginedService {

    private final PersonalInfoService personalInfoService;
    private final SurfaceImageService surfaceImageService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisteredUserLoginedServiceImpl(
            WebApplicationContext wc,
            MapperFacade mapperFacade,
            PersonalInfoService personalInfoService,
            SurfaceImageService surfaceImageService,
            PasswordEncoder passwordEncoder) {
        super(wc, mapperFacade);

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
        return "修改新密码成功";
    }



}
