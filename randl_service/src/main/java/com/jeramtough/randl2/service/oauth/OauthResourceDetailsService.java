package com.jeramtough.randl2.service.oauth;

import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.service.base.MyBaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2021-01-31
 */
public interface OauthResourceDetailsService extends MyBaseService<OauthResourceDetails, OauthResourceDetailsDto> {

    List<OauthResourceDetailsDto> getResourceDetailsDtoList(String resourceIds);

}
