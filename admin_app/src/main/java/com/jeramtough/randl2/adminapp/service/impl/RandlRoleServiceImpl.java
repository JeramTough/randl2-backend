package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.service.impl.BaseDtoServiceImpl;
import com.jeramtough.randl2.common.mapper.RandlUserRoleMapMapper;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.permission.AddRoleParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateRoleParams;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.adminapp.service.RandlRoleService;
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
public class RandlRoleServiceImpl extends BaseDtoServiceImpl<RandlRoleMapper, RandlRole, RandlRoleDto>
        implements RandlRoleService, WithLogger {

    private final RandlUserRoleMapMapper randlUserRoleMapMapper;

    @Autowired
    public RandlRoleServiceImpl(WebApplicationContext wc,
                                RandlUserRoleMapMapper randlUserRoleMapMapper) {
        super(wc);
        this.randlUserRoleMapMapper = randlUserRoleMapMapper;
    }

    @Override
    protected RandlRoleDto toDto(RandlRole randlRole) {
        RandlRoleDto randlRoleDto = getMapperFacade().map(randlRole, RandlRoleDto.class);
        return randlRoleDto;
    }


    @Override
    public String addRole(AddRoleParams params) {
        BeanValidator.verifyParams(params);
        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlRole>().eq("name", params.getName())) != null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "角色名");
        }

        RandlRole randlRole = getMapperFacade().map(params, RandlRole.class);
        randlRole.setName(randlRole.getName().toUpperCase());
        save(randlRole);
        return "添加新的系统角色成功";
    }

    @Override
    public String deleteRole(Long fid) {
        return "删除系统角色【】成功";
    }

    @Override
    public String updateRole(UpdateRoleParams params) {
        BeanValidator.verifyParams(params);

        return "";
    }

    @Override
    public RandlRoleDto getRole(Long fid) {
        return null;
    }

    @Override
    public List<RandlRoleDto> getAllRole() {
        List<RandlRoleDto> randlRoleDtoList = getMapperFacade().mapAsList(list(), RandlRoleDto.class);
        return randlRoleDtoList;
    }


    @Override
    public List<RandlRoleDto> getRoleListByKeyword(String keyword) {
        QueryWrapper<RandlRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", keyword).or().like("name", "%" + keyword + "%")
                    .or().like("description", "%" + keyword + "%");
        List<RandlRole> apiList = getBaseMapper().selectList(queryWrapper);
        if (apiList == null) {
            throw new ApiResponseException(5040);
        }
        return getDtoList(apiList);
    }

    @Override
    public RandlRoleDto getRoleByAppIdAndUid(Long appId, Long uid) {
        RandlRole randlRole = randlUserRoleMapMapper.selectOneRandlRoleByAppIdAndUid(appId, uid);
        if (randlRole == null) {
            throw new ApiResponseException(ErrorU.CODE_502.C);
        }
        return toDto(randlRole);
    }
}
