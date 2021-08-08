package com.jeramtough.randl2.adminapp.config.optlog;

import com.jeramtough.jtlog.with.WithLogger;
import com.jeramtough.jtweb.component.optlog.bean.AddHistoryParams;
import com.jeramtough.jtweb.component.optlog.channel.LogChannel;
import com.jeramtough.randl2.adminapp.config.optlog.RandlOperationLogFactory;
import com.jeramtough.randl2.common.model.entity.RandlOperationLog;
import com.jeramtough.randl2.service.randl.RandlOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * <pre>
 * Created on 2021/2/22 16:27
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class RandlLogChannel implements LogChannel, WithLogger {

    private final WebApplicationContext webApplicationContext;
    private final RandlOperationLogService randlOperationLogService;

    @Autowired
    public RandlLogChannel(WebApplicationContext webApplicationContext,
                           RandlOperationLogService randlOperationLogService) {
        this.webApplicationContext = webApplicationContext;
        this.randlOperationLogService = randlOperationLogService;
    }

    @Override
    public void saveLog(AddHistoryParams addHistoryParams) {

        getLogger().verbose("准备进入操作日志数据库保存渠道");

        RandlOperationLog randlOperationLog = RandlOperationLogFactory.getRandlOperationLog(webApplicationContext
                , addHistoryParams);
        boolean isSuccessful = randlOperationLogService.save(randlOperationLog);

        if (isSuccessful) {
            getLogger().debug("保存操作日志到数据库成功！");
        }
        else {
            getLogger().error("保存操作日志到数据库失败！");
        }

    }
}
