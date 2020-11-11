package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtcomponent.tree.adapter.OneTreeNodeAdapter;
import com.jeramtough.jtcomponent.tree.processor.DefaultTreeProcessor;
import com.jeramtough.jtcomponent.tree.structure.TreeNode;
import com.jeramtough.jtcomponent.tree.util.TreeNodeUtils;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserHolder;
import com.jeramtough.randl2.adminapp.component.userdetail.login.AdminUserLoginer;
import com.jeramtough.randl2.adminapp.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.adminapp.service.RandlModuleRoleMapService;
import com.jeramtough.randl2.common.component.moduletree.ModuleAuthDtoOneTreeNodeAdapter;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.params.user.UserCredentials;
import com.jeramtough.randl2.adminapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2020/10/3 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService, WithLogger {

    private final SourceSurfaceImageMapper surfaceImageMapper;
    private final RandlModuleRoleMapService randlModuleRoleMapService;
    private final AppSetting appSetting;

    @Autowired
    public LoginServiceImpl(WebApplicationContext wc,
                            SourceSurfaceImageMapper surfaceImageMapper,
                            RandlModuleRoleMapService randlModuleRoleMapService,
                            AppSetting appSetting) {
        super(wc);
        this.surfaceImageMapper = surfaceImageMapper;
        this.randlModuleRoleMapService = randlModuleRoleMapService;
        this.appSetting = appSetting;
    }

    @Override
    public SystemUserDto adminLogin(UserCredentials userCredentials) {
        BeanValidator.verifyParams(userCredentials);

        UserLoginer userLoginer = super.getWC().getBean(AdminUserLoginer.class);
        SystemUser systemUser;
        try {
            systemUser = userLoginer.login(userCredentials);
        }
        catch (ApiResponseException e) {
            getLogger().debug("登录失败! 登录参数%s 因为：%s", userCredentials.toString(), e.getMessage());
            throw e;
        }
        getLogger().verbose("登录成功，登录参数%s", userCredentials.toString());

        //到了这里，用户必须是登录成功了的
        UserHolder.afterLogin(systemUser);

        //processing SystemUserDto
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        String surfaceImage = surfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        //处理角色Dto
        List<RandlRole> randlRoleList = systemUser.getRoles();
        List<RandlRoleDto> randlRoleDtoList =
                randlRoleList.stream()
                             .map(randlRole -> getMapperFacade().map(randlRole, RandlRoleDto.class))
                             .collect(Collectors.toList());
        systemUserDto.setRoles(randlRoleDtoList);

        getLogger().verbose("开始获取用户模块授权信息");
        List<Long> roleIdList = systemUser.getRoles().parallelStream()
                                          .map(RandlRole::getFid)
                                          .collect(Collectors.toList());
        List<Map<String, Object>> moduleAuthList=
                randlModuleRoleMapService.getRandlModuleAuthTreeByAppIdAndRoleIds(appSetting
                .getDefaultAdminAppId(), roleIdList);
        systemUserDto.setModuleAuthList(moduleAuthList);

        return systemUserDto;
    }

    @Override
    public String adminLogout() {
        return null;
    }
}
