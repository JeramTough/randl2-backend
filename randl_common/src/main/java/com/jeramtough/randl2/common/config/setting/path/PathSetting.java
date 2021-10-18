package com.jeramtough.randl2.common.config.setting.path;

import com.jeramtough.jtweb.component.location.chunzhen.setting.ChunZhenLocationSettingAdapter;

/**
 * <pre>
 * Created on 2021/10/12 上午10:36
 * by @author WeiBoWen
 * </pre>
 */
public class PathSetting implements ChunZhenLocationSettingAdapter {

    private String chunzhenFilePath;

    public String getChunzhenFilePath() {
        return chunzhenFilePath;
    }

    public void setChunzhenFilePath(String chunzhenFilePath) {
        this.chunzhenFilePath = chunzhenFilePath;
    }

    @Override
    public String getQqwryDatFilePath() {
        return getChunzhenFilePath();
    }
}
