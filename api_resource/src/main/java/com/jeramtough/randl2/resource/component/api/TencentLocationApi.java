package com.jeramtough.randl2.resource.component.api;

import com.jeramtough.randl2.common.config.setting.AppSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * Created on 2021/10/12 上午10:26
 * by @author WeiBoWen
 * </pre>
 */
@Component
public class TencentLocationApi {

    private final AppSetting appSetting;

    @Autowired
    public TencentLocationApi(
            AppSetting appSetting) {
        this.appSetting = appSetting;
    }


}
