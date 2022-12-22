package com.jeramtough.randl2.common.model.params.surfaceimage;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * <pre>
 * Created on 2020/2/20 17:47
 * by @author JeramTough
 * </pre>
 */

@Schema(description = "更新用户头像参数")

public class UpdateSurfaceImageParams implements Serializable {

   @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "用户ID", example = "0", required = true)
    private Long uid;

   @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "图片base64格式的编码值", example = "0", required = true)
    private String surfaceImage;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }
}
