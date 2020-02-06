package com.jeramtough.randl2.dto;

import com.jeramtough.randl2.dao.entity.Role;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <pre>
 * Created on 2020/2/6 16:54
 * by @author JeramTough
 * </pre>
 */
public class SystemUserDto {

    private Long uid;

    private String username;

    private String password;

    private String phoneNumber;

    private String emailAddress;

    private Integer accountStatus;

    private Role role;

    private String surfaceImage;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }
}
