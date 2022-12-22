package com.jeramtough.randl2.common.model.params.module;

import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@Schema(description = "RandlModule对象")
public class AddRandlModuleParams implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "菜单中文名")
    private String name;

    @Schema(description = "菜单描述")
    @Length(max = 254, payload = ErrorU.CODE_7.class, message = "最长254个字符")
    private String description;

    @Schema(description = "菜单路径")
    private String path;


    @Schema(description = "排序")
    @Range(payload = ErrorU.CODE_7.class, max = Integer.MAX_VALUE, message = "数字")
    private Integer moduleOrder;

    @Schema(description = "菜单图标 默认为斜杠")
    @NotBlankButNull(payload = ErrorU.CODE_2.class,isSetNullAuto = true)
    private String icon;

    @Schema(description = "上级菜单Id")
    private Long parentId;

    @Schema(description = "是否可用")
    private Integer isAble;

    @Schema(description = "AppId")
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long appId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public void setModuleOrder(Integer mudoleOrder) {
        this.moduleOrder = mudoleOrder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "RandlModule{" +
                ", name=" + name +
                ", description=" + description +
                ", url=" + path +
                ", order=" + moduleOrder +
                ", icon=" + icon +
                ", parentId=" + parentId +
                ", isAble=" + isAble +
                ", appId=" + appId +
                "}";
    }
}
