package com.jeramtough.randl2.adminapp.component.attestation.request;

import org.springframework.util.AntPathMatcher;

/**
 * <pre>
 * Created on 2022/12/23 下午4:03
 * by @author WeiBoWen
 * </pre>
 */
public class BaseJtAuthorizationRequest {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher("/");


    public BaseJtAuthorizationRequest() {
    }

    public AntPathMatcher getAntPathMatcher() {
        return antPathMatcher;
    }

}
