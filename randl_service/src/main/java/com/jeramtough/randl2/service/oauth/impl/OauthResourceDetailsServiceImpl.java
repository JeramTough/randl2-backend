package com.jeramtough.randl2.service.oauth.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.StringUtil;
import com.jeramtough.randl2.common.mapper.OauthResourceDetailsMapper;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
import com.jeramtough.randl2.service.randl.RandlAppService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2021-01-31
 */
@Service
public class OauthResourceDetailsServiceImpl extends MyBaseServiceImpl<OauthResourceDetailsMapper,
        OauthResourceDetails, OauthResourceDetailsDto> implements OauthResourceDetailsService {

    private final RandlAppService randlAppService;

    public OauthResourceDetailsServiceImpl(WebApplicationContext wc,
                                           RandlAppService randlAppService) {
        super(wc);
        this.randlAppService = randlAppService;
    }

    @Override
    protected OauthResourceDetailsDto toDto(OauthResourceDetails oauthResourceDetails) {
        OauthResourceDetailsDto dto = toDto1(oauthResourceDetails, OauthResourceDetailsDto.class);
        RandlApp randlApp = randlAppService.getById(dto.getAppId());
        Objects.requireNonNull(randlApp);
        getMapperFacade().map(randlApp, dto);
        return dto;
    }

    @Override
    public List<OauthResourceDetailsDto> getResourceDetailsDtoList(String resourceIds) {
        List<String> resourceIdList = StringUtil.splitByComma(resourceIds);

        QueryWrapper<OauthResourceDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("fid", resourceIdList);

        List<OauthResourceDetails> oauthResourceDetailsList = getBaseMapper().selectList(queryWrapper);

        return getDtoListForAsyn(oauthResourceDetailsList);
    }
}
