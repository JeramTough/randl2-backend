package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.params.registereduser.*;
import com.jeramtough.randl2.common.model.entity.RandlUser;
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
public interface RegisteredUserService extends BaseService<RandlUser,
        RandlUserDto>, UserDetailsService {

    Map<String, Object> verifyPhoneOrEmailForNew(VerifyPhoneOrEmailForNewParams params);

    Map<String, Object> verifyPhoneOrEmailByForget(VerifyPhoneOrEmailByForgetParams params);

    String verifyPassword(VerifyPasswordParams params);

    Object verifyPasswordByForget(VerifyPasswordParams params);

    RandlUserDto register(DoRegisterOrResetParams params);

    RandlUserDto reset(DoRegisterOrResetParams params);

    String removeRegisteredUser(Long uid);

    String updateRegisteredUser(UpdateRegisteredUserParams params);

    List<RandlUserDto> getRegisteredUsersByKeyword(String keyword);


}
