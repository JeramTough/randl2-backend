package com.jeramtough.randl2.common.model.entity;

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
 * @since 2020-10-03
 */
@ApiModel(value="RandlUser对象", description="")
public class RandlUser implements Serializable {

    private static final long serialVersionUID=1L;

  /**
   * 用户id主键
   */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

  /**
   * 系统账号
   */
    private String account;

  /**
   * 手机号码
   */
    private String phoneNumber;

  /**
   * 邮箱地址
   */
    private String emailAddress;

  /**
   * 密码
   */
    private String password;

  /**
   * 注册时间
   */
    private LocalDateTime registrationTime;

    /**
   * 修改时间
   */
    private LocalDateTime modifyTime;

  /**
   * 注册ip
   */
    private String registrationIp;

  /**
   * 账号状态 0:禁用 | 1:启用 | 2:标记删除
   */
    private Integer accountStatus;

  /**
   * 头像id
   */
    private Long surfaceImageId;

  /**
   * 用户来源渠道 0:管理员添加 | 1:用户注册 | 2:数据库直接添加
   */
    private Integer channel;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "RandlUser{" +
        "uid=" + uid +
        ", account=" + account +
        ", phoneNumber=" + phoneNumber +
        ", emailAddress=" + emailAddress +
        ", password=" + password +
        ", registrationTime=" + registrationTime +
        ", registrationIp=" + registrationIp +
        ", accountStatus=" + accountStatus +
        ", surfaceImageId=" + surfaceImageId +
        ", channel=" + channel +
        "}";
    }
}
