package com.jeramtough.ssoserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.jeramtough.jtweb.component.validation.BeanValidator;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.config.setting.AppSetting;
import com.jeramtough.randl2.common.component.token.MyJwtTokenHolder;
import com.jeramtough.randl2.common.mapper.RandlAppMapper;
import com.jeramtough.randl2.common.mapper.SourceSurfaceImageMapper;
import com.jeramtough.randl2.common.model.dto.OauthTokenDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.login.LoginByVerificationCodeParams;
import com.jeramtough.randl2.common.model.params.login.SsoLoginByPasswordParams;
import com.jeramtough.randl2.common.model.params.login.SsoLoginByVerificationCodeParams;
import com.jeramtough.randl2.component.login.PasswordUserLoginer;
import com.jeramtough.randl2.component.login.VerificationCodeUserLoginer;
import com.jeramtough.randl2.component.login.user.UserLoginer;
import com.jeramtough.randl2.service.resource.VerificationCodeService;
import com.jeramtough.ssoserver.service.SsoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Created on 2020/11/15 1:05
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class SsoLoginServiceImpl extends BaseServiceImpl implements SsoLoginService {

    private final RandlAppMapper randlAppMapper;
    private final VerificationCodeService verificationCodeService;
    private final SourceSurfaceImageMapper sourceSurfaceImageMapper;
    private final AppSetting appSetting;

    @Autowired
    public SsoLoginServiceImpl(WebApplicationContext webApplicationContext, RandlAppMapper randlAppMapper,
                               VerificationCodeService verificationCodeService,
                               SourceSurfaceImageMapper sourceSurfaceImageMapper,
                               AppSetting appSetting) {
        super(webApplicationContext);
        this.randlAppMapper = randlAppMapper;
        this.verificationCodeService = verificationCodeService;
        this.sourceSurfaceImageMapper = sourceSurfaceImageMapper;
        this.appSetting = appSetting;
    }

    @Override
    public Map<String, Object> loginByPassword(SsoLoginByPasswordParams params) {
        BeanValidator.verifyParams(params);

        //SSO登录只能有默认的角色信息
        LoginByPasswordParams passwordParams = BeanUtil.copyProperties(params, LoginByPasswordParams.class);
        passwordParams.setAppId(appSetting.getDefaultUserAppId());

        UserLoginer userLoginer = (PasswordUserLoginer) getWC().getBean("passwordUserLoginer");
        return loginToToken(userLoginer, passwordParams);
    }

    @Override
    public String logout() {
        UserHolder.clear();
        return "退出登录成功";
    }

    @Override
    public Map<String, Object> loginByVerificationCode(SsoLoginByVerificationCodeParams params) {
        BeanValidator.verifyParams(params);

        //SSO登录只能有默认的角色信息
        LoginByVerificationCodeParams verificationCodeParams = BeanUtil.copyProperties(params,
                LoginByVerificationCodeParams.class);
        verificationCodeParams.setAppId(appSetting.getDefaultUserAppId());

        UserLoginer userLoginer = (VerificationCodeUserLoginer) getWC().getBean("verificationCodeUserLoginer");
        return loginToToken(userLoginer, verificationCodeParams);
    }

    //*******************

    /**
     * 登录完返回jwtToken
     */
    private Map<String, Object> loginToToken(UserLoginer userLoginer, Object params) {

        SystemUser systemUser = userLoginer.login(params);

        UserHolder.afterLogin(systemUser);

        //生成token
        OauthTokenDto oauthTokenDto = getWC().getBean(MyJwtTokenHolder.class).getToken(systemUser);
        Map<String, Object> resultMap = new HashMap<>(2);
        SystemUserDto systemUserDto = BeanUtil.copyProperties(systemUser, SystemUserDto.class);

        //隐藏密码和uid
        systemUserDto.setPassword(null);
        systemUserDto.setUid(null);

        //设置头像
        String surfaceImage = sourceSurfaceImageMapper.selectById(
                systemUser.getSurfaceImageId()).getSurfaceImage();
        systemUserDto.setSurfaceImage(surfaceImage);
        systemUserDto.setSurfaceImage(surfaceImage);

        resultMap.put("systemUser", systemUserDto);
        resultMap.put("tokenBody", oauthTokenDto);
        return resultMap;
    }
}
