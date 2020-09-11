package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.permission.AddRoleParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateRoleParams;
import com.jeramtough.randl2.common.model.entity.AdminUser;
import com.jeramtough.randl2.common.model.entity.Role;
import com.jeramtough.randl2.common.mapper.AdminUserMapper;
import com.jeramtough.randl2.common.mapper.RoleMapper;
import com.jeramtough.randl2.common.model.dto.RoleDto;
import com.jeramtough.randl2.adminapp.service.RoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleDto>
        implements RoleService,
        WithLogger {

    private final AdminUserMapper adminUserMapper;

    @Autowired
    public RoleServiceImpl(WebApplicationContext wc, MapperFacade mapperFacade,
                           AdminUserMapper adminUserMapper) {
        super(wc, mapperFacade);
        this.adminUserMapper = adminUserMapper;
    }

    @Override
    protected RoleDto toDto(Role role) {
        RoleDto roleDto = getMapperFacade().map(role, RoleDto.class);
        return roleDto;
    }


    @Override
    public String addRole(AddRoleParams params) {
        BeanValidator.verifyDto(params);
        if (getBaseMapper().selectOne(
                new QueryWrapper<Role>().eq("name", params.getName())) != null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "角色名");
        }

        Role role = getMapperFacade().map(params, Role.class);
        role.setName(role.getName().toUpperCase());
        save(role);
        return "添加新的系统角色成功";
    }

    @Override
    public String deleteRole(Long fid) {
        Role role = getBaseMapper().selectById(fid);
        if (role == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
        }

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", role.getFid());
        if (adminUserMapper.selectCount(queryWrapper) > 0) {
            throw new ApiResponseException(5011);
        }

        getBaseMapper().deleteById(fid);
        return "删除系统角色【" + role.getName() + "】成功";
    }

    @Override
    public String updateRole(UpdateRoleParams params) {
        BeanValidator.verifyDto(params);
        Role role = getBaseMapper().selectById(params.getFid());
        if (role == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
        }

        if (!params.getName().equalsIgnoreCase(role.getName())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<Role>().eq("name", params.getName())) != null) {
                throw new ApiResponseException(5001);
            }
        }

        role = getMapperFacade().map(params, Role.class);
        if (role.getName() != null) {
            role.setName(role.getName().toUpperCase());
        }
        updateById(role);
        return "更新接口【" + role.getName() + "】成功";
    }

    @Override
    public RoleDto getRole(Long fid) {
        Role role = getBaseMapper().selectById(fid);
        if (role == null) {
            throw new ApiResponseException(ErrorU.CODE_7.C, "角色");
        }
        return getBaseDto(role);
    }

    @Override
    public List<RoleDto> getAllRole() {
        List<RoleDto> roleDtoList = getMapperFacade().mapAsList(list(), RoleDto.class);
        return roleDtoList;
    }

    @Override
    public List<RoleDto> getAllAdminRole() {
        List<Role> list = getBaseMapper().selectList(new QueryWrapper<Role>().like("name",
                "%_ADMIN"));
        List<RoleDto> roleDtoList = getMapperFacade().mapAsList(list, RoleDto.class);
        return roleDtoList;
    }

    @Override
    public List<RoleDto> getAllUserRole() {
        List<Role> list = getBaseMapper().selectList(new QueryWrapper<Role>().like("name",
                "%_USER"));
        List<RoleDto> roleDtoList = getMapperFacade().mapAsList(list, RoleDto.class);
        return roleDtoList;
    }

    @Override
    public List<RoleDto> getRoleListByKeyword(String keyword) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", keyword).or().like("name", "%" + keyword + "%")
                    .or().like("description", "%" + keyword + "%");
        List<Role> apiList = getBaseMapper().selectList(queryWrapper);
        if (apiList == null) {
            throw new ApiResponseException(5040);
        }
        return getDtoList(apiList);
    }
}
