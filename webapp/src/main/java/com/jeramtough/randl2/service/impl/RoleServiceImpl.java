package com.jeramtough.randl2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.bean.permission.AddRoleParams;
import com.jeramtough.randl2.bean.permission.UpdateRoleParams;
import com.jeramtough.randl2.dao.entity.Api;
import com.jeramtough.randl2.dao.entity.Role;
import com.jeramtough.randl2.dao.mapper.RoleMapper;
import com.jeramtough.randl2.dto.RoleDto;
import com.jeramtough.randl2.service.RoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

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
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService,
        WithLogger {

    @Autowired
    public RoleServiceImpl(WebApplicationContext wc, MapperFacade mapperFacade) {
        super(wc, mapperFacade);
    }

    @Override
    public String addRole(AddRoleParams params) {
        BeanValidator.verifyDto(params);
        if (getBaseMapper().selectOne(
                new QueryWrapper<Role>().eq("name", params.getName())) != null) {
            throw new ApiResponseException(5001);
        }

        Role role = getMapperFacade().map(params, Role.class);
        role.setName(role.getName().toUpperCase());
        save(role);
        return "添加新的系统角色成功";
    }

    @Override
    public String deleteRole(Long roleId) {
        Role role = getBaseMapper().selectById(roleId);
        if (role == null) {
            throw new ApiResponseException(5010);
        }
        return "删除系统角色【" + role.getName() + "】成功";
    }

    @Override
    public String updateRole(UpdateRoleParams params) {
        Role role = getBaseMapper().selectById(params.getFid());
        if (role == null) {
            throw new ApiResponseException(5021);
        }
        role = getMapperFacade().map(params, Role.class);
        if (role.getName() != null) {
            role.setName(role.getName().toUpperCase());
        }
        updateById(role);
        return "更新接口【" + role.getName() + "】成功";
    }

    @Override
    public RoleDto getRole(Long roleId) {
        Role role = getBaseMapper().selectById(roleId);
        if (role == null) {
            throw new ApiResponseException(5030);
        }
        RoleDto roleDto = getMapperFacade().map(role, RoleDto.class);
        return roleDto;
    }

    @Override
    public List<RoleDto> getAllRole() {
        List<RoleDto> roleDtoList = getMapperFacade().mapAsList(list(), RoleDto.class);
        return roleDtoList;
    }
}
