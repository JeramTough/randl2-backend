package com.jeramtough.randl2.component.login.user;

import cn.hutool.core.bean.BeanUtil;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.config.setting.AppSetting;
import com.jeramtough.randl2.common.mapper.RandlUserMapper;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.service.randl.RandlRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <pre>
 * Created on 2020/11/20 0:39
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class AccountUserLoginer extends BaseUserLoginer implements UserLoginer {

    private final RandlRoleService randlRoleService;
    private final AppSetting appSetting;

    @Autowired
    public AccountUserLoginer(PasswordEncoder passwordEncoder,
                              RandlUserMapper randlUserMapper,
                              RandlRoleService randlRoleService,
                              AppSetting appSetting) {
        super(passwordEncoder, randlUserMapper);
        this.randlRoleService = randlRoleService;
        this.appSetting = appSetting;
    }

    @Override
    public SystemUser login(Object credentials) throws ApiResponseException {
        String account = (String) credentials;

        RandlUser randlUser = getRandlUserMapper().selectByAccount(account);
        checkRandlUser(randlUser);

        //获取用户角色信息
        List<RandlRole> randlRoleList = randlRoleService.getRoleListByAppIdAndUid
                (appSetting.getDefaultUserAppId(), randlUser.getUid());

        SystemUser systemUser = BeanUtil.copyProperties(randlUser, SystemUser.class);
        systemUser.setRoles(randlRoleList);

        return systemUser;
    }
}
