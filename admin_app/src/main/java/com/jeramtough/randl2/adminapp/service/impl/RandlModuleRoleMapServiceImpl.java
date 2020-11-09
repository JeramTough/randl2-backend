package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.service.RandlModuleRoleMapService;
import com.jeramtough.randl2.adminapp.service.RandlRoleService;
import com.jeramtough.randl2.common.mapper.*;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.RandlModuleRoleMapDto;
import com.jeramtough.randl2.common.model.entity.*;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.service.impl.MyBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Service
public class RandlModuleRoleMapServiceImpl extends MyBaseServiceImpl<RandlModuleRoleMapMapper,
        RandlModuleRoleMap, RandlModuleRoleMapDto>
        implements RandlModuleRoleMapService {

    private final RandlUserRoleMapMapper randlUserRoleMapMapper;
    private final RandlModuleRoleMapMapper randlModuleRoleMapMapper;
    private final RandlModuleMapper randlModuleMapper;
    private final RandlAppMapper randlAppMapper;

    private final RandlRoleService randlRoleService;

    @Autowired
    public RandlModuleRoleMapServiceImpl(WebApplicationContext wc,
                                         RandlUserRoleMapMapper randlUserRoleMapMapper,
                                         RandlModuleRoleMapMapper randlModuleRoleMapMapper,
                                         RandlModuleMapper randlModuleMapper,
                                         RandlAppMapper randlAppMapper,
                                         RandlRoleService randlRoleService) {
        super(wc);
        this.randlUserRoleMapMapper = randlUserRoleMapMapper;
        this.randlModuleRoleMapMapper = randlModuleRoleMapMapper;
        this.randlModuleMapper = randlModuleMapper;
        this.randlAppMapper = randlAppMapper;
        this.randlRoleService = randlRoleService;
    }

    @Override
    protected RandlModuleRoleMapDto toDto(RandlModuleRoleMap randlModuleRoleMap) {
        return toDto1(randlModuleRoleMap, RandlModuleRoleMapDto.class);
    }

    @Override
    public List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndUid(Long appId, Long uid) {
        //如果是超级管理员
        if (SuperAdmin.UID.equals(uid)) {
            return getRandlModuleAuthDtosByAppIdAndRoleId(appId, SuperAdmin.ROLE_ID);
        }

        RandlRole randlRole = randlUserRoleMapMapper.selectOneRandlRoleByAppIdAndUid(appId, uid);
        if (randlRole == null) {
            throw new ApiResponseException(ErrorU.CODE_502.C);
        }
        return getRandlModuleAuthDtosByAppIdAndRoleId(appId, randlRole.getFid());
    }

    @Override
    public List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndRoleId(Long appId, Long roleId) {
        return getRandlModuleAuthDtosByAppIdAndRoleIds(appId, Collections.singletonList(roleId));
    }

    @Override
    public List<RandlModuleAuthDto> getRandlModuleAuthDtosByAppIdAndRoleIds(Long appId, List<Long> roleIds) {
        if (randlAppMapper.selectById(appId) == null) {
            throw new ApiResponseBeanException(ErrorU.CODE_10.C, "appId", "应用");
        }

        //这个app下所有的模块
        List<RandlModule> moduleList = randlModuleMapper.selectList(
                new QueryWrapper<RandlModule>().eq("app_id", appId));

        if (CollectionUtils.isEmpty(roleIds)) {
            throw new IllegalStateException("roleIds 数量不能等于0");
        }

        //这个角色的授权情况
        List<RandlModuleRoleMap> moduleRoleMapList = randlModuleRoleMapMapper.selectList(
                new QueryWrapper<RandlModuleRoleMap>()
                        .eq("app_id", appId)
                        .in("role_id", roleIds));

        //模块Id为键值的RandlModuleRoleMap集合 -模块授权情况
        Map<Long, RandlModuleRoleMap> moduleIdKeyModuleRoleMap =
                moduleRoleMapList.parallelStream().collect(Collectors.toMap(RandlModuleRoleMap::getModuleId,
                        randlModuleRoleMap -> randlModuleRoleMap));

        List<RandlModuleAuthDto> randlModuleAuthDtoList = new ArrayList<>();
        //开始组装模块授权对象
        for (RandlModule randlModule : moduleList) {
            //模块授权
            RandlModuleAuthDto moduleAuthDto = toRandlModuleAuthDto(moduleIdKeyModuleRoleMap, randlModule,
                    roleIds);
            randlModuleAuthDtoList.add(moduleAuthDto);
        }
        return randlModuleAuthDtoList;
    }

    //*****************************

    private RandlModuleAuthDto toRandlModuleAuthDto(
            Map<Long, RandlModuleRoleMap> moduleIdKeyModuleRoleMap,
            RandlModule randlModule, List<Long> roleIds) {

        RandlModuleAuthDto randlModuleAuthDto = getMapperFacade().map(randlModule, RandlModuleAuthDto.class);
        randlModuleAuthDto.setMid(randlModule.getFid());
        randlModuleAuthDto.setParentModuleId(randlModule.getParentId());

        //超级管理员角色，直接获得所有权限
        if (roleIds.contains(SuperAdmin.ROLE_ID)) {
            randlModuleAuthDto.setIsAuth(1);
        }
        else {
            //如果有这个映射关系则授权
            if (moduleIdKeyModuleRoleMap.get(randlModule.getFid()) != null) {
                randlModuleAuthDto.setIsAuth(1);
            }
            else {
                randlModuleAuthDto.setIsAuth(0);
            }
        }

        return randlModuleAuthDto;
    }


}
