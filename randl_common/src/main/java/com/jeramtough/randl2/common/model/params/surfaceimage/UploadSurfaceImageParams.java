package com.jeramtough.randl2.common.model.params.surfaceimage;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModelProperty;

import jakarta.validation.constraints.NotNull;

/**
 * <pre>
 * Created on 2020/2/20 18:29
 * by @author JeramTough
 * </pre>
 */
public class UploadSurfaceImageParams {

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "图片base64格式的编码值", example = "0", required = true)
    private String surfaceImage;

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }
}
