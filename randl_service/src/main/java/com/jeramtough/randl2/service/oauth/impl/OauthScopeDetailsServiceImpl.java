package com.jeramtough.randl2.service.oauth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.StringUtil;
import com.jeramtough.randl2.common.mapper.OauthScopeDetailsMapper;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthScopeDetails;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthScopeDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2021/2/2 0:52
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class OauthScopeDetailsServiceImpl extends MyBaseServiceImpl<OauthScopeDetailsMapper, OauthScopeDetails,
        OauthScopeDetailsDto> implements OauthScopeDetailsService {

    public OauthScopeDetailsServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    protected OauthScopeDetailsDto toDto(OauthScopeDetails oauthScopeDetails) {
        return toDto1(oauthScopeDetails, OauthScopeDetailsDto.class);
    }

    @Override
    public List<OauthScopeDetailsDto> getClientScopeList(String scopeIds) {
        List<Long> ids = StringUtil.splitByComma(scopeIds)
                                   .stream()
                                   .map(Long::parseLong)
                                   .collect(Collectors.toList());
        List<OauthScopeDetails> entityList = listByIds(ids);
        return getDtoList(entityList);
    }

    @Override
    public String getClientScopesStr(String scopeIds) {
        List<OauthScopeDetailsDto> scopeDetailsDtoList=getClientScopeList(scopeIds);
        if (scopeDetailsDtoList.size()==0){
            return null;
        }

        StringBuilder sb=new StringBuilder();
        scopeDetailsDtoList
                .parallelStream()
                .map(OauthScopeDetailsDto::getScopeExpression)
                .forEach(scopeExpression -> sb.append(scopeExpression).append(","));

        String clientScopesStr=sb.toString();
        if (clientScopesStr.length()>0){
            clientScopesStr=clientScopesStr.substring(0,clientScopesStr.length()-1);
        }
        return clientScopesStr;
    }

    @Override
    public List<OauthScopeDetailsDto> getClientScopeListByResourceId(Long resourceId) {
        QueryWrapper<OauthScopeDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_id", resourceId);

        return getDtoListForAsyn(getBaseMapper().selectList(queryWrapper));
    }
}
