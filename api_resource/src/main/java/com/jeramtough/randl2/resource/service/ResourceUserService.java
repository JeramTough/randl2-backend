package com.jeramtough.randl2.resource.service;

import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.model.dto.SystemUser2Dto;

/**
 * <pre>
 * Created on 2021/8/8 下午6:50
 * by @author WeiBoWen
 * </pre>
 */
public interface ResourceUserService {
    SystemUser2Dto getRandlUserByToken();
}
