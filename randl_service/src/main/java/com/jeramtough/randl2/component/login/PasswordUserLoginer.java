package com.jeramtough.randl2.component.login;

import cn.hutool.core.bean.BeanUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.mapper.RandlRoleMapper;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.component.login.user.BaseUserLoginer;
import com.jeramtough.randl2.component.login.user.UserLoginer;
import com.jeramtough.randl2.service.randl.RandlRoleService;
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
@Component("passwordUserLoginer")
public class PasswordUserLoginer extends BaseUserLoginer implements UserLoginer {

    private final PasswordEncoder passwordEncoder;
    private final RandlRoleMapper randlRoleMapper;
    private final RandlUserMapper randlUserMapper;
    private final RandlRoleService randlRoleService;

    @Autowired
    public PasswordUserLoginer(PasswordEncoder passwordEncoder,
                               RandlRoleMapper randlRoleMapper,
                               RandlUserMapper randlUserMapper,
                               RandlRoleService randlRoleService) {
        super(passwordEncoder, randlUserMapper);
        this.passwordEncoder = passwordEncoder;
        this.randlRoleMapper = randlRoleMapper;
        this.randlUserMapper = randlUserMapper;
        this.randlRoleService = randlRoleService;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        LoginByPasswordParams loginByPasswordParams = (LoginByPasswordParams) credentials;
        RandlUser randlUser = getRandlUserByAcOrPhOrEm(loginByPasswordParams.getCredential(),
                loginByPasswordParams.getPassword());

        //获取用户角色信息
        List<RandlRole> randlRoleList = randlRoleService.getRoleListByAppIdAndUid
                (loginByPasswordParams.getAppId(), randlUser.getUid());

        SystemUser systemUser = BeanUtil.copyProperties(randlUser, SystemUser.class);
        systemUser.setRoles(randlRoleList);

        return systemUser;
    }
}
