package com.jeramtough.randl2.common.component.token;

import com.jeramtough.jtcomponent.task.bean.TaskResult;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.model.dto.OauthTokenDto;

/**
 * <pre>
 * Created on 2020/11/20 15:46
 * by @author WeiBoWen
 * </pre>
 */
public interface TokenHolder {

    OauthTokenDto getToken(SystemUser systemUser);

    TaskResult verifyToken(String token);

}
