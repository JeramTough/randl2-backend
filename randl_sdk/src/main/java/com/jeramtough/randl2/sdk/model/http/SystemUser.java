/**
  * Copyright 2020 bejson.com 
  */
package com.jeramtough.randl2.sdk.model.http;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2020-11-25 23:36:31
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class SystemUser {

    private String account;
    private int accountStatus;
    private String emailAddress;
    private String phoneNumber;
    private Date registrationTime;
    private List<Roles> roles;
    private int surfaceImageId;
    public void setAccount(String account) {
         this.account = account;
     }
     public String getAccount() {
         return account;
     }

    public void setAccountStatus(int accountStatus) {
         this.accountStatus = accountStatus;
     }
     public int getAccountStatus() {
         return accountStatus;
     }

    public void setEmailAddress(String emailAddress) {
         this.emailAddress = emailAddress;
     }
     public String getEmailAddress() {
         return emailAddress;
     }

    public void setPhoneNumber(String phoneNumber) {
         this.phoneNumber = phoneNumber;
     }
     public String getPhoneNumber() {
         return phoneNumber;
     }

    public void setRegistrationTime(Date registrationTime) {
         this.registrationTime = registrationTime;
     }
     public Date getRegistrationTime() {
         return registrationTime;
     }

    public void setRoles(List<Roles> roles) {
         this.roles = roles;
     }
     public List<Roles> getRoles() {
         return roles;
     }

    public void setSurfaceImageId(int surfaceImageId) {
         this.surfaceImageId = surfaceImageId;
     }
     public int getSurfaceImageId() {
         return surfaceImageId;
     }

}