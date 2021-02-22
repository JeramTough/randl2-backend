package com.jeramtough.randl2.adminapp.config.web;

import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.component.optlog.bean.AddHistoryParams;
import com.jeramtough.jtweb.component.optlog.channel.LogChannel;
import com.jeramtough.jtweb.component.optlog.config.OptLoggerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * Created on 2021/2/22 9:19
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
public class MyOptLoggerConfig implements OptLoggerConfig {
    @Override
    public Long getUserId(HttpServletRequest request) {
        return null;
    }

    @Override
    public String getExpandInfo() {
        return "拓展额外信息";
    }


    @Override
    public Boolean isAble() {
        return true;
    }

    @Override
    public LogChannel[] logChanels() {
        return new LogChannel[]{new LogChannel() {
            @Override
            public void saveLog(AddHistoryParams params) {
                L.arrive();
            }
        }};
    }
}
