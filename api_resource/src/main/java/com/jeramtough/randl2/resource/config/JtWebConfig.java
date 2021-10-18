package com.jeramtough.randl2.resource.config;

import com.jeramtough.jtweb.component.location.DefaultLocationGating;
import com.jeramtough.jtweb.component.location.LocationGating;
import com.jeramtough.jtweb.component.location.chunzhen.setting.ChunZhenLocationSettingAdapter;
import com.jeramtough.jtweb.component.location.tencent.setting.TencentLocationSettingAdapter;
import com.jeramtough.randl2.common.config.setting.AppSetting;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * Created on 2021/8/15 上午10:58
 * by @author WeiBoWen
 * </pre>
 */
@Configuration
//@EnableDbMonitor
//@EnableMapperCache
public class JtWebConfig {

    private final AppSetting appSetting;

    public JtWebConfig(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    @Bean
    public LocationGating getLocationGating() {
        ChunZhenLocationSettingAdapter chunZhenLocationSettingAdapter =
                appSetting.getPath();
        TencentLocationSettingAdapter tencentLocationSettingAdapter =
                appSetting.getApi().getTencentLocation();
        LocationGating locationGating = new DefaultLocationGating(
                chunZhenLocationSettingAdapter, tencentLocationSettingAdapter);
        return locationGating;
    }

}
