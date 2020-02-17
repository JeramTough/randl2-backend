package com.jeramtough.randl2.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.time.LocalDateTime;

/**
 * <pre>
 * Created on 2020/2/16 17:37
 * by @author JeramTough
 * </pre>
 */
public class RegisteredUserDto {

    private Long uid;

    private String account;

    private String phoneNumber;

    private String emailAddress;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private LocalDateTime registrationTime;

    private String registrationIp;

    private Integer accountStatus;


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

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "uid=" + uid +
                ", account=" + account +
                ", phoneNumber=" + phoneNumber +
                ", emailAddress=" + emailAddress +
                ", registrationTime=" + registrationTime +
                ", registrationIp=" + registrationIp +
                ", accountStatus=" + accountStatus +
                "}";
    }
}
