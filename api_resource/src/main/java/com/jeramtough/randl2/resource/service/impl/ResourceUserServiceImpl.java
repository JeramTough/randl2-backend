package com.jeramtough.randl2.resource.service.impl;

import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.resource.service.ResourceUserService;
import com.jeramtough.randl2.service.user.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Created on 2021/8/8 下午6:50
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class ResourceUserServiceImpl implements ResourceUserService {

    private final SystemUserService systemUserService;

    @Autowired
    public ResourceUserServiceImpl(
            SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Override
    public SystemUserDto getRandlUserByToken() {
        SystemUser systemUser = UserHolder.getSystemUser();
        SystemUserDto systemUserDto = systemUserService.getSystemUserDto(systemUser);
        return systemUserDto;
    }
}
