package com.jeramtough.randl2.common.model.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
@Schema(description="RandlUser对象")
public class RandlUserDto implements Serializable{

    private static final long serialVersionUID=1L;

    @Schema(description = "用户id主键")
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    @Schema(description = "系统账号")
    private String account;

    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "邮箱地址")
    private String emailAddress;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "注册时间")
    private Date registrationTime;

    @Schema(description = "注册ip")
    private String registrationIp;

    @Schema(description = "注册地址")
    private String registrationAddress;

    @Schema(description = "账号状态 0:禁用 | 1:启用 | 2:标记删除")
    private Integer accountStatus;

    @Schema(description = "头像id")
    private Long surfaceImageId;

    @Schema(description = "用户来源渠道 0:管理员添加 | 1:用户注册 | 2:数据库直接添加")
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


    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
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

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
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
