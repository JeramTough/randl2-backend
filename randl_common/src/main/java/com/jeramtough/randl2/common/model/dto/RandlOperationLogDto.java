package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-22
 */
@ApiModel(value="RandlOperationLog对象", description="")
public class RandlOperationLogDto implements Serializable{

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    @ApiModelProperty(value = "操作机ip地址")
    private String clientIp;

    @ApiModelProperty(value = "管理员id")
    private Long adminId;

    @ApiModelProperty(value = "管理员名字")
    private String adminName;

    @ApiModelProperty(value = "接口描述")
    private String apiDescription;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日志记录创建时间")
    private Date createTime;

    @ApiModelProperty(value = "API模块")
    private String apiModule;

    @ApiModelProperty(value = "执行是否完成，0:未完成，1:完成")
    private Boolean result;

    @ApiModelProperty(value = "请求url")
    private String requestUrl;

    @ApiModelProperty(value = "java方法")
    private String javaMethod;

    @ApiModelProperty(value = "请求参数")
    private String requestArgs;

    @ApiModelProperty(value = "相应内容")
    private String responseBody;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApiModule() {
        return apiModule;
    }

    public void setApiModule(String apiModule) {
        this.apiModule = apiModule;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getJavaMethod() {
        return javaMethod;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    public String getRequestArgs() {
        return requestArgs;
    }

    public void setRequestArgs(String requestArgs) {
        this.requestArgs = requestArgs;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "RandlOperationLog{" +
        "fid=" + fid +
        ", ip=" + clientIp +
        ", adminId=" + adminId +
        ", adminName=" + adminName +
        ", apiDescription=" + apiDescription +
        ", createTime=" + createTime +
        ", apiModule=" + apiModule +
        ", result=" + result +
        ", requestUrl=" + requestUrl +
        ", javaMethod=" + javaMethod +
        ", requestArgs=" + requestArgs +
        ", responseBody=" + responseBody +
        "}";
    }
}
