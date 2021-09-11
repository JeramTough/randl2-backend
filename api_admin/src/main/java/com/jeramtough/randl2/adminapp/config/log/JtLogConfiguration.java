package com.jeramtough.randl2.adminapp.config.log;

import com.jeramtough.jtlog.config.SimpleLogConfigDefaultValues;
import com.jeramtough.jtlog.jtlogger.LoggerManager;
import com.jeramtough.jtlog.level.LogLevel;
import com.jeramtough.jtlog.recorder.LogRecorder;
import com.jeramtough.jtlog.recorder.file.FileLogRecorder;
import com.jeramtough.jtlog.recorder.strategy.SortedDateFileStrategy;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

/**
 * <pre>
 * Created on 2020/10/25 15:51
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
public class JtLogConfiguration {

    static {
        LoggerManager.setLogConfigDefaultValues(new SimpleLogConfigDefaultValues() {
            @Override
            public LogLevel decideMinVisibleLevel() {
                return LogLevel.DEBUG;
            }

            @Override
            public void additionGlobalLogRecorders(List<LogRecorder> logRecorders) {
                logRecorders.add(new FileLogRecorder(new SortedDateFileStrategy(new File(
                        "logs"), "admin.log")));
            }
        });
    }

}
