package com.jeramtough.randl2.userapp.service;

import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.common.model.entity.PersonalInfo;
import com.jeramtough.randl2.common.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.common.service.BaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface PersonalInfoService extends BaseService<PersonalInfo,PersonalInfoDto> {

    PersonalInfoDto getPersonalInfoByUid(Long uid);

    PersonalInfoDto getPersonalInfoDtoByUidWithoutSurfaceImage(Long uid);

    String updatePersonalInfo(UpdatePersonalInfoParams params);
}
