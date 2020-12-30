package com.jeramtough.randl2.userapp.service.impl;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.service.impl.BaseServiceImpl;
import com.jeramtough.randl2.common.model.params.login.LoginByPasswordParams;
import com.jeramtough.randl2.userapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * <pre>
 * Created on 2020/10/3 11:09
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService, WithLogger {


    @Autowired
    public LoginServiceImpl(WebApplicationContext wc) {
        super(wc);
    }

    @Override
    public Map<String, Object> userLogin(LoginByPasswordParams params) {

        return null;
    }
}
