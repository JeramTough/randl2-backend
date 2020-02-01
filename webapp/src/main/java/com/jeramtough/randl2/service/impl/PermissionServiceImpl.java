package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.permission.PermissionParams;
import com.jeramtough.randl2.dao.entity.Permission;
import com.jeramtough.randl2.dao.mapper.PermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeramtough.randl2.dao.mapper.RoleMapper;
import com.jeramtough.randl2.dto.PermissionDto;
import com.jeramtough.randl2.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements
        PermissionService {

    private final RoleMapper roleMapper;

    @Autowired
    public PermissionServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public String addPermissions(PermissionParams permissionParams) {
        BeanValidator.verifyDto(permissionParams);

        if (roleMapper.selectById(permissionParams.getRoleId()) == null) {
            throw new ApiResponseException(3001);
        }

        List<Permission> permissionList = new ArrayList<>();
        for (Long apiId : permissionParams.getApiIds()) {
            //TODO 判断该接口是否存在代码没写
            Permission permission = new Permission();
            permission.setRoleId(permissionParams.getRoleId());
            permission.setApiId(apiId);
            permissionList.add(permission);
        }
        saveBatch(permissionList);

        return "添加API接口权限成功！";
    }

    @Override
    public String removePermissions(PermissionParams permissionParams) {
        BeanValidator.verifyDto(permissionParams);

        if (roleMapper.selectById(permissionParams.getRoleId()) == null) {
            throw new ApiResponseException(3001);
        }

        for (Long apiId : permissionParams.getApiIds()) {
            getBaseMapper().delete(new QueryWrapper<Permission>()
                    .eq("api_id", apiId)
                    .eq("rrole_id",
                            permissionParams.getRoleId()));
        }

        return "移除API接口权限成功！";
    }

    @Override
    public List<PermissionDto> getPermissions() {
        return getBaseMapper().selectListPermissionDto();
    }
}
