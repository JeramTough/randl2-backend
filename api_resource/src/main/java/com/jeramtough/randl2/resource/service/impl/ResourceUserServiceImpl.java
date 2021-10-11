package com.jeramtough.randl2.resource.service.impl;

import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.resource.service.ResourceUserService;
import com.jeramtough.randl2.service.randl.RandlPersonalInfoService;
import com.jeramtough.randl2.service.randl.RandlUserService;
import com.jeramtough.randl2.service.user.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <pre>
 * Created on 2021/8/8 下午6:50
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class ResourceUserServiceImpl implements ResourceUserService {

    private final SystemUserService systemUserService;
    private final RandlUserService randlUserService;
    private final RandlPersonalInfoService randlPersonalInfoService;

    @Autowired
    public ResourceUserServiceImpl(
            SystemUserService systemUserService,
            RandlUserService randlUserService,
            RandlPersonalInfoService randlPersonalInfoService) {
        this.systemUserService = systemUserService;
        this.randlUserService = randlUserService;
        this.randlPersonalInfoService = randlPersonalInfoService;
    }

    @Override
    public SystemUserDto getRandlUserByToken() {
        SystemUser systemUser = UserHolder.getSystemUser();
        MyClientDetails myClientDetails = UserHolder.getClientDetails();

        Objects.requireNonNull(myClientDetails);
        Long otherAppId = myClientDetails.getOauthClientDetails().getAppId();

        SystemUserDto systemUserDto = systemUserService
                .getSystemUserDto(systemUser, otherAppId);
        return systemUserDto;
    }

    @Override
    public String test() {
        RandlUser randlUser = randlUserService.getById(999);
        return randlUser.toString();
    }

    @Override
    public RandlPersonalInfoDto getPersonalInfoByToken() {
        SystemUser systemUser = UserHolder.getSystemUser();

        Objects.requireNonNull(systemUser);
        Objects.requireNonNull(systemUser.getUid());

        RandlPersonalInfoDto personalInfoDto =
                randlPersonalInfoService.getPersonalInfoByUid(systemUser.getUid());
        return personalInfoDto;
    }
}
