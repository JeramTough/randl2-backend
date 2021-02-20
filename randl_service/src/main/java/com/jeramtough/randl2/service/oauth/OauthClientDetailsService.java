package com.jeramtough.randl2.service.oauth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthClientDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthClientDetailsParams;
import com.jeramtough.randl2.service.base.MyBaseService;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
public interface OauthClientDetailsService extends MyBaseService<OauthClientDetails, OauthClientDetailsDto>
        , ClientDetailsService {

     String updateByParams(UpdateOauthClientDetailsParams params);

    OauthClientDetails getOneByClientId(String clientId);

    OauthClientDetailsDto getDtoByClientId(String clientId);

    String add(AddOauthClientDetailsParams params);

    OauthClientDetailsDto getOneByAppId(Long appId);

    List<OauthClientDetails> getListByResourceId(Long resourceId);
}
