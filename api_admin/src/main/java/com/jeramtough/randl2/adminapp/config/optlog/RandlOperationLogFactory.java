package com.jeramtough.randl2.adminapp.config.optlog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeramtough.jtweb.component.optlog.bean.AddHistoryParams;
import com.jeramtough.randl2.common.component.attestation.userdetail.UserHolder;
import com.jeramtough.randl2.common.model.entity.RandlOperationLog;
import com.jeramtough.randl2.common.model.entity.RandlUser;
import com.jeramtough.randl2.service.randl.RandlUserService;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <pre>
 * Created on 2021/2/22 17:29
 * by @author WeiBoWen
 * </pre>
 */
public class RandlOperationLogFactory {


    public static RandlOperationLog getRandlOperationLog(WebApplicationContext wc,
                                                         AddHistoryParams params) {
        RandlOperationLog randlOperationLog = new RandlOperationLog();

        //设置id
        String expandInfoStr = params.getExpandInfo();
        Objects.requireNonNull(expandInfoStr);
        JSONObject expandInfo = JSON.parseObject(expandInfoStr);
        Long uid = expandInfo.getLong("uid");
        randlOperationLog.setAdminId(uid);

        if (UserHolder.hasLogined()) {
            //设置帐号名
            Objects.requireNonNull(UserHolder.getSystemUser());
            if (uid.equals(UserHolder.getSystemUser().getUid())) {
                randlOperationLog.setAdminName(UserHolder.getSystemUser().getAccount());
            }
            else {
                RandlUserService randlUserService = (RandlUserService) wc.getBean(
                        "randlUserService");
                RandlUser randlUser = randlUserService.getById(uid);
                randlOperationLog.setAdminName(randlUser.getAccount());
            }
        }


        //设置api描述
        randlOperationLog.setApiDescription(params.getInterfaceDetail().getApiDescription());

        //设置调用者ip
        randlOperationLog.setClientIp(params.getIp());

        //设置时间
        randlOperationLog.setCreateTime(LocalDateTime.now());

        //设置是否完成
        randlOperationLog.setResult(params.getIsCompleted());

        //设置模块
        randlOperationLog.setApiModule(params.getInterfaceDetail().getApiModuleTag());

        //设置java方法名
        randlOperationLog.setJavaMethod(params.getInterfaceDetail().getShortJavaMethodName());

        //设置请求url
        randlOperationLog.setRequestUrl(params.getRequestUrl());

        //设置请求参数和响应结果
        randlOperationLog.setResponseBody(params.getResponse());
        randlOperationLog.setRequestArgs(params.getArgs());

        return randlOperationLog;
    }

}
