package com.jeramtough.authserver.component.login;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.login.BaseUserLoginer;
import com.jeramtough.randl2.common.component.login.UserLoginer;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.component.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.login.LoginCredentials;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <pre>
 * Created on 2020/11/15 2:27
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class CredentialsUserLoginer extends BaseUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final RandlRoleMapper randlRoleMapper;
    private final RandlUserMapper randlUserMapper;
    private final MapperFacade mapperFacade;
    private final AppSetting appSetting;
    private final RandlRoleService randlRoleService;

    @Autowired
    public CredentialsUserLoginer(PasswordEncoder passwordEncoder,
                                  RandlRoleMapper randlRoleMapper,
                                  RandlUserMapper randlUserMapper, MapperFacade mapperFacade,
                                  AppSetting appSetting,
                                  RandlRoleService randlRoleService) {
        super(passwordEncoder, randlUserMapper);
        this.passwordEncoder = passwordEncoder;
        this.randlRoleMapper = randlRoleMapper;
        this.randlUserMapper = randlUserMapper;
        this.mapperFacade = mapperFacade;
        this.appSetting = appSetting;
        this.randlRoleService = randlRoleService;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        LoginCredentials loginCredentials = (LoginCredentials) credentials;
        RandlUser randlUser = getRandlUserByAcOrPhOrEm(loginCredentials.getCredentials(),
                loginCredentials.getPassword());

        //获取用户角色信息
        List<RandlRole> randlRoleList = randlRoleService.getRoleListByAppIdAndUid
                (appSetting.getDefaultAdminAppId(), randlUser.getUid());

        SystemUser systemUser = mapperFacade.map(randlUser, SystemUser.class);
        systemUser.setRoles(randlRoleList);

        return systemUser;
    }
}
