package com.jeramtough.authserver.service;

import com.jeramtough.randl2.common.component.attestation.userdetail.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <pre>
 * Created on 2020/11/17 8:36
 * by @author WeiBoWen
 * </pre>
 */
public interface MyUserDetailsService extends UserDetailsService {

    MyUserDetails loadUserById(Long uid);

}
