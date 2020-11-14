package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.user.UserCredentials;
import com.jeramtough.randl2.service.base.MyBaseService;

/**
 * <pre>
 * Created on 2020/11/14 21:12
 * by @author WeiBoWen
 * </pre>
 */
public interface LoginService  {

    SystemUserDto adminLogin(UserCredentials userCredentials);

    String adminLogout();


}
