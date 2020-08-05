package com.jeramtough.randl2.model.params.surfaceimage;

import com.jeramtough.randl2.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <pre>
 * Created on 2020/2/20 17:47
 * by @author JeramTough
 * </pre>
 */

@ApiModel("更新用户头像参数")

public class UpdateSurfaceImageParams implements Serializable {

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiModelProperty(value = "用户ID", example = "0", required = true)
    private Long uid;

    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiModelProperty(value = "图片base64格式的编码值", example = "0", required = true)
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
