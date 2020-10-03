package com.jeramtough.randl2.common.model.params.surfaceimage;

import com.jeramtough.randl2.common.model.error.ErrorU;
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

@ApiModel("更新管理员用户头像参数")

public class UpdateCurrentAdminSurfaceImageParams implements Serializable {


    @NotNull(message = ErrorU.CODE_1.C + "")
    @ApiModelProperty(value = "图片base64格式的编码值", example = "0", required = true)
    private String surfaceImage;


    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }
}
