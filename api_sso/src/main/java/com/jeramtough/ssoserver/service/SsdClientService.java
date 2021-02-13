package com.jeramtough.ssoserver.service;

import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;

import java.util.List;

/**
 * <pre>
 * Created on 2021/1/30 22:25
 * by @author WeiBoWen
 * </pre>
 */
public interface SsdClientService {

    OauthClientDetailsDto getClientByTempClientId(String tempClientId);

}
