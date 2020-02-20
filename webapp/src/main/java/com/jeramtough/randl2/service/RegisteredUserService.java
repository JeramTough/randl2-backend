package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.registereduser.UpdateRegisteredUserParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dto.RegisteredUserDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RegisteredUserService extends BaseService<RegisteredUser, RegisteredUserDto> {

    String verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params);

    String verifyPassword(VerifyPasswordParams params);

    RegisteredUserDto register();

    String removeRegisteredUser(Long uid);

    String updateRegisteredUser(UpdateRegisteredUserParams params);
}
