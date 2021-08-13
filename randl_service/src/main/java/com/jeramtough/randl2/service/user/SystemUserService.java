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

    SystemUserDto getSystemUserDto(SystemUser systemUser);

}
