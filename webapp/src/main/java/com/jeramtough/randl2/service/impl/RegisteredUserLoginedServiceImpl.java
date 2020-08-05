package com.jeramtough.randl2.service.impl;

import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.model.error.ErrorU;
import com.jeramtough.randl2.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.model.params.registereduser.BindingPhoneOrEmailParams;
import com.jeramtough.randl2.model.params.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.model.params.verificationcode.VerifyVerificationCodeParams;
import com.jeramtough.randl2.component.userdetail.SystemUser;
import com.jeramtough.randl2.component.userdetail.UserHolder;
import com.jeramtough.randl2.component.verificationcode.sender.SendWay;
import com.jeramtough.randl2.model.entity.RegisteredUser;
import com.jeramtough.randl2.dao.mapper.RegisteredUserMapper;
import com.jeramtough.randl2.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.model.dto.RegisteredUserDto;
import com.jeramtough.randl2.service.PersonalInfoService;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import com.jeramtough.randl2.service.SurfaceImageService;
import com.jeramtough.randl2.service.VerificationCodeService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

/**
 * <pre>
 * Created on 2020/3/23 19:07
 * by @author JeramTough
 * </pre>
 */
@Service
public class RegisteredUserLoginedServiceImpl extends BaseServiceImpl<RegisteredUserMapper,
        RegisteredUser, RegisteredUserDto> implements RegisteredUserLoginedService {

    private final VerificationCodeService verificationCodeService;
    private final PersonalInfoService personalInfoService;
    private final SurfaceImageService surfaceImageService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisteredUserLoginedServiceImpl(
            WebApplicationContext wc,
            MapperFacade mapperFacade,
            VerificationCodeService verificationCodeService,
            PersonalInfoService personalInfoService,
            SurfaceImageService surfaceImageService,
            PasswordEncoder passwordEncoder) {
        super(wc, mapperFacade);
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
            throw new ApiResponseException(ErrorU.CODE_206.C);
        }

        if (!params.getNewPassword().equals(params.getRepeatedNewPassword())) {
            throw new ApiResponseException(ErrorU.CODE_101.C);
        }

        systemUser.setPassword(passwordEncoder.encode(params.getNewPassword()));
        UserHolder.afterLogin(systemUser);
        getBaseMapper().updatePassword(systemUser.getUid(), systemUser.getPassword());
        return "修改新密码成功";
    }

    @Override
    public String bindPhoneNumberOrEmailAddress(BindingPhoneOrEmailParams params) {
        BeanValidator.verifyDto(params);

        SystemUser systemUser = UserHolder.getSystemUser();
        SendWay sendWay = SendWay.getSendWay(params.getWay());
        switch (Objects.requireNonNull(sendWay)) {
            case PHONE:
                if (systemUser.getPhoneNumber() != null && systemUser.getPhoneNumber().equals(
                        params.getPhoneOrEmail())) {
                    throw new ApiResponseException(ErrorU.CODE_207.C);
                }
                if (getBaseMapper().selectByPhoneNumber(params.getPhoneOrEmail()) != null) {
                    throw new ApiResponseException(ErrorU.CODE_208.C);
                }
                verificationCodeService.verify(
                        new VerifyVerificationCodeParams(params.getVerificationCode(),
                                params.getPhoneOrEmail()));

                systemUser.setPhoneNumber(params.getPhoneOrEmail());
                getBaseMapper().updatePhoneNumber(systemUser.getUid(),
                        systemUser.getPhoneNumber());
                break;
            case EMAIL:
                if (systemUser.getEmailAddress() != null && systemUser.getEmailAddress().equals(
                        params.getPhoneOrEmail())) {
                    throw new ApiResponseException(ErrorU.CODE_207.C);
                }
                if (getBaseMapper().selectByEmailAddress(params.getPhoneOrEmail()) != null) {
                    throw new ApiResponseException(ErrorU.CODE_208.C);
                }
                verificationCodeService.verify(
                        new VerifyVerificationCodeParams(params.getVerificationCode(),
                                params.getPhoneOrEmail()));

                systemUser.setEmailAddress(params.getPhoneOrEmail());
                getBaseMapper().updateEmailAddress(systemUser.getUid(),
                        systemUser.getEmailAddress());
                break;
            default:
        }

        return String.format("绑定%s[%s]到账号%s成功！", sendWay.getName(), params.getPhoneOrEmail(),
                systemUser.getUsername());
    }

    //******************


}
