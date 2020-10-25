package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.BaseDtoService;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.adminuser.ConditionUserParams;
import com.jeramtough.randl2.common.model.params.adminuser.RegisterRandlUserParams;
import com.jeramtough.randl2.common.model.params.adminuser.UpdateRandlUserParams;

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

    String removeRandUserById(Long uid);

    PageDto<RandlUserDto> pageByCondition(
            QueryByPageParams queryByPageParams, ConditionUserParams params);

    String updateRandlUser(UpdateRandlUserParams params);
}
