package com.jeramtough.randl2.service.randl;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.model.dto.RandlUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.user.ConditionUserParams;
import com.jeramtough.randl2.common.model.params.user.RegisterRandlUserParams;
import com.jeramtough.randl2.common.model.params.user.UpdateRandlUserParams;
import com.jeramtough.randl2.service.base.MyBaseService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
public interface RandlUserService extends MyBaseService<RandlUser, RandlUserDto> {


    RandlUserDto add(RegisterRandlUserParams params);

    String removeRandUserById(Long uid);

    PageDto<RandlUserDto> pageByCondition(
            QueryByPageParams queryByPageParams, ConditionUserParams params);

    String updateRandlUser(UpdateRandlUserParams params);

    RandlUserDto getWithRoleByCredentialsAndAppId(String credentials, Long appId);

    RandlUser getByCredentials(String credentials);
}
