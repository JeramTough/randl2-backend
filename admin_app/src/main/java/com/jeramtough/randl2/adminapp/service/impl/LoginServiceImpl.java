package com.jeramtough.randl2.adminapp.service.impl;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.apiresponse.BeanValidator;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.adminapp.component.setting.AppSetting;
import com.jeramtough.randl2.adminapp.component.userdetail.SystemUser;
import com.jeramtough.randl2.adminapp.component.userdetail.UserHolder;
import com.jeramtough.randl2.adminapp.component.userdetail.login.AdminUserLoginer;
import com.jeramtough.randl2.adminapp.component.userdetail.login.UserLoginer;
import com.jeramtough.randl2.adminapp.service.RandlModuleAuthService;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.RandlModuleAuthDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.adminapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * <pre>
 * Created on 2020/10/3 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService, WithLogger {

    private final SourceSurfaceImageMapper surfaceImageMapper;
    private final RandlModuleAuthService randlModuleAuthService;
    private final AppSetting appSetting;

    @Autowired
    public LoginServiceImpl(WebApplicationContext wc,
                            SourceSurfaceImageMapper surfaceImageMapper,
                            RandlModuleAuthService randlModuleAuthService,
                            AppSetting appSetting) {
        super(wc);
        this.surfaceImageMapper = surfaceImageMapper;
        this.randlModuleAuthService = randlModuleAuthService;
        this.appSetting = appSetting;
    }

    @Override
    public SystemUserDto adminLogin(AdminUserCredentials adminUserCredentials) {
        BeanValidator.verifyParams(adminUserCredentials);

        UserLoginer userLoginer = super.getWC().getBean(AdminUserLoginer.class);
        SystemUser systemUser;
        try {
            systemUser = userLoginer.login(adminUserCredentials);
        }
        catch (ApiResponseException e) {
            getLogger().debug("登录失败! 登录参数%s 因为：%s", adminUserCredentials.toString(), e.getMessage());
            throw e;
        }
        getLogger().verbose("登录成功，登录参数%s", adminUserCredentials.toString());

        //到了这里，用户必须是登录成功了的
        UserHolder.afterLogin(systemUser);

        //processing SystemUserDto
        SystemUserDto systemUserDto = getMapperFacade().map(systemUser, SystemUserDto.class);
        String surfaceImage = surfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);

        getLogger().verbose("开始获取用户模块授权信息");
        List<RandlModuleAuthDto> moduleAuthDtoList =
                randlModuleAuthService.getRandlModuleAuthDtosByAppIdAndRoleId(appSetting.getAdminDefaultAppId(),
                        systemUser.getRoleId());

        systemUserDto.setModuleAuthList(moduleAuthDtoList);

        return systemUserDto;
    }

    @Override
    public String adminLogout() {
        return null;
    }
}