package com.jeramtough.authserver.component.oauth2.provider;

import com.jeramtough.authserver.component.oauth2.token.ClientSecretAuthenticationToken;
import com.jeramtough.authserver.component.oauth2.token.JwtAuthenticationToken;
import com.jeramtough.authserver.service.MyUserDetailsService;
import com.jeramtough.jtcomponent.task.response.TaskResponse;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.setting.AppSetting;
import com.jeramtough.randl2.common.component.userdetail.MyUserDetails;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

        jwtAuthenticationToken.setAuthenticated(true);
        String uid = taskResponse.getTaskResult().getStringPayload("uid", "0");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(uid));

        // 构建返回的用户登录成功的UsernamePasswordAuthenticationToken，
        //为了跳过UserPasswordFileter的校验
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(jwtAuthenticationToken, token,
                        authorities);

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
