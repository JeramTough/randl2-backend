package com.jeramtough.ssoserver.service.impl;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.oauth2.Oauth2RequestHolder;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import com.jeramtough.randl2.service.oauth.OauthScopeDetailsService;
import com.jeramtough.ssoserver.service.SsdClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2021/1/30 22:26
 * by @author WeiBoWen
 * </pre>
 */

@Service
public class SsdClientServiceImpl implements SsdClientService {

    private final Oauth2RequestHolder oauth2RequestHolder;
    private final OauthClientDetailsService oauthClientDetailsService;
    private final OauthScopeDetailsService oauthScopeDetailsService;

    @Autowired
    public SsdClientServiceImpl(
            Oauth2RequestHolder oauth2RequestHolder,
            OauthClientDetailsService oauthClientDetailsService,
            OauthScopeDetailsService oauthScopeDetailsService) {
        this.oauth2RequestHolder = oauth2RequestHolder;
        this.oauthClientDetailsService = oauthClientDetailsService;
        this.oauthScopeDetailsService = oauthScopeDetailsService;
    }

    @Override
    public OauthClientDetailsDto getClientByTempClientId(String tempClientId) {
        String clientId = oauth2RequestHolder.getClientId(tempClientId);
        if (clientId == null) {
            throw new ApiResponseException(ErrorU.CODE_803.C);
        }

        OauthClientDetailsDto dto = oauthClientDetailsService.getDtoByClientId(clientId);

        //因为是用户资源，把非用户资源的过滤掉
        Map<String, List<OauthScopeDetailsDto>> scopeMap=dto.getScopeMap();
        List<OauthResourceDetailsDto> resources=dto.getResources()
           .parallelStream()
           .filter(oauthResourceDetailsDto -> {
               if (oauthResourceDetailsDto.getIsUserResource()!=1){
                   scopeMap.remove(oauthResourceDetailsDto.getFid().toString());
                   return false;
               }
               return true;
           }).collect(Collectors.toList());
        dto.setResources(resources);

        return dto;
    }

}
