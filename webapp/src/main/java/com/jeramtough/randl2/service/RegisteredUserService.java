package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.registereduser.*;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
import com.jeramtough.randl2.dto.RegisteredUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RegisteredUserService extends BaseService<RegisteredUser,
        RegisteredUserDto> , UserDetailsService {

    String verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params);

    String verifyPhoneOrEmailByForget(VerifyPhoneOrEmailByForgetParams params);

    String verifyPassword(VerifyPasswordParams params);

    RegisteredUserDto register();

    RegisteredUserDto resetPassword();

    String removeRegisteredUser(Long uid);

    String updateRegisteredUser(UpdateRegisteredUserParams params);

    List<RegisteredUserDto> getRegisteredUsersByKeyword(String keyword);

    String loginByPassword(RegisteredUserCredentials credentials);
}
