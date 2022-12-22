package com.jeramtough.randl2.service.user.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.config.setting.AppSetting;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.service.randl.RandlModuleRoleMapService;
import com.jeramtough.randl2.service.resource.ResourceSurfaceImageService;
import com.jeramtough.randl2.service.user.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2021/8/12 下午4:03
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class SystemUserServiceImpl extends BaseServiceImpl implements SystemUserService,
        WithLogger {

    private final AppSetting appSetting;
    private final ResourceSurfaceImageService resourceSurfaceImageService;
    private final RandlModuleRoleMapService randlModuleRoleMapService;

    @Autowired
    public SystemUserServiceImpl(WebApplicationContext wc,
                                 AppSetting appSetting,
                                 ResourceSurfaceImageService resourceSurfaceImageService,
                                 RandlModuleRoleMapService randlModuleRoleMapService) {
        super(wc);
        this.appSetting = appSetting;
        this.resourceSurfaceImageService = resourceSurfaceImageService;
        this.randlModuleRoleMapService = randlModuleRoleMapService;
    }

    @Override
    public SystemUserDto getSystemUserDto(SystemUser systemUser) {
        Long appId = appSetting.getDefaultAppId();
        return getSystemUserDto(systemUser, appId);
    }

    @Override
    public SystemUserDto getSystemUserDto(SystemUser systemUser, Long appId) {
        //processing SystemUserDto
        SystemUserDto systemUserDto = BeanUtil.copyProperties(systemUser, SystemUserDto.class);
        String surfaceImage = resourceSurfaceImageService.getById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        //处理角色Dto
        List<RandlRole> randlRoleList = systemUser.getRoles();
        List<RandlRoleDto> randlRoleDtoList =
                randlRoleList.stream()
                             .map(randlRole -> BeanUtil.copyProperties(randlRole,
                                     RandlRoleDto.class))
                             .collect(Collectors.toList());
        systemUserDto.setRoles(randlRoleDtoList);

        getLogger().verbose("开始获取用户模块授权信息");
        List<Long> roleIdList = systemUser.getRoles().parallelStream()
                                          .map(RandlRole::getFid)
                                          .collect(Collectors.toList());
        List<Map<String, Object>> moduleAuthList =
                randlModuleRoleMapService.getRandlModuleAuthTreeByAppIdAndRoleIds(appId,
                        roleIdList);
        systemUserDto.setModuleAuthList(moduleAuthList);

        return systemUserDto;
    }
}
