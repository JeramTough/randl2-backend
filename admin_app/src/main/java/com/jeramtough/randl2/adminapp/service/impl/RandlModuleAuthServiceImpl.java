package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.service.RandlModuleAuthService;
import com.jeramtough.randl2.common.mapper.*;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.RandlModuleRoleMapDto;
import com.jeramtough.randl2.common.model.entity.*;
import com.jeramtough.randl2.common.model.error.ErrorU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class RandlModuleAuthServiceImpl extends BaseDtoServiceImpl<RandlModuleRoleMapMapper,
        RandlModuleRoleMap, RandlModuleRoleMapDto>
        implements RandlModuleAuthService {

    private final RandlUserRoleMapMapper randlUserRoleMapMapper;
    private final RandlModuleRoleMapMapper randlModuleRoleMapMapper;
    private final RandlApiMapper randlApiMapper;
    private final RandlModuleMapper randlModuleMapper;
    private final RandlModuleApiMapMapper randlModuleApiMapMapper;

    @Autowired
    public RandlModuleAuthServiceImpl(WebApplicationContext wc,
                                      RandlUserRoleMapMapper randlUserRoleMapMapper,
                                      RandlModuleRoleMapMapper randlModuleRoleMapMapper,
                                      RandlApiMapper randlApiMapper,
                                      RandlModuleMapper randlModuleMapper,
                                      RandlModuleApiMapMapper randlModuleApiMapMapper) {
        super(wc);
        this.randlUserRoleMapMapper = randlUserRoleMapMapper;
        this.randlModuleRoleMapMapper = randlModuleRoleMapMapper;
        this.randlApiMapper = randlApiMapper;
        this.randlModuleMapper = randlModuleMapper;
        this.randlModuleApiMapMapper = randlModuleApiMapMapper;
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
        //这个app下所有的模块
        List<RandlModule> moduleList = randlModuleMapper.selectList(
                new QueryWrapper<RandlModule>().eq("app_id", appId));

        //这个app下所有的接口
        List<RandlApi> apiList = randlApiMapper.selectList(new QueryWrapper<RandlApi>().eq("app_id",
                appId));

        //以appId为键值，保存接口
        Map<Long, RandlApi> idKeyApiMap;
        idKeyApiMap = apiList.stream().collect(Collectors.toMap(RandlApi::getFid, randlApi -> randlApi));

        //这个app下的所有模块与接口的映射关系
        List<RandlModuleApiMap> moduleApiMapList = randlModuleApiMapMapper.selectList(
                new QueryWrapper<RandlModuleApiMap>().eq("app_id",
                        appId));

        //以模块Id为键值，保存每个模块下面所有的接口
        Map<Long, List<RandlApi>> moduleIdKeyApisMap = new HashMap<>(10);
        for (RandlModuleApiMap randlModuleApiMap : moduleApiMapList) {
            List<RandlApi> apis = moduleIdKeyApisMap.get(randlModuleApiMap.getModuleId());
            if (apis == null) {
                apis = new ArrayList<>();
            }
            apis.add(idKeyApiMap.get(randlModuleApiMap.getApiId()));
            moduleIdKeyApisMap.put(randlModuleApiMap.getModuleId(), apis);
        }

        //这个角色的授权情况
        List<RandlModuleRoleMap> moduleRoleMapList = randlModuleRoleMapMapper.selectList(
                new QueryWrapper<RandlModuleRoleMap>().eq(
                        "app_id", appId).eq("role_id", roleId));
        //模块Id为键值的RandlModuleRoleMap集合 -模块授权情况
        Map<Long, RandlModuleRoleMap> moduleIdKeyModuleRoleMap = new HashMap<>(moduleRoleMapList.size());
        //接口Id为键值的RandlModuleRoleMap集合 -接口授权情况
        Map<Long, RandlModuleRoleMap> apiIdKeyModuleRoleMap = new HashMap<>(moduleRoleMapList.size());
        for (RandlModuleRoleMap randlModuleRoleMap : moduleRoleMapList) {
            //如果是模块授权的话
            if (randlModuleRoleMap.getModuleId() != null && randlModuleRoleMap.getApiId() == null) {
                moduleIdKeyModuleRoleMap.put(randlModuleRoleMap.getModuleId(), randlModuleRoleMap);
            }
            //如果是接口授权的话
            if (randlModuleRoleMap.getModuleId() != null && randlModuleRoleMap.getApiId() != null) {
                apiIdKeyModuleRoleMap.put(randlModuleRoleMap.getApiId(), randlModuleRoleMap);
            }
        }

        List<RandlModuleAuthDto> randlModuleAuthDtoList = new ArrayList<>();
        //开始组装模块授权对象
        for (RandlModule randlModule : moduleList) {
            //模块授权
            RandlModuleAuthDto moduleAuthDto = toRandlModuleAuthDto(moduleIdKeyModuleRoleMap, randlModule, roleId);
            randlModuleAuthDtoList.add(moduleAuthDto);

            //接口授权
            List<RandlApi> apis = moduleIdKeyApisMap.get(randlModule.getFid());
            if (apis != null) {
                for (RandlApi api : apis) {
                    RandlModuleAuthDto moduleAuthDtoForApi = toRandlModuleAuthDtoForApi(apiIdKeyModuleRoleMap,
                            randlModule, api, roleId);
                    randlModuleAuthDtoList.add(moduleAuthDtoForApi);
                }
            }
        }
        return randlModuleAuthDtoList;
    }

    //*****************************

    private RandlModuleAuthDto toRandlModuleAuthDto(
            Map<Long, RandlModuleRoleMap> moduleIdKeyModuleRoleMap,
            RandlModule randlModule, Long roleId) {

        RandlModuleAuthDto randlModuleAuthDto = getMapperFacade().map(randlModule, RandlModuleAuthDto.class);
        randlModuleAuthDto.setMid(randlModule.getFid());
        randlModuleAuthDto.setParentModuleId(randlModule.getParentId());
        randlModuleAuthDto.setModuleType(0);

        //超级管理员角色，直接获得所有权限
        if (SuperAdmin.ROLE_ID.equals(roleId)) {
            randlModuleAuthDto.setIsAuth(1);
        }
        else {
            if (moduleIdKeyModuleRoleMap.get(randlModule.getFid()) != null) {
                randlModuleAuthDto.setIsAuth(1);
            }
            else {
                randlModuleAuthDto.setIsAuth(0);
            }
        }

        return randlModuleAuthDto;
    }

    private RandlModuleAuthDto toRandlModuleAuthDtoForApi(
            Map<Long, RandlModuleRoleMap> apiIdKeyModuleRoleMap,
            RandlModule randlModule, RandlApi randlApi, Long roleId) {

        RandlModuleAuthDto randlModuleAuthDto = getMapperFacade().map(randlApi, RandlModuleAuthDto.class);
        //防止接口id和模块Id相同
        randlModuleAuthDto.setMid(randlApi.getFid() - (randlApi.getFid() * 2));
        randlModuleAuthDto.setParentModuleId(randlModule.getFid());
        randlModuleAuthDto.setLevel(randlModule.getLevel() + 1);
        randlModuleAuthDto.setIcon(randlModule.getIcon());
        randlModuleAuthDto.setModuleType(1);
        randlModuleAuthDto.setIsAble(randlModule.getIsAble());

        //超级管理员角色，直接获得所有权限
        if (SuperAdmin.ROLE_ID.equals(roleId)) {
            randlModuleAuthDto.setIsAuth(1);
        }
        else {
            if (apiIdKeyModuleRoleMap.get(randlApi.getFid()) != null) {
                randlModuleAuthDto.setIsAuth(1);
            }
            else {
                randlModuleAuthDto.setIsAuth(0);
            }
        }
        return randlModuleAuthDto;
    }
}
