package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.adminuser.RegisterRandlUserParams;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
public interface RandlUserService extends BaseDtoService<RandlUser, RandlUserDto> {


    RandlUserDto add(RegisterRandlUserParams params);

    RandlUserDto getRandlUserByKeyword(String keyword);

    String removeRandUserById(Long uid);
}
