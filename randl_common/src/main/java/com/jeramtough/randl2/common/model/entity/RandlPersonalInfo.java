package com.jeramtough.randl2.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * <p>
 * 
 * </p>
 *
 * @author JeramTough
 * @since 2020-10-02
 */
@Schema(description="RandlPersonalInfo对象")
public class RandlPersonalInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "fid", type = IdType.AUTO)
    private Long fid;

    private Long uid;

    private String gender;

    private Integer age;

    private LocalDate birthday;

    private String nickname;

    private String realname;

    private String homeAddress;

    private String school;

    private String company;

    private String job;

    private String contactWays;

    private String personalizedSignature;

    private Integer identityNumber;


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getContactWays() {
        return contactWays;
    }

    public void setContactWays(String contactWays) {
        this.contactWays = contactWays;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public Integer getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(Integer identityNumber) {
        this.identityNumber = identityNumber;
    }

    @Override
    public String toString() {
        return "RandlPersonalInfo{" +
        "fid=" + fid +
        ", uid=" + uid +
        ", gender=" + gender +
        ", age=" + age +
        ", birthday=" + birthday +
        ", nickname=" + nickname +
        ", realname=" + realname +
        ", homeAddress=" + homeAddress +
        ", school=" + school +
        ", company=" + company +
        ", job=" + job +
        ", contactWays=" + contactWays +
        ", personalizedSignature=" + personalizedSignature +
        ", identityNumber=" + identityNumber +
        "}";
    }
}
