package com.jeramtough.randl2.service;

import com.jeramtough.randl2.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.model.params.registereduser.BindingPhoneOrEmailParams;
import com.jeramtough.randl2.model.params.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.model.entity.RegisteredUser;
import com.jeramtough.randl2.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.model.dto.RegisteredUserDto;

/**
 * <pre>
 *     必须是使用令牌才能访问的权限接口
 * Created on 2020/3/23 19:06
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserLoginedService extends BaseService<RegisteredUser,
        RegisteredUserDto> {

    PersonalInfoDto getPersonalInfo();

    String updatePersonalInfo(UpdatePersonalInfoParams params);

    String updateSurfaceImageByBase64(UploadSurfaceImageParams params);

    String resetPassword(ResetPasswordParams params);

    String bindPhoneNumberOrEmailAddress(BindingPhoneOrEmailParams params);
}
