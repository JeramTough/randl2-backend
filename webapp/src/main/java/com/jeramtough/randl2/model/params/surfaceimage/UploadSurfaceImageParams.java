package com.jeramtough.randl2.model.params.surfaceimage;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/20 18:29
 * by @author JeramTough
 * </pre>
 */
public class UploadSurfaceImageParams {

    @NotNull(message = "{'code':667,'placeholders':['更新失败','图片base64值']}")
    @ApiModelProperty(value = "图片base64格式的编码值", example = "0", required = true)
    private String surfaceImage;

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }
}
