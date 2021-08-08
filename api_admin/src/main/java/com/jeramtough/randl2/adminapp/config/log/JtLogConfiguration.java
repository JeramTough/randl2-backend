package com.jeramtough.randl2.adminapp.config.log;

import com.jeramtough.jtlog.bean.LogInformation;
import com.jeramtough.jtlog.config.SimpleLogConfigDefaultValues;
import com.jeramtough.jtlog.context.LogContext;
import com.jeramtough.jtlog.jtlogger.LoggerManager;
import com.jeramtough.jtlog.level.LogLevel;
import com.jeramtough.jtlog.recorder.FileLogRecorder;
import com.jeramtough.jtlog.recorder.LogRecorder;

import java.io.File;
import java.util.List;

/**
 * <pre>
 * Created on 2020/10/25 15:51
 * by @author WeiBoWen
 * </pre>
 */
public class JtLogConfiguration {

    static {
        LoggerManager.setLogConfigDefaultValues(new SimpleLogConfigDefaultValues() {
            @Override
            public LogLevel decideMinVisibleLevel() {
                return LogLevel.DEBUG;
            }

            @Override
            public void additionGlobalLogRecorders(List<LogRecorder> logRecorders) {
                logRecorders.add(new FileLogRecorder(new File("./logs/System.log")));
            }
        });
    }

}
