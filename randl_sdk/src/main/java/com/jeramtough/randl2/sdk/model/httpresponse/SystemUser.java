package com.jeramtough.randl2.sdk.model.httpresponse;

import javax.security.auth.Subject;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-03
 */
public class SystemUser implements Principal,Serializable {

    private static final long serialVersionUID = 1L;

    private Long uid;

    private String account;

    private String phoneNumber;

    private String emailAddress;

    private String password;

    private Date registrationTime;

    private String registrationIp;

    private Integer accountStatus;

    private Long surfaceImageId;

    private Integer channel;

    private String surfaceImage;

    private List<RandlRole> roles;


    private List<Map<String, Object>> moduleAuthList;


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

    public String getSurfaceImage() {
        return surfaceImage;
    }

    public void setSurfaceImage(String surfaceImage) {
        this.surfaceImage = surfaceImage;
    }

    public List<Map<String, Object>> getModuleAuthList() {
        return moduleAuthList;
    }

    public void setModuleAuthList(List<Map<String, Object>> moduleAuthList) {
        this.moduleAuthList = moduleAuthList;
    }

    public List<RandlRole> getRoles() {
        return roles;
    }

    public void setRoles(List<RandlRole> roles) {
        this.roles = roles;
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

    @Override
    public String getName() {
        return getAccount();
    }

}
