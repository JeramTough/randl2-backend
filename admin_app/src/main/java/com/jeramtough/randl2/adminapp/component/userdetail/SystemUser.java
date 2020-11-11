package com.jeramtough.randl2.adminapp.component.userdetail;

import com.jeramtough.randl2.common.model.entity.RandlRole;

import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * Created on 2020/1/30 0:01
 * by @author JeramTough
 * </pre>
 */
public class SystemUser implements Principal, Serializable {

    private Long uid;

    private String account;

    private String phoneNumber;

    private String emailAddress;

    private String password;

    private LocalDateTime registrationTime;

    private String registrationIp;

    private Integer accountStatus;

    private UserType userType;

    private Long surfaceImageId;

    private List<RandlRole>roles;

    private Long appId;

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String getName() {
        return getAccount();
    }

    public Long getSurfaceImageId() {
        return surfaceImageId;
    }

    public void setSurfaceImageId(Long surfaceImageId) {
        this.surfaceImageId = surfaceImageId;
    }


    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<RandlRole> getRoles() {
        return roles;
    }

    public void setRoles(List<RandlRole> roles) {
        this.roles = roles;
    }
}
