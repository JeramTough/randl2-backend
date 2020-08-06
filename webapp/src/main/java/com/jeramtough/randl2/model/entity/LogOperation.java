package com.jeramtough.randl2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@ApiModel(value = "LogOperation对象", description = "")
public class LogOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @ApiModelProperty(value = "操作机ip地址")
    private String ip;

    @ApiModelProperty(value = "管理员id")
    private Long adminId;

    @ApiModelProperty(value = "管理员名字")
    private String adminName;

    @ApiModelProperty(value = "接口名")
    private String apiName;

    @ApiModelProperty(value = "接口描述")
    private String apiDescription;

    @ApiModelProperty(value = "接口标签 使用英文,逗号分隔")
    private String tags;

    @ApiModelProperty(value = "操作内容")
    private String content;

    @ApiModelProperty(value = "日志记录创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "执行是否成功")
    private Boolean result;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "LogOperation{" +
                "fid=" + fid +
                ", ip=" + ip +
                ", adminId=" + adminId +
                ", adminName=" + adminName +
                ", apiName=" + apiName +
                ", content=" + content +
                ", createTime=" + createTime +
                ", result=" + result +
                "}";
    }
}
