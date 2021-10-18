package com.jeramtough.randl2.common.component.token;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.config.setting.AppSetting;
import com.jeramtough.randl2.common.model.constant.OAuth2Constants;
import com.jeramtough.randl2.common.model.dto.OauthTokenDto;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2020/11/20 15:47
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class MyJwtTokenHolder implements TokenHolder {

    private final AppSetting appSetting;


    @Autowired
    public MyJwtTokenHolder(AppSetting appSetting) {
        this.appSetting = appSetting;
    }


    @Override
    public OauthTokenDto getToken(SystemUser systemUser) {
        OauthTokenDto oauthTokenDto = new OauthTokenDto();

        String[] roleAlias = systemUser.getRoles()
                                       .parallelStream()
                                       .map(RandlRole::getAlias)
                                       .toArray(String[]::new);

        String token = JwtTokenUtil.createToken(systemUser.getUid().toString(), systemUser.getAccount(), roleAlias,
                appSetting.getJwtSigningKey(),
                appSetting.getJwtIssuer(), appSetting.getJwtValidity());

        oauthTokenDto.setValue(token);
        oauthTokenDto.setTokenPrefix(OAuth2Constants.BEARER_PREFIX);
        oauthTokenDto.setExpiration(System.currentTimeMillis() + appSetting.getJwtSsoValidity());
        long minutes = appSetting.getJwtSsoValidity() / 1000 / 60;
        oauthTokenDto.setInfo("sso登录令牌有效时间为" + minutes + "分钟，请使用sso登录令牌去换取oauth2令牌或者前往用户授权页");

        return oauthTokenDto;
    }

    @Override
    public TaskResult verifyToken(String token) {
        return JwtTokenUtil.verifyToken(token, appSetting.getJwtSigningKey(),
                appSetting.getJwtIssuer()).getTaskResult();
    }
}
