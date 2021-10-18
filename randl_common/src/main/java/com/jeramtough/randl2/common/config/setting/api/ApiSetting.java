package com.jeramtough.randl2.common.config.setting.api;

/**
 * <pre>
 * Created on 2021/10/12 上午10:37
 * by @author WeiBoWen
 * </pre>
 */
public class ApiSetting {

    private TencentLocationSetting tencentLocation;

    public TencentLocationSetting getTencentLocation() {
        return tencentLocation;
    }

    public void setTencentLocation(
            TencentLocationSetting tencentLocation) {
        this.tencentLocation = tencentLocation;
    }
}
