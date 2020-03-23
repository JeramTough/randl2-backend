package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials;
import com.jeramtough.randl2.dao.entity.RegisteredUser;
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

    Map loginByPassword(RegisteredUserCredentials credentials);

    /**
     * 登录根据之前已经存在的token
     */
    void loginByExistingToken(String token);

}
