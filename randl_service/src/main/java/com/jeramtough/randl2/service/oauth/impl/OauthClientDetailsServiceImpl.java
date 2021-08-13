package com.jeramtough.randl2.service.oauth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.IdUtil;
import com.jeramtough.jtcomponent.utils.StringUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.mapper.OauthClientDetailsMapper;
import com.jeramtough.randl2.common.model.constant.AuthorizationGrantType;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.common.model.entity.OauthScopeDetails;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthClientDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthClientDetailsParams;
import com.jeramtough.randl2.common.util.OauthUtil;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
import com.jeramtough.randl2.service.oauth.OauthScopeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-11-16
 */
@Service("oauthClientDetailsServiceImpl")
public class OauthClientDetailsServiceImpl extends MyBaseServiceImpl<OauthClientDetailsMapper,
        OauthClientDetails, OauthClientDetailsDto>
        implements OauthClientDetailsService {

    private final OauthResourceDetailsService oauthResourceDetailsService;
    private final OauthScopeDetailsService oauthScopeDetailsService;

    @Autowired
    public OauthClientDetailsServiceImpl(WebApplicationContext wc,
                                         OauthResourceDetailsService oauthResourceDetailsService,
                                         OauthScopeDetailsService oauthScopeDetailsService) {
        super(wc);
        this.oauthResourceDetailsService = oauthResourceDetailsService;
        this.oauthScopeDetailsService = oauthScopeDetailsService;
    }

    @Override
    protected OauthClientDetailsDto toDto(OauthClientDetails oauthClientDetails) {
        OauthClientDetailsDto dto = toDto1(oauthClientDetails, OauthClientDetailsDto.class);
        return toDto2(dto);
    }

    protected OauthClientDetailsDto toDto2(OauthClientDetailsDto dto) {
        if (dto.getResourceIds() != null) {
            List<OauthResourceDetailsDto> resourceDetailsDtoList =
                    oauthResourceDetailsService.getResourceDetailsDtoList(
                            dto.getResourceIds());
            dto.setResources(resourceDetailsDtoList);

            Map<String, List<OauthScopeDetailsDto>> scopeMap = new HashMap<>(
                    resourceDetailsDtoList.size());

            resourceDetailsDtoList
                    .parallelStream()
                    .forEach(oauthResourceDetailsDto -> {
                        Long resourceId = oauthResourceDetailsDto.getFid();
                        List<OauthScopeDetailsDto> oauthScopeDetailsDtoList =
                                oauthScopeDetailsService.getClientScopeListByResourceId(
                                        resourceId);
                        scopeMap.put(resourceId.toString(), oauthScopeDetailsDtoList);
                    });
            dto.setScopeMap(scopeMap);
        }

        if (!StringUtils.isEmpty(dto.getAuthorizedGrantTypes())) {
            dto.setAuthorizedGrantTypeList(
                    StringUtil.splitByComma(dto.getAuthorizedGrantTypes()));
        }

        if (!StringUtils.isEmpty(dto.getWebServerRedirectUris())) {
            dto.setWebServerRedirectUriList(
                    StringUtil.splitByComma(dto.getWebServerRedirectUris()));
        }

        if (!StringUtils.isEmpty(dto.getClientSecret())) {
            dto.setClientSecret(dto.getClientSecret().replace("{noop}", ""));
        }

        return dto;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws
            ClientRegistrationException {
        //从数据库获取OAuth2客户端实体
        OauthClientDetails oauthClientDetails = getOneByClientId(clientId);
        Objects.requireNonNull(oauthClientDetails);

        List<OauthResourceDetails> oauthResourceDetailsList = new ArrayList<>();
        if (!StringUtils.isEmpty(oauthClientDetails.getResourceIds())) {
            List<String> ids = StringUtil.splitByComma(oauthClientDetails.getResourceIds());
            oauthResourceDetailsList = oauthResourceDetailsService.listByIds(ids);
        }

        List<Long> resourceIds = oauthResourceDetailsList
                .parallelStream()
                .map(OauthResourceDetails::getFid)
                .collect(Collectors.toList());

        QueryWrapper<OauthScopeDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("resource_id", resourceIds);
        queryWrapper.in("resource_id", resourceIds);
        List<OauthScopeDetails> oauthScopeDetailsList = oauthScopeDetailsService.list(
                queryWrapper);

        //转成Spring自带的
        ClientDetails clientDetails = new MyClientDetails(oauthClientDetails,
                oauthScopeDetailsList);

        return clientDetails;
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

    @Override
    public OauthClientDetailsDto getDtoByClientId(String clientId) {
        OauthClientDetailsDto oauthClientDetailsDto = getBaseMapper()
                .selectByClientIdOrAppId(clientId, null);

        if (oauthClientDetailsDto == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "clientId", "Oauth客户端");
        }

        return toDto2(oauthClientDetailsDto);
    }

    @Override
    public String add(AddOauthClientDetailsParams params) {
        BeanValidator.verifyParams(params);

        OauthClientDetails oauthClientDetails = getMapperFacade().map(params,
                OauthClientDetails.class);

        //设置clientId
        String clientId = IdUtil.getUUID();
        oauthClientDetails.setClientId(clientId);

        //设置密钥
        String clientSecret = OauthUtil.createClientSecret(clientId);
        oauthClientDetails.setClientSecret(clientSecret);

        save(oauthClientDetails);

        return "设置为OAuth2应用成功！";
    }

    @Override
    public OauthClientDetailsDto getOneByAppId(Long appId) {
        OauthClientDetailsDto oauthClientDetailsDto = getBaseMapper()
                .selectByClientIdOrAppId(null, appId);
        if (oauthClientDetailsDto == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "App应用");
        }
        return toDto2(oauthClientDetailsDto);
    }

    @Override
    public List<OauthClientDetails> getListByResourceId(Long resourceId) {
        QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_ids", resourceId)
                    .or()
                    .likeLeft("resource_ids", "," + resourceId)
                    .or()
                    .likeRight("resource_ids", resourceId + ",")
                    .or()
                    .like("resource_ids", "," + resourceId + ",");
        return getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public ClientDetails loadClientByClientIdAndGrantType(String clientId,
                                                          String grantType) throws
            ClientRegistrationException {
        Objects.requireNonNull(grantType);

        OauthClientDetails oauthClientDetails = getOneByClientId(clientId);
        Objects.requireNonNull(oauthClientDetails);

        List<OauthResourceDetails> oauthResourceDetailsList = new ArrayList<>();
        if (!StringUtils.isEmpty(oauthClientDetails.getResourceIds())) {
            List<String> ids = StringUtil.splitByComma(oauthClientDetails.getResourceIds());
            oauthResourceDetailsList = oauthResourceDetailsService.listByIds(ids);
        }

        List<Long> resourceIds = oauthResourceDetailsList
                .parallelStream()
                .map(OauthResourceDetails::getFid)
                .collect(Collectors.toList());

        QueryWrapper<OauthScopeDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("resource_id", resourceIds);

        //如果授权模式不是授权码模式和简化授权模式,
        //那么就只能得到不需要用户点同意的授权域
        AuthorizationGrantType authorizationGrantType =
                AuthorizationGrantType.getAuthorizationGrentType(grantType);
        if (!(authorizationGrantType == AuthorizationGrantType.AUTHORIZATION_CODE)
                &&!(authorizationGrantType == AuthorizationGrantType.IMPLICIT)) {
            queryWrapper.eq("is_required", 0);
        }

        List<OauthScopeDetails> oauthScopeDetailsList = oauthScopeDetailsService.list(
                queryWrapper);

        ClientDetails clientDetails = new MyClientDetails(oauthClientDetails,
                oauthScopeDetailsList);
        return clientDetails;
    }

    @Override
    public String updateByParams(UpdateOauthClientDetailsParams params) {
        BeanValidator.verifyParams(params);

        //是否需要更新密钥
        if (params.getIisUpdateSecret()) {
            //设置密钥
            String clientSecret = OauthUtil.createClientSecret(params.getClientId());
            params.setClientSecret(clientSecret);
        }


        return super.updateByParams(params);
    }
}
