package com.jeramtough.randl2.common.model.params.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jeramtough.jtweb.component.validation.constraints.NotBlankButNull;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@ApiModel(value = "RandlModule对象", description = "")
public class UpdateModuleParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    @NotNull(payload = ErrorU.CODE_1.class)
    private Long fid;

    @ApiModelProperty(value = "菜单中文名")
    private String name;

    @ApiModelProperty(value = "菜单描述")
    @Length(max = 254, payload = ErrorU.CODE_7.class, message = "最长254个字符")
    private String description;

    @ApiModelProperty(value = "菜单路径")
    private String path;

    @ApiModelProperty(value = "排序")
    @Range(payload = ErrorU.CODE_7.class, max = Integer.MAX_VALUE, message = "数字")
    private Integer moduleOrder;

    @ApiModelProperty(value = "菜单图标 默认为斜杠")
    @NotBlankButNull(payload = ErrorU.CODE_2.class,isSetNullAuto = true)
    private String icon;

    @ApiModelProperty(value = "是否可用")
    private Integer isAble;

    @ApiModelProperty(value = "上级菜单Id")
    private Long parentId;

    @JSONField(serialize=false)
    private Integer level;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

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


    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "RandlModule{" +
                "fid=" + fid +
                ", name=" + name +
                ", description=" + description +
                ", url=" + path +
                ", order=" + moduleOrder +
                ", icon=" + icon +
                ", isAble=" + isAble +
                "}";
    }
}
