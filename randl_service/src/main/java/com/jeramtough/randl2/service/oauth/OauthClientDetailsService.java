package com.jeramtough.randl2.service.oauth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.service.base.MyBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface OauthClientDetailsService extends MyBaseService<OauthClientDetails, OauthClientDetailsDto> {

    OauthClientDetails getOneByClientId(String clientId);

}
