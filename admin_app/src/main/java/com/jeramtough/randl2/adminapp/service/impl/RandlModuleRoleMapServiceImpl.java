package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.service.RandlModuleRoleMapService;
import com.jeramtough.randl2.adminapp.service.RandlRoleService;
import com.jeramtough.randl2.common.component.moduletree.ModuleAuthDtoOneTreeNodeAdapter;
import com.jeramtough.randl2.common.mapper.*;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.RandlModuleRoleMapDto;
import com.jeramtough.randl2.common.model.entity.*;
import com.jeramtough.randl2.common.model.error.ErrorS;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.module.SetModuleRoleMapParams;
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
        implements RandlModuleRoleMapService, WithLogger {

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
            //"roleIds 数量不能等于0"
            throw new ApiResponseException(ErrorS.CODE_2.C,"获取权限");
        }

        //这个角色的授权情况
        List<RandlModuleRoleMap> moduleRoleMapList = randlModuleRoleMapMapper.selectList(
                new QueryWrapper<RandlModuleRoleMap>()
                        .eq("app_id", appId)
                        .in("role_id", roleIds));

        //模块Id为键值的RandlModuleRoleMap集合 -模块授权情况
        Map<Long, RandlModuleRoleMap> moduleIdKeyModuleRoleMap =
                moduleRoleMapList.parallelStream().distinct().collect(Collectors.toMap(RandlModuleRoleMap::getModuleId,
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

    @Override
    public List<Map<String, Object>> getRandlModuleAuthTreeByAppIdAndRoleId(Long appId, Long roleId) {
        return getRandlModuleAuthTreeByAppIdAndRoleIds(appId, Collections.singletonList(roleId));
    }

    @Override
    public List<Map<String, Object>> getRandlModuleAuthTreeByAppIdAndRoleIds(Long appId, List<Long> roleIds) {
        List<RandlModuleAuthDto> moduleAuthDtoList = getRandlModuleAuthDtosByAppIdAndRoleIds(appId, roleIds);

        getLogger().verbose("开始处理用户模块授权信息作为树形结构");
        List<OneTreeNodeAdapter<RandlModuleAuthDto>> oneTreeNodeAdapterList = new ArrayList<>();
        for (RandlModuleAuthDto randlModuleAuthDto : moduleAuthDtoList) {
            OneTreeNodeAdapter<RandlModuleAuthDto> adapter = new ModuleAuthDtoOneTreeNodeAdapter(
                    randlModuleAuthDto);
            oneTreeNodeAdapterList.add(adapter);
        }
        TreeNode rootTreeNode = new DefaultTreeProcessor().processing(oneTreeNodeAdapterList);

        Map<String, Object> treeNodeMap = TreeNodeUtils.toTreeMap(rootTreeNode);
        getLogger().debug("处理为树形结构并且过滤出菜单模块完成: " + rootTreeNode.getDetail());

        List<Map<String, Object>> list = (List<Map<String, Object>>) treeNodeMap.get("children");
        return list;
    }

    @Override
    public String setModuleRoleMap(SetModuleRoleMapParams params) {
        BeanValidator.verifyParams(params);

        //先删后加
        getBaseMapper().delete(new QueryWrapper<RandlModuleRoleMap>()
                .eq("app_id", params.getAppId())
                .eq("role_id", params.getRoleId()));
        params.getModuleIds().parallelStream().forEach(moduleId -> {
            RandlModuleRoleMap entity = new RandlModuleRoleMap();
            entity.setAppId(params.getAppId());
            entity.setRoleId(params.getRoleId());
            entity.setModuleId(moduleId);
            getBaseMapper().insert(entity);
        });
        return "设置成功！";
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
