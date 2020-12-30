package com.jeramtough.randl2.service.oauth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.randl2.common.mapper.OauthClientDetailsMapper;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.service.base.MyBaseService;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-11-16
 */
@Service
public class OauthClientDetailsServiceImpl extends MyBaseServiceImpl<OauthClientDetailsMapper,
        OauthClientDetails, OauthClientDetailsDto>
        implements OauthClientDetailsService {

    public OauthClientDetailsServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    protected OauthClientDetailsDto toDto(OauthClientDetails oauthClientDetails) {
        return toDto1(oauthClientDetails, OauthClientDetailsDto.class);
    }

    @Override
    public OauthClientDetails getOneByClientId(String clientId) {
        QueryWrapper queryWrapper = new QueryWrapper<OauthClientDetails>();
        queryWrapper.eq("client_id", clientId);
        OauthClientDetails oauthClientDetails = getBaseMapper().selectOne(queryWrapper);
        if (oauthClientDetails == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "clitentId", "Oauth客户端");
        }
        return oauthClientDetails;
    }

}
