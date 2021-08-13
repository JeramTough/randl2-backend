package com.jeramtough.randl2.component.login.user;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.AccountStatus;
import com.jeramtough.randl2.common.component.attestation.userdetail.SuperAdmin;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.login.UserCredentials;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <pre>
 * Created on 2020/1/30 10:31
 * by @author JeramTough
 * </pre>
 */
@Component
@Scope("request")
public class AdminUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final SuperAdmin superAdmin;
    private final RandlRoleMapper randlRoleMapper;
    private final RandlUserMapper randlUserMapper;
    private final MapperFacade mapperFacade;
    private final AppSetting appSetting;
    private final RandlRoleService randlRoleService;


    @Autowired
    public AdminUserLoginer(
            PasswordEncoder passwordEncoder,
            SuperAdmin superAdmin,
            RandlRoleMapper randlRoleMapper,
            RandlUserMapper randlUserMapper, MapperFacade mapperFacade,
            AppSetting appSetting, RandlRoleService randlRoleService) {
        this.passwordEncoder = passwordEncoder;
        this.superAdmin = superAdmin;
        this.randlRoleMapper = randlRoleMapper;
        this.randlUserMapper = randlUserMapper;
        this.mapperFacade = mapperFacade;
        this.appSetting = appSetting;
        this.randlRoleService = randlRoleService;
    }

    @Override
    public SystemUser login(Object credentials) {
        UserCredentials userCredentials = (UserCredentials) credentials;

        //如果是超级管理员登录
        if (superAdmin.getUsername().equals(
                userCredentials.getUsername()) && passwordEncoder
                .matches(userCredentials.getPassword(),
                        superAdmin.getPassword())) {
            return superAdmin.getSystemUser();
        }

        //如果是普通的系统管理员登录
        RandlUser randlUser = randlUserMapper.selectByAccount(((UserCredentials) credentials).getUsername());
        if (randlUser == null) {
            //登录失败，不存在该用户
            throw new ApiResponseException(ErrorU.CODE_302.C);
        }

        //判断当前用户的状态
        if (AccountStatus.toAccountStatus(randlUser.getAccountStatus()) != AccountStatus.ABLE) {
            throw new ApiResponseException(ErrorU.CODE_304.C);
        }

        //如果密码不正确
        boolean passwordIsRight =
                passwordEncoder.matches(userCredentials.getPassword(),
                        randlUser.getPassword());
        if (!passwordIsRight) {
            throw new ApiResponseException(ErrorU.CODE_301.C);
        }

        //获取用户角色信息
        List<RandlRole> randlRoleList = randlRoleService.getRoleListByAppIdAndUid
                (appSetting.getDefaultAdminAppId(), randlUser.getUid());

        SystemUser systemUser = mapperFacade.map(randlUser, SystemUser.class);
        systemUser.setRoles(randlRoleList);

        return systemUser;
    }
}
