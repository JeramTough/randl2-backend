package com.jeramtough.randl2.service.registeruser;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.params.registereduser.BindingPhoneOrEmailParams;
import com.jeramtough.randl2.common.model.params.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;

/**
 * <pre>
 *     必须是使用令牌才能访问的权限接口
 * Created on 2020/3/23 19:06
 * by @author JeramTough
 * </pre>
 */
public interface RegisteredUserLoginedService extends BaseDtoService<RandlUser,
        RandlUserDto> {

    RandlPersonalInfoDto getPersonalInfo();

    String updatePersonalInfo(UpdatePersonalInfoParams params);

    String updateSurfaceImageByBase64(UploadSurfaceImageParams params);

    String resetPassword(ResetPasswordParams params);

    String bindPhoneNumberOrEmailAddress(BindingPhoneOrEmailParams params);
}
