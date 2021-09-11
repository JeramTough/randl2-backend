package com.jeramtough.randl2.service.details;

import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <pre>
 * Created on 2021/8/9 下午3:11
 * by @author WeiBoWen
 * </pre>
 */
public interface MyUserDetailsService extends UserDetailsService {

    /**
     * 调用该方法，角色信息默认只有Randl普通用户
     */
    MyUserDetails loadUserById(Long uid);

    /**
     * 调用该方法，角色信息默认只有Randl普通用户
     */
    MyUserDetails loadUserByAccount(String account);

    MyUserDetails loadUserByAccount(String account, Long appId);

}
