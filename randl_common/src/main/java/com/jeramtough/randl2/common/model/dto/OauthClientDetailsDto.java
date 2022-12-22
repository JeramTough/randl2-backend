package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-02
 */
@Schema(description = "OauthClientDetails对象")
public class OauthClientDetailsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long appId;

    private String clientId;

    private String clientSecret;

    @Schema(description = "所拥有的资源Ids")
    private String resourceIds;

    private String authorizedGrantTypes;

    private List<String> authorizedGrantTypeList;

    private String webServerRedirectUris;

    private List<String> webServerRedirectUriList;

    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    private Boolean autoApprove;

    @Schema(description = "客户端中文名称")
    private String appName;

    @Schema(description = "客户端描述")
    private String description;

    @Schema(description = "是否可用")
    private Integer isAble;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "0=后台服务端，1=Web前端，2=安卓苹果端，3=小程序端")
    private Integer type;

    private List<OauthResourceDetailsDto> resources;

    private Map<String, List<OauthScopeDetailsDto>> scopeMap;

    public List<String> getAuthorizedGrantTypeList() {
        return authorizedGrantTypeList;
    }

    public void setAuthorizedGrantTypeList(List<String> authorizedGrantTypeList) {
        this.authorizedGrantTypeList = authorizedGrantTypeList;
    }

    public List<String> getWebServerRedirectUriList() {
        return webServerRedirectUriList;
    }

    public void setWebServerRedirectUriList(List<String> webServerRedirectUriList) {
        this.webServerRedirectUriList = webServerRedirectUriList;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }


    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUris() {
        return webServerRedirectUris;
    }

    public void setWebServerRedirectUris(String webServerRedirectUris) {
        this.webServerRedirectUris = webServerRedirectUris;
    }

    public Long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public List<OauthResourceDetailsDto> getResources() {
        return resources;
    }

    public void setResources(List<OauthResourceDetailsDto> resources) {
        this.resources = resources;
    }

    public Map<String, List<OauthScopeDetailsDto>> getScopeMap() {
        return scopeMap;
    }

    public void setScopeMap(
            Map<String, List<OauthScopeDetailsDto>> scopeMap) {
        this.scopeMap = scopeMap;
    }

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

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OauthClientDetails{" +
                "fid=" + fid +
                ", appId=" + appId +
                ", clientId=" + clientId +
                ", resourceIds=" + resourceIds +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", webServerRedirectUris=" + webServerRedirectUris +
                ", accessTokenValidity=" + accessTokenValidity +
                ", refreshTokenValidity=" + refreshTokenValidity +
                ", autoApprove=" + autoApprove +
                "}";
    }
}
