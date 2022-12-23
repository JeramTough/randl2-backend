package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.adminapp.service.LoginService;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.UserCredentials;
import com.jeramtough.randl2.component.login.user.AdminUserLoginer;
import com.jeramtough.randl2.component.login.user.UserLoginer;
import com.jeramtough.randl2.service.user.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2020/10/3 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService, WithLogger {

    private final SystemUserService systemUserService;

    @Autowired
    public LoginServiceImpl(WebApplicationContext wc,
                            SystemUserService systemUserService) {
        super(wc);
        this.systemUserService = systemUserService;
    }

    @Override
    public void adminLogin(String username, String password) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(password);
        userCredentials.setUsername(username);
        this.adminLogin(userCredentials);
    }

    @Override
    public void adminLogin(UserCredentials userCredentials) {
        BeanValidator.verifyParams(userCredentials);

        UserLoginer userLoginer = super.getWC().getBean(AdminUserLoginer.class);
        SystemUser systemUser;
        try {
            systemUser = userLoginer.login(userCredentials);
        }
        catch (ApiResponseException e) {
            getLogger().debug("登录失败! 登录参数%s 因为：%s", userCredentials.toString(),
                    e.getMessage());
            throw e;
        }
        getLogger().verbose("登录成功，登录参数%s", userCredentials.toString());

        UserHolder.afterLogin(systemUser);
    }

    @Override
    public String adminLogoutSuccessful() {
        return "退出登陆成功！";
    }

    @Override
    public String unlogged() {
        return "未登录！";
    }

    @Override
    public String denied() {
        return "未授权不被允许！";
    }

    @Override
    public SystemUserDto adminLoginSuccessful() {
        SystemUser systemUser = UserHolder.getSystemUser();
        SystemUserDto systemUserDto = systemUserService.getSystemUserDto(systemUser);
        return systemUserDto;
    }

}
