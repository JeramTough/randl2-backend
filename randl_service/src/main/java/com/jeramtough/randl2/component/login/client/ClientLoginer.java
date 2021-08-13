package com.jeramtough.randl2.component.login.client;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.attestation.clientdetail.MyClientDetails;

/**
 * <pre>
 * Created on 2020/11/19 0:36
 * by @author WeiBoWen
 * </pre>
 */
public interface ClientLoginer {

    public MyClientDetails login(Object credentials) throws ApiResponseException;
}
