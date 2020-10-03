package com.jeramtough.randl2.adminapp.service;

import com.jeramtough.randl2.common.model.entity.RandlPersonalInfo;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlPersonalInfoService extends BaseService<RandlPersonalInfo, RandlPersonalInfoDto> {

    RandlPersonalInfoDto getPersonalInfoByUid(Long uid);

    RandlPersonalInfoDto getPersonalInfoDtoByUidWithoutSurfaceImage(Long uid);

    String updatePersonalInfo(UpdatePersonalInfoParams params);
}
