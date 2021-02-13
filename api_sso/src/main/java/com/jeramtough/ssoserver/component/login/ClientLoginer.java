package com.jeramtough.ssoserver.component.login;

import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;
import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;

/**
 * <pre>
 * Created on 2020/11/19 0:36
 * by @author WeiBoWen
 * </pre>
 */
public interface ClientLoginer {

    public MyClientDetails login(Object credentials) throws ApiResponseException;
}
