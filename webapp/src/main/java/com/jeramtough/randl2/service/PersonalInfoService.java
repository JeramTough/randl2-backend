package com.jeramtough.randl2.service;

import com.jeramtough.randl2.bean.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.dao.entity.PersonalInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.dto.PersonalInfoDto;

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
