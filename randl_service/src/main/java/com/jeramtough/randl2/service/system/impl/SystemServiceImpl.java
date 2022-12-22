package com.jeramtough.randl2.service.system.impl;

import com.jeramtough.jtweb.component.apiinfo.core.ApiInfoRecorder;
import com.jeramtough.randl2.common.config.setting.AppSetting;
import com.jeramtough.randl2.service.randl.RandlApiService;
import com.jeramtough.randl2.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Created on 2022/12/22 下午3:45
 * by @author WeiBoWen
 * </pre>
 */
@Service
public class SystemServiceImpl implements SystemService {

    private final AppSetting appSetting;
    private final RandlApiService randlApiService;

    @Autowired
    public SystemServiceImpl(AppSetting appSetting, RandlApiService randlApiService) {
        this.appSetting = appSetting;
        this.randlApiService = randlApiService;
    }

    @Override
    public void registerSystemApiInfo() {
        ApiInfoRecorder apiInfoRecorder = ApiInfoRecorder.getInstance();

        randlApiService.registerRandlApi(apiInfoRecorder.getApiInfos().stream().toList(),
                appSetting.getDefaultAppId());
    }
}
