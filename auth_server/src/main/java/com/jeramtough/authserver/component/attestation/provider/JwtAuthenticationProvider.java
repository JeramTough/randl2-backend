package com.jeramtough.authserver.component.attestation.provider;

import com.jeramtough.authserver.component.attestation.token.JwtAuthenticationToken;
import com.jeramtough.authserver.service.MyUserDetailsService;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.SimplePrincipal;
import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <pre>
 * Created on 2020/11/18 9:27
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final AppSetting appSetting;
    private final MyUserDetailsService myUserDetailsService;


    @Autowired
    public JwtAuthenticationProvider(AppSetting appSetting,
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
        String[] roleAlias = taskResponse.getTaskResult().getStringArrayPayload("roleAlias");


       /* MyUserDetails myUserDetails = myUserDetailsService.loadUserById(Long.parseLong(uid));
        SystemUser systemUser = myUserDetails.getSystemUser();*/

        SimplePrincipal simplePrincipal = new SimplePrincipal(uid);

        List<GrantedAuthority> authorities =
                Arrays.asList(roleAlias)
                      .parallelStream()
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());

        // 构建返回的用户登录成功的UsernamePasswordAuthenticationToken，
        //为了跳过UserPasswordFileter的校验
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(simplePrincipal, token, authorities);

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
