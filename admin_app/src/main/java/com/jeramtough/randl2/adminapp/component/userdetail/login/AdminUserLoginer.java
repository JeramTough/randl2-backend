package com.jeramtough.randl2.adminapp.component.userdetail.login;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.component.userdetail.AccountStatus;
import com.jeramtough.randl2.common.mapper.RandlUserRoleMapMapper;
import com.jeramtough.randl2.common.model.entity.RandlUserWithRole;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.adminapp.component.userdetail.SuperAdmin;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserType;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    private final RandlUserRoleMapMapper userRoleMapMapper;
    private final MapperFacade mapperFacade;
    private final AppSetting appSetting;


    @Autowired
    public AdminUserLoginer(
            PasswordEncoder passwordEncoder,
            SuperAdmin superAdmin,
            RandlRoleMapper randlRoleMapper,
            RandlUserRoleMapMapper userRoleMapMapper,
            MapperFacade mapperFacade,
            AppSetting appSetting) {
        this.passwordEncoder = passwordEncoder;
        this.superAdmin = superAdmin;
        this.randlRoleMapper = randlRoleMapper;
        this.userRoleMapMapper = userRoleMapMapper;
        this.mapperFacade = mapperFacade;
        this.appSetting = appSetting;
    }

    @Override
    public SystemUser login(Object credentials) {
        AdminUserCredentials adminUserCredentials = (AdminUserCredentials) credentials;

        //如果是超级管理员登录
        if (superAdmin.getUsername().equals(
                adminUserCredentials.getUsername()) && passwordEncoder
                .matches(adminUserCredentials.getPassword(),
                        superAdmin.getPassword())) {
            return superAdmin.getSystemUser();
        }

        //如果是普通的系统管理员登录
        RandlUserWithRole randlUserWithRole =
                userRoleMapMapper.selectOneRandlUserByAppIdAndAccount(appSetting.getAdminDefaultAppId(),
                        adminUserCredentials.getUsername());

        if (randlUserWithRole != null) {
            //判断当前用户的状态
            if (AccountStatus.toAccountStatus(randlUserWithRole.getAccountStatus()) != AccountStatus.ABLE) {
                throw new ApiResponseException(ErrorU.CODE_304.C);
            }

            boolean passwordIsRight =
                    passwordEncoder.matches(adminUserCredentials.getPassword(),
                            randlUserWithRole.getPassword());
            if (passwordIsRight) {

                //所有用户在用一个应用下只能拥有一种角色
                SystemUser systemUser = mapperFacade.map(randlUserWithRole, SystemUser.class);
                systemUser.setUserType(UserType.ADMIN);
                return systemUser;
            }
            else {
                throw new ApiResponseException(ErrorU.CODE_301.C);
            }
        }
        else {
            //登录失败，管理员应用下不存在该用户
            throw new ApiResponseException(ErrorU.CODE_302.C);
        }
    }
}
