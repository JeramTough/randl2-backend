package com.jeramtough.randl2.userapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.entity.AppApi;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.permission.PermissionParams;
import com.jeramtough.randl2.common.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.common.model.entity.Permission;
import com.jeramtough.randl2.common.model.entity.Role;
import com.jeramtough.randl2.common.mapper.AppApiMapper;
import com.jeramtough.randl2.common.mapper.PermissionMapper;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import com.jeramtough.randl2.common.model.dto.PermissionDto;
import com.jeramtough.randl2.common.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.userapp.service.PermissionService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission,
        PermissionDto> implements
        PermissionService, WithLogger {

    private final RoleMapper roleMapper;
    private final AppApiMapper appApiMapper;

    @Autowired
    public PermissionServiceImpl(WebApplicationContext wc,
                                 MapperFacade mapperFacade,
                                 RoleMapper roleMapper,
                                 AppApiMapper appApiMapper) {
        super(wc, mapperFacade);
        this.roleMapper = roleMapper;
        this.appApiMapper = appApiMapper;
    }

    @Override
    protected PermissionDto toDto(Permission permission) {
        return null;
    }


    @Override
    public List<PermissionDto> getPermissionListByRoleId(Long roleId) {
        if (roleId.equals(SuperAdmin.ROLE_ID)) {
            List<PermissionDto> permissionDtoList = new ArrayList<>();
            List<AppApi> appApiList = appApiMapper.selectList(null);
            for (AppApi appApi : appApiList) {
                PermissionDto permissionDto = new PermissionDto();
                permissionDto.setApiDescription(appApi.getDescription());
                permissionDto.setApiId(appApi.getFid());
                permissionDto.setApiPath(appApi.getPath());
                permissionDto.setApiAlias(appApi.getAlias());
                permissionDto.setRoleDescription(SuperAdmin.ROLE_DESCRIPTION);
                permissionDto.setRoleId(SuperAdmin.ROLE_ID);
                permissionDto.setRoleName(SuperAdmin.ROLE_NAME);
                permissionDtoList.add(permissionDto);
            }
            return permissionDtoList;
        }
        else {
            if (roleMapper.selectById(roleId) == null) {
                throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
            }
            return getBaseMapper().selectListPermissionDtoByRoleId(roleId);
        }
    }

    @Override
    public String setPermissions(PermissionParams permissionParams) {
        BeanValidator.verifyDto(permissionParams);

        Role role = roleMapper.selectById(permissionParams.getRoleId());
        if (role == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
        }

        //先移除该角色的所有接口权限，然后在重新设置回去
        getBaseMapper().delete(
                new QueryWrapper<Permission>().eq("role_id", permissionParams.getRoleId()));
        List<Permission> permissionList = new ArrayList<>();
        for (Long apiId : permissionParams.getApiIds()) {
            //TODO 判断该接口是否存在代码没写
            Permission permission = new Permission();
            permission.setRoleId(permissionParams.getRoleId());
            permission.setApiId(apiId);
            permissionList.add(permission);
        }
        saveBatch(permissionList);

        return "角色【" + role.getDescription() + "】更新API接口权限成功！";
    }

    @Override
    public String removePermissions(PermissionParams permissionParams) {
        BeanValidator.verifyDto(permissionParams);

        if (roleMapper.selectById(permissionParams.getRoleId()) == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
        }

        for (Long apiId : permissionParams.getApiIds()) {
            getBaseMapper().delete(new QueryWrapper<Permission>()
                    .eq("api_id", apiId)
                    .eq("role_id",
                            permissionParams.getRoleId()));
        }

        return "移除API接口权限成功！";
    }

    @Override
    public List<PermissionDto> getPermissions() {
        return getBaseMapper().selectListPermissionDto();
    }


}
