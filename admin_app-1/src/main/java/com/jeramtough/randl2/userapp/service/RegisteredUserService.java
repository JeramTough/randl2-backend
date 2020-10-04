package com.jeramtough.randl2.userapp.service;

import com.jeramtough.randl2.common.model.params.registereduser.*;
import com.jeramtough.randl2.common.model.entity.RegisteredUser;
import com.jeramtough.randl2.common.model.dto.RegisteredUserDto;
import com.jeramtough.randl2.common.service.BaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RegisteredUserService extends BaseService<RegisteredUser,
        RegisteredUserDto>, UserDetailsService {

    Map<String, Object> verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params);

    Map<String, Object> verifyPhoneOrEmailByForget(VerifyPhoneOrEmailByForgetParams params);

    String verifyPassword(VerifyPasswordParams params);

    Object verifyPasswordByForget(VerifyPasswordParams params);

    RegisteredUserDto register(DoRegisterOrResetParams params);

    RegisteredUserDto reset(DoRegisterOrResetParams params);

    String removeRegisteredUser(Long uid);

    String updateRegisteredUser(UpdateRegisteredUserParams params);

    List<RegisteredUserDto> getRegisteredUsersByKeyword(String keyword);


}