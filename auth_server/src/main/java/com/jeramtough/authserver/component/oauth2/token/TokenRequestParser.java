package com.jeramtough.authserver.component.oauth2.token;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2020/11/21 21:13
 * by @author WeiBoWen
 * </pre>
 */
public interface TokenRequestParser {

    String parse(HttpServletRequest request);

}
