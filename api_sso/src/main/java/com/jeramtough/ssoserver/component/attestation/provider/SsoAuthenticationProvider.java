package com.jeramtough.ssoserver.component.attestation.provider;

import com.jeramtough.ssoserver.component.attestation.token.JwtAuthenticationToken;
import com.jeramtough.ssoserver.service.MyUserDetailsService;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.AccountStatus;
import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.model.entity.RandlRole;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Created on 2020/11/18 9:27
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class SsoAuthenticationProvider implements AuthenticationProvider {

    private final AppSetting appSetting;
    private final MyUserDetailsService myUserDetailsService;


    @Autowired
    public SsoAuthenticationProvider(AppSetting appSetting,
                                     MyUserDetailsService myUserDetailsService) {
        this.appSetting = appSetting;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = ((JwtAuthenticationToken.JwtPrincipal) jwtAuthenticationToken.getPrincipal()).getName();

        if (token == null) {
            throw new ApiResponseException(ErrorU.CODE_5.C);
        }

        TaskResponse taskResponse = JwtTokenUtil.verifyToken(token, appSetting.getJwtSigningKey(),
                appSetting.getJwtIssuer());
        if (!taskResponse.isSuccessful()) {
            throw new ApiResponseException(ErrorU.CODE_6.C, taskResponse.getTaskResult().getMessage());
        }

        String uid = taskResponse.getTaskResult().getStringPayload("uid", "0");
        String account = taskResponse.getTaskResult().getStringPayload("account", uid);
        String[] roleAlias = taskResponse.getTaskResult().getStringArrayPayload("roleAlias");


        //生成SystemUser
        SystemUser systemUser = new SystemUser();
        systemUser.setUid(Long.parseLong(uid));
        systemUser.setAccount(account);
        systemUser.setAccountStatus(AccountStatus.ABLE.getNumber());

        List<RandlRole> roleList =
                Arrays.asList(roleAlias)
                      .parallelStream()
                      .map((alias) -> {
                          RandlRole randlRole = new RandlRole();
                          randlRole.setAlias(alias);
                          return randlRole;
                      })
                      .collect(Collectors.toList());
        systemUser.setRoles(roleList);

        MyUserDetails myUserDetails = new MyUserDetails(systemUser);


        // 构建返回的用户登录成功的UsernamePasswordAuthenticationToken，
        //为了跳过UserPasswordFileter的校验
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(systemUser, token, myUserDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(myUserDetails);


        return usernamePasswordAuthenticationToken;
    }

    /**
     * providerManager会遍历所有
     * securityconfig中注册的provider集合
     * 根据此方法返回true或false来决定由哪个provider
     * 去校验请求过来的authentication
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

}
