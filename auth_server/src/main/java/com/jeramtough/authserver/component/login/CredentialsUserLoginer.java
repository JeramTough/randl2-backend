package com.jeramtough.authserver.component.login;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.login.user.BaseUserLoginer;
import com.jeramtough.randl2.common.component.login.user.UserLoginer;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.login.LoginCredentialsParams;
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
@Component("credentialsUserLoginer")
public class CredentialsUserLoginer extends BaseUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final RandlRoleMapper randlRoleMapper;
    private final RandlUserMapper randlUserMapper;
    private final MapperFacade mapperFacade;
    private final RandlRoleService randlRoleService;

    @Autowired
    public CredentialsUserLoginer(PasswordEncoder passwordEncoder,
                                  RandlRoleMapper randlRoleMapper,
                                  RandlUserMapper randlUserMapper, MapperFacade mapperFacade,
                                  RandlRoleService randlRoleService) {
        super(passwordEncoder, randlUserMapper);
        this.passwordEncoder = passwordEncoder;
        this.randlRoleMapper = randlRoleMapper;
        this.randlUserMapper = randlUserMapper;
        this.mapperFacade = mapperFacade;
        this.randlRoleService = randlRoleService;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        LoginCredentialsParams loginCredentialsParams = (LoginCredentialsParams) credentials;
        RandlUser randlUser = getRandlUserByAcOrPhOrEm(loginCredentialsParams.getCredentials(),
                loginCredentialsParams.getPassword());

        //获取用户角色信息
        List<RandlRole> randlRoleList = randlRoleService.getRoleListByAppIdAndUid
                (loginCredentialsParams.getAppId(), randlUser.getUid());

        SystemUser systemUser = mapperFacade.map(randlUser, SystemUser.class);
        systemUser.setRoles(randlRoleList);

        return systemUser;
    }
}
