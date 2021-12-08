package com.jeramtough.randl2.service.oauth.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.utils.StringUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.randl2.common.mapper.OauthResourceDetailsMapper;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.dto.OauthScopeDetailsDto;
import com.jeramtough.randl2.common.model.entity.OauthClientDetails;
import com.jeramtough.randl2.common.model.entity.OauthResourceDetails;
import com.jeramtough.randl2.common.model.entity.OauthScopeDetails;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthResourceDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthScopeDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthResourceDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthScopeDetailsParams;
import com.jeramtough.randl2.service.base.impl.MyBaseServiceImpl;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
import com.jeramtough.randl2.service.oauth.OauthScopeDetailsService;
import com.jeramtough.randl2.service.randl.RandlAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final OauthScopeDetailsService oauthScopeDetailsService;

    @Autowired
    public OauthResourceDetailsServiceImpl(WebApplicationContext wc,
                                           RandlAppService randlAppService,
                                           OauthScopeDetailsService oauthScopeDetailsService) {
        super(wc);
        this.randlAppService = randlAppService;
        this.oauthScopeDetailsService = oauthScopeDetailsService;
    }

    @Override
    protected OauthResourceDetailsDto toDto(OauthResourceDetails oauthResourceDetails) {
        OauthResourceDetailsDto dto = super.toDto(oauthResourceDetails);
        RandlApp randlApp = randlAppService.getById(dto.getAppId());
        Objects.requireNonNull(randlApp);
        BeanUtil.copyProperties(randlApp, dto);
        //因为fid参数被覆盖了
        dto.setFid(oauthResourceDetails.getFid());

        List<OauthScopeDetailsDto> scopeDetailsDtoList =
                oauthScopeDetailsService.getClientScopeListByResourceId(oauthResourceDetails.getFid());
        dto.setScopeDetailsList(scopeDetailsDtoList);

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

    @Override
    public String add(AddOauthResourceDetailsParams params) {
        BeanValidator.verifyParams(params);

        OauthResourceDetails oauthResourceDetails = BeanUtil.copyProperties(params, OauthResourceDetails.class);

        save(oauthResourceDetails);
        Long resourceId = oauthResourceDetails.getFid();
        oauthScopeDetailsService.addList(resourceId, params.getScopeDetailsList());

        return "添加成功！";
    }

    @Override
    public String update(UpdateOauthResourceDetailsParams params) {
        updateByParams(params);

        List<OauthScopeDetailsDto> entityList =
                oauthScopeDetailsService.getClientScopeListByResourceId(params.getFid());

        List<UpdateOauthScopeDetailsParams> updateOauthScopeDetailsParamsList = new ArrayList<>();
        List<AddOauthScopeDetailsParams> addOauthScopeDetailsParamsList = new ArrayList<>();
        List<OauthScopeDetailsDto> deleteOauthScopeDetailsList = new ArrayList<>(entityList);

        params.getScopeDetailsList()
              .parallelStream()
              .forEach(scopeDetailsParams -> {
                  if (scopeDetailsParams.getFid() == null) {
                      AddOauthScopeDetailsParams addOauthScopeDetailsParams =
                              BeanUtil.copyProperties(scopeDetailsParams, AddOauthScopeDetailsParams.class);
                      addOauthScopeDetailsParamsList.add(addOauthScopeDetailsParams);
                  }
                  else {
                      updateOauthScopeDetailsParamsList.add(scopeDetailsParams);
                  }

                  //不包含的是要被删掉的
                  AtomicReference<OauthScopeDetailsDto> deleteOauthScopeDetails = new AtomicReference<>();
                  boolean isContained = entityList
                          .parallelStream()
                          .anyMatch(oauthScopeDetailsDto -> {
                                      boolean is = (oauthScopeDetailsDto.getFid().equals(
                                              scopeDetailsParams.getFid()));
                                      if (is) {
                                          deleteOauthScopeDetails.set(oauthScopeDetailsDto);
                                      }
                                      return is;
                                  }
                          );
                  if (isContained) {
                      deleteOauthScopeDetailsList.remove(deleteOauthScopeDetails.get());
                  }
              });

        oauthScopeDetailsService.addList(params.getFid(), addOauthScopeDetailsParamsList);
        oauthScopeDetailsService.updateList(updateOauthScopeDetailsParamsList);

        List<Long> removeIds = deleteOauthScopeDetailsList
                .parallelStream()
                .map(OauthScopeDetailsDto::getFid)
                .collect(Collectors.toList());
        if (!removeIds.isEmpty()){
            oauthScopeDetailsService.removeByIds(removeIds);
        }

        return "更新成功！";
    }

    @Override
    public OauthResourceDetailsDto getOneByAppId(Long appId) {
        QueryWrapper<OauthResourceDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_Id", appId);

        OauthResourceDetails entity = getBaseMapper().selectOne(queryWrapper);
        if (entity == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "App应用");
        }
        OauthResourceDetailsDto dto = toDto(entity);
        return dto;
    }

    @Override
    public String removeById(Long fid) {
//        super.removeById(fid);

        //删除oauth_scope_details的数据
        QueryWrapper<OauthScopeDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_id", fid);

        try {
            //因为可能删除的数量为0，不管心是否成功，所以try起来
            oauthScopeDetailsService.remove(queryWrapper);
        }catch (ApiResponseException ignored){}


        //删除oauth_client_details表内的数据
        OauthClientDetailsService oauthClientDetailsService = getWC().getBean(OauthClientDetailsServiceImpl.class);
        List<OauthClientDetails> oauthClientDetailsList = oauthClientDetailsService.getListByResourceId(fid);
        oauthClientDetailsList
                .parallelStream()
                .forEach(oauthClientDetails -> {
                    List<String> ids = StringUtil.splitByComma(oauthClientDetails.getResourceIds());
                    ids.remove(fid.toString());
                    String newResourceIds = StringUtil.appendByComma(ids);
                    oauthClientDetails.setResourceIds(newResourceIds);

                    oauthClientDetailsService.updateById(oauthClientDetails);
                });


        return "删除成功";
    }
}
