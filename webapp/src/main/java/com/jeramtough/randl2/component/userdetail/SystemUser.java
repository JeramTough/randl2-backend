package com.jeramtough.randl2.component.userdetail;

import com.jeramtough.randl2.component.userdetail.UserType;
import com.jeramtough.randl2.dao.entity.Role;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * <pre>
 * Created on 2020/1/30 0:01
 * by @author JeramTough
 * </pre>
 */
public class SystemUser implements Principal {

    private Long uid;

    private String username;

    private String phoneNumber;

    private String emailAddress;

    private String password;

    private LocalDateTime registrationTime;

    private String registrationIp;

    private Integer accountStatus;

    private UserType userType;

    private Role role;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String getName() {
        return getUsername();
    }
}
