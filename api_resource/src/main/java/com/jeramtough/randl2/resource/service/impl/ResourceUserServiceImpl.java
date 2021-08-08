package com.jeramtough.randl2.resource.service.impl;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.dto.SystemUser2Dto;
import com.jeramtough.randl2.resource.service.ResourceUserService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Created on 2021/8/8 下午6:50
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class ResourceUserServiceImpl implements ResourceUserService {

    @Override
    public SystemUser2Dto getRandlUserByToken() {
        L.arrive();
        return null;
    }
}
