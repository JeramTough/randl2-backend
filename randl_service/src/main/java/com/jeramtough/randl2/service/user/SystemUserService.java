package com.jeramtough.randl2.service.user;

import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.model.dto.SystemUserDto;

/**
 * <pre>
 * Created on 2021/8/12 下午4:02
 * by @author WeiBoWen
 * </pre>
 */
public interface SystemUserService {

    /**
     * 使用默认系统
     */
    SystemUserDto getSystemUserDto(SystemUser systemUser);

    /**
     * 根据appId
     */
    SystemUserDto getSystemUserDto(SystemUser systemUser, Long appId);

}
