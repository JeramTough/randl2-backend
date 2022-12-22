package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description="RandlOperationLog对象")
public class RandlOperationLog implements Serializable {

    private static final long serialVersionUID=1L;

  /**
   * 主键
   */
    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

  /**
   * 操作机ip地址
   */
    private String clientIp;

  /**
   * 管理员id
   */
    private Long adminId;

  /**
   * 管理员名字
   */
    private String adminName;

  /**
   * 接口描述
   */
    private String apiDescription;

  /**
   * 日志记录创建时间
   */
    private Date createTime;

  /**
   * API模块
   */
    private String apiModule;

  /**
   * 执行是否完成，0:未完成，1:完成
   */
    private Boolean result;

  /**
   * 请求url
   */
    private String requestUrl;

  /**
   * java方法
   */
    private String javaMethod;

  /**
   * 请求参数
   */
    private String requestArgs;

  /**
   * 相应内容
   */
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
