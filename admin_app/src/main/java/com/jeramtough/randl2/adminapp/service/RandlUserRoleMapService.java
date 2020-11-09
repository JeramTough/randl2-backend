package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.dto.RandlUserRoleMapDto;
import com.jeramtough.randl2.common.model.entity.RandlUserRoleMap;
import com.jeramtough.randl2.common.model.params.user.SetUserRoleMapParams;
import com.jeramtough.randl2.common.service.MyBaseService;

/**
 * <pre>
 * Created on 2020/11/7 2:07
 * by @author WeiBoWen
 * </pre>
 */
public interface RandlUserRoleMapService extends MyBaseService<RandlUserRoleMap, RandlUserRoleMapDto> {
    String setUserRoleMap(SetUserRoleMapParams params);
}
