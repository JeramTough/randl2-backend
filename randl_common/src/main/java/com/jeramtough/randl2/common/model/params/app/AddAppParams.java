package com.jeramtough.randl2.common.model.params.app;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@ApiModel(value="添加RandlApp对象", description="")
public class AddAppParams implements Serializable{

    private static final long serialVersionUID=1L;

   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "客户端中文名称")
    private String appName;


   @NotNull(payload = ErrorU.CODE_1.class)
    @ApiModelProperty(value = "客户端描述")
   @Length(max = 255,payload = ErrorU.CODE_7.class,message = "最长255个字符")
    private String description;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return "RandlUserApp{" +
        ", appName=" + appName +
        ", description=" + description +
        "}";
    }
}
