package com.jeramtough.randl2.common.config.setting.api;

import com.jeramtough.jtweb.component.location.tencent.setting.TencentLocationSettingAdapter;

/**
 * <pre>
 * Created on 2021/10/12 上午10:38
 * by @author WeiBoWen
 * </pre>
 */
public class TencentLocationSetting implements TencentLocationSettingAdapter {

    private String url;
    private String key;

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
