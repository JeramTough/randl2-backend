package com.jeramtough.randl2.common.model.params.personalinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@ApiModel(value = "更新PersonalInfo对象参数", description = "")
public class AddPersonalInfoParams implements Serializable {

    @NotNull(payload = ErrorU.CODE_1.class)
    private Long uid;

    private String gender;

    private Integer age;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
