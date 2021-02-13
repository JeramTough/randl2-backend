package com.jeramtough.randl2.service.oauth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthScopeDetails;
import com.jeramtough.randl2.service.base.MyBaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
public interface OauthScopeDetailsService extends MyBaseService<OauthScopeDetails, OauthScopeDetailsDto> {

    List<OauthScopeDetailsDto> getClientScopeList(String scopeIds);

    String getClientScopesStr(String scopeIds);

    List<OauthScopeDetailsDto> getClientScopeListByResourceId(Long resourceId);
}
