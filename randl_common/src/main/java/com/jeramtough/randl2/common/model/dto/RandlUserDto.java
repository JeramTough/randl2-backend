package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.jeramtough.randl2.common.model.entity.RandlRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@ApiModel(value="RandlUser对象", description="")
public class RandlUserDto implements Serializable{

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id主键")
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    @ApiModelProperty(value = "系统账号")
    private String account;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱地址")
    private String emailAddress;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
    private LocalDateTime registrationTime;

    @ApiModelProperty(value = "注册ip")
    private String registrationIp;

    @ApiModelProperty(value = "账号状态 0:禁用 | 1:启用 | 2:标记删除")
    private Integer accountStatus;

    @ApiModelProperty(value = "头像id")
    private Long surfaceImageId;

    @ApiModelProperty(value = "用户来源渠道 0:管理员添加 | 1:用户注册 | 2:数据库直接添加")
    private Integer channel;

    private List<Long> roleIds;

    private List<RandlRoleDto> roles;


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getRegistrationIp() {
        return registrationIp;
    }

    public void setRegistrationIp(String registrationIp) {
        this.registrationIp = registrationIp;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getSurfaceImageId() {
        return surfaceImageId;
    }

    public void setSurfaceImageId(Long surfaceImageId) {
        this.surfaceImageId = surfaceImageId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<RandlRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RandlRoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "RandlUser{" +
        "uid=" + uid +
        ", account=" + account +
        ", phoneNumber=" + phoneNumber +
        ", emailAddress=" + emailAddress +
        ", registrationTime=" + registrationTime +
        ", registrationIp=" + registrationIp +
        ", accountStatus=" + accountStatus +
        ", surfaceImageId=" + surfaceImageId +
        ", channel=" + channel +
        "}";
    }
}
