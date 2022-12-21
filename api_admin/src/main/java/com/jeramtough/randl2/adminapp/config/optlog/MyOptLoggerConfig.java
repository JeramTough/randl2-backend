package com.jeramtough.randl2.adminapp.config.optlog;

import com.alibaba.fastjson2.JSONObject;
import com.jeramtough.jtweb.component.optlog.channel.LogChannel;
import com.jeramtough.jtweb.component.optlog.config.OptLoggerConfig;
import com.jeramtough.jtweb.component.optlog.filter.OptLogFilter;
import com.jeramtough.randl2.common.component.attestation.userdetail.SystemUser;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

/**
 * <pre>
 * Created on 2021/2/22 16:35
 * by @author WeiBoWen
 * </pre>
 */
@Order(1)
@Primary
@Configuration
public class MyOptLoggerConfig implements OptLoggerConfig {

    private final RandlLogChannel randlLogChannel;

    @Autowired
    public MyOptLoggerConfig(RandlLogChannel randlLogChannel) {
        this.randlLogChannel = randlLogChannel;
    }


    @Override
    public String getExpandInfo() {
        JSONObject expandInfo=new JSONObject();
        if (UserHolder.hasLogined()){
            SystemUser systemUser = UserHolder.getSystemUser();
            if (systemUser != null) {
                expandInfo.put("uid",systemUser.getUid());
            }
        }
        return expandInfo.toJSONString();
    }

    @Override
    public Boolean isAble() {
        return true;
    }

    @Override
    public LogChannel[] logChanels() {
        return new LogChannel[]{randlLogChannel};
    }

    @Override
    public OptLogFilter[] optLogFilters() {
        return new OptLogFilter[0];
    }
}
