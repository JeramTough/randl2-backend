package com.jeramtough.randl2.service.randl;

import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.jtweb.service.JtBaseService;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;
import com.jeramtough.randl2.common.model.entity.RandlPersonalInfo;
import com.jeramtough.randl2.common.model.params.personalinfo.AddPersonalInfoParams;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
public interface RandlPersonalInfoService extends
        JtBaseService<RandlPersonalInfo, RandlPersonalInfoDto> {

    RandlPersonalInfoDto getPersonalInfoByUid(Long uid);

    RandlPersonalInfoDto getPersonalInfoDtoByUidWithoutSurfaceImage(Long uid);

    String updatePersonalInfo(UpdatePersonalInfoParams params);

    String addPersonalInfo(AddPersonalInfoParams params);

    PageDto<Map<String, Object>> getRandlPersonalInfoDtoListByPage(
            QueryByPageParams queryByPageParams);
}
