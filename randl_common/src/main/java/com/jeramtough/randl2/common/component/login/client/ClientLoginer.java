package com.jeramtough.randl2.common.component.login.client;

import com.jeramtough.jtweb.component.apiresponse.exception.ApiResponseException;
import com.jeramtough.randl2.common.component.clientdetail.MyClientDetails;

/**
 * <pre>
 * Created on 2020/11/17 10:38
 * by @author WeiBoWen
 * </pre>
 */
public interface ClientLoginer {

    MyClientDetails login(Object credentials) throws ApiResponseException;

}
