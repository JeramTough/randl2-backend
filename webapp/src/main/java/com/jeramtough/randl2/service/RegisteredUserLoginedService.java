package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.bean.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.bean.registereduser.LoginByVerificationCodeCredentials;
import com.jeramtough.randl2.bean.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.bean.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dto.PersonalInfoDto;
import com.jeramtough.randl2.dto.RegisteredUserDto;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/3/23 19:06
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserLoginedService extends BaseService<RegisteredUser,
        RegisteredUserDto> {
    /**
     * 登录根据之前已经存在的token
     */
    void loginByExistingToken(String token);

    Map<String, Object> loginByPassword(LoginByPasswordCredentials credentials);

    Map<String, Object> loginByVerificationCode(LoginByVerificationCodeCredentials credentials);

    PersonalInfoDto getPersonalInfo();

    String updatePersonalInfo(UpdatePersonalInfoParams params);

    String updateSurfaceImageByBase64(UploadSurfaceImageParams params);

    String resetPassword(ResetPasswordParams params);
}
