package com.jeramtough.randl2.adminapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseBeanException;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.service.RandlRoleService;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserRoleMapMapper;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.entity.RandlApp;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.BaseConditionParams;
import com.jeramtough.randl2.common.model.params.role.AddRoleParams;
import com.jeramtough.randl2.common.model.params.role.ConditionRoleParams;
import com.jeramtough.randl2.common.model.params.role.UpdateRoleParams;
import com.jeramtough.randl2.common.service.impl.MyBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
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
public class RandlRoleServiceImpl extends MyBaseServiceImpl<RandlRoleMapper, RandlRole, RandlRoleDto>
        implements RandlRoleService, WithLogger {

    private final RandlUserRoleMapMapper randlUserRoleMapMapper;
    private final AppSetting appSetting;
    private final RandlAppMapper randlAppMapper;

    private RandlRole defaultAdminRole, defaultUserRole;

    @Autowired
    public RandlRoleServiceImpl(WebApplicationContext wc,
                                RandlUserRoleMapMapper randlUserRoleMapMapper,
                                AppSetting appSetting,
                                RandlAppMapper randlAppMapper) {
        super(wc);
        this.randlUserRoleMapMapper = randlUserRoleMapMapper;
        this.appSetting = appSetting;
        this.randlAppMapper = randlAppMapper;
    }

    @Override
    protected RandlRoleDto toDto(RandlRole randlRole) {
        RandlRoleDto randlRoleDto = getMapperFacade().map(randlRole, RandlRoleDto.class);
        return randlRoleDto;
    }


    @Override
    public String addRole(AddRoleParams params) {
        BeanValidator.verifyParams(params);

        this.intDefaultRole();

        if (randlAppMapper.selectOne(
                new QueryWrapper<RandlApp>().eq("fid", params.getAppId())) == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "app应用");
        }

        if (params.getAlias().equals(SuperAdmin.ROLE_ALIAS_NAME) || params.getAlias().equalsIgnoreCase(
                defaultUserRole.getAlias())) {
            throw new ApiResponseBeanException(ErrorU.CODE_503.C, "alias别名");
        }

        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlRole>().eq("name", params.getName()).eq("app_id",
                        params.getAppId())) != null) {
            throw new ApiResponseException(ErrorU.CODE_11.C, "该应用下的角色名");
        }

        if (getBaseMapper().selectOne(
                new QueryWrapper<RandlRole>().eq("alias", params.getAlias()).eq("app_id",
                        params.getAppId())) != null) {
            throw new ApiResponseException(ErrorU.CODE_11.C, "角色别名(不可重复值)");
        }

        RandlRole randlRole = getMapperFacade().map(params, RandlRole.class);
        randlRole.setAlias(randlRole.getAlias().toUpperCase());
        randlRole.setCreateTime(LocalDateTime.now());

        getBaseMapper().insert(randlRole);
        return "添加新的系统角色成功";
    }

    @Override
    public String deleteRole(Long fid) {
        this.intDefaultRole();
        checkDefaultRoleOption(fid);

        return removeOneById(fid);
    }

    @Override
    public String updateRole(UpdateRoleParams params) {
        BeanValidator.verifyParams(params);
        this.intDefaultRole();
        this.checkDefaultRoleOption(params.getFid());

        RandlRole currentRandlRole = getBaseMapper().selectById(params.getFid());
        if (currentRandlRole == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "角色");
        }

        if (!currentRandlRole.getAlias().equalsIgnoreCase(params.getAlias())) {
            if (params.getAlias().equals(SuperAdmin.ROLE_ALIAS_NAME) || params.getAlias().equalsIgnoreCase(
                    defaultUserRole.getAlias())) {
                throw new ApiResponseBeanException(ErrorU.CODE_503.C, "alias别名");
            }

            if (getBaseMapper().selectOne(
                    new QueryWrapper<RandlRole>().eq("alias", params.getAlias()).eq("app_id",
                            params.getAppId())) != null) {
                throw new ApiResponseException(ErrorU.CODE_11.C, "角色别名(不可重复值)");
            }
        }

        if (!currentRandlRole.getName().equalsIgnoreCase(params.getName())) {
            if (getBaseMapper().selectOne(
                    new QueryWrapper<RandlRole>().eq("name", params.getName()).eq("app_id",
                            params.getAppId())) != null) {
                throw new ApiResponseException(ErrorU.CODE_11.C, "该应用下的角色名");
            }
        }


        RandlRole randlRole = getMapperFacade().map(params, RandlRole.class);
        randlRole.setAlias(randlRole.getAlias().toUpperCase());

        getBaseMapper().updateById(randlRole);
        return "更新角色成功";
    }


    @Override
    public List<RandlRoleDto> getRoleListByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<RandlRole> getRoleListByAppIdAndUid(Long appId, Long uid) {
        List<RandlRole> randlRoleList = getBaseMapper().selectListByUid(uid, appId);
        //每个用户都默认拥有RandlUser的角色
        RandlRole defaultRandlUserRole = getBaseMapper().selectById(appSetting.getDefaultUserRoleId());
        randlRoleList.add(defaultRandlUserRole);
        return randlRoleList;
    }

    @Override
    public List<RandlRoleDto> getListByAppId(Long appId) {

        this.intDefaultRole();


        if (randlAppMapper.selectOne(
                new QueryWrapper<RandlApp>().eq("fid", appId)) == null) {
            throw new ApiResponseException(ErrorU.CODE_9.C, "app应用");
        }

        List<RandlRole> randlRoleList = getBaseMapper().selectList(
                new QueryWrapper<RandlRole>().eq("app_id", appId));

        //每个应用都有Randl客户端用户这个角色
        if (!appId.equals(appSetting.getDefaultUserAppId())) {
            randlRoleList.add(defaultUserRole);
        }

        return getDtoList(randlRoleList);
    }

    @Override
    public PageDto<RandlRoleDto> pageByConditionTwo(QueryByPageParams queryByPageParams,
                                                    BaseConditionParams params,
                                                    QueryWrapper<RandlRole> queryWrapper) {
        ConditionRoleParams paramsForApi = (ConditionRoleParams) params;
        if (!paramsForApi.getAppId().equals(appSetting.getDefaultUserAppId()) && queryByPageParams.getSize() > 0) {
            queryByPageParams.setSize(queryByPageParams.getSize() - 1);
        }

        this.intDefaultRole();

        queryWrapper.nested(warpper -> warpper.eq("app_id", paramsForApi.getAppId()));

        if (params.getKeyword() != null) {
            queryWrapper.and(wrapper ->
                    wrapper.like("alias", params.getKeyword())
                           .or()
                           .eq("fid", params.getKeyword())
                           .or()
                           .like("name", params.getKeyword())
                           .or()
                           .like("description", params.getKeyword()));
        }

        //所有的应用都有默认Randl用户这个角色
        PageDto<RandlRoleDto> pageDto = super.pageByConditionTwo(queryByPageParams, params, queryWrapper);
        if (!paramsForApi.getAppId().equals(appSetting.getDefaultUserAppId())) {
            List<RandlRoleDto> list = new ArrayList<>();
            list.add(toDto(defaultUserRole));
            list.addAll(pageDto.getList());
            pageDto.setList(list);
        }
        return pageDto;
    }

    //*****************

    private void intDefaultRole() {
        if (defaultUserRole == null) {
            defaultUserRole = getBaseMapper().selectById(appSetting.getDefaultUserRoleId());
        }
        if (defaultAdminRole == null) {
            defaultAdminRole = getBaseMapper().selectById(appSetting.getDefaultAdminRoleId());
        }
    }


    /**
     * 检查是否对预设橘色进行的操作
     */
    private void checkDefaultRoleOption(Long roleId) {
        if (roleId.equals(defaultAdminRole.getFid()) || roleId.equals(defaultUserRole.getFid())) {
            throw new ApiResponseException(ErrorU.CODE_504.C);
        }
    }
}
