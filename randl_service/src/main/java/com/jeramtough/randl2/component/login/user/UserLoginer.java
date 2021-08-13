package com.jeramtough.randl2.component.login.user;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;

/**
 * <pre>
 * Created on 2020/1/30 10:28
 * by @author JeramTough
 * </pre>
 */
public interface UserLoginer {

    SystemUser login(Object credentials) throws ApiResponseException;
}
