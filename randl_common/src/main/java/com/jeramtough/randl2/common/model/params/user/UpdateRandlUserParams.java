package com.jeramtough.randl2.common.model.params.user;

import com.jeramtough.randl2.common.model.error.ErrorU;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * <pre>
 * Created on 2020/1/30 17:41
 * by @author JeramTough
 * </pre>
 */
@Schema(description = "更新Randl用户参数")
public class UpdateRandlUserParams {

   @NotNull(payload = ErrorU.CODE_1.class)
    @Schema(description = "用户ID", example = "0", required = true)
    private Long uid;

    @Schema(description = "用户名", example = "username", required = false)
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,16}$",payload = ErrorU.CODE_8.class,
            message = "帐号名长度范围在5-16位；只能为数字或者字母或者下划线_；不能含有特殊字符")
    private String account;

    @Pattern(regexp = "^\\S{8,16}$",payload = ErrorU.CODE_8.class,
            message = "密码长度范围在8-16位；只允许非空白任意字符")
    @Schema(description = "密码", example = "password", required = false)
    private String password;

    @Schema(description = "手机号码", example = "15289678163", dataType = "String", required =
            false)
    @Pattern(
            regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|" +
                    "(18[0-9])|(19[8|9]))\\d{8}$"
            ,payload = ErrorU.CODE_4.class,message = "例子:15289678164")
    private String phoneNumber;

    @Schema(description = "邮箱地址", example = "1321312@qq.com", required = false)
    @Email(payload = ErrorU.CODE_4.class,message = "例子:1171867004@qq.com")
    private String emailAddress;

    /**
     * 账号状态 0:禁用 | 1:启用 | 2:标记删除
     */
    @Schema(description = "账号状态 0:禁用 | 1:启用 | 2:标记删除", example = "1", required = false)
    private Integer accountStatus;

    /**
     * 头像id
     */
    @Schema(description = "头像id", example = "1", required = false)
    private Long surfaceImageId;

    public UpdateRandlUserParams() {
    }

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

    public Long getSurfaceImageId() {
        return surfaceImageId;
    }

    public void setSurfaceImageId(Long surfaceImageId) {
        this.surfaceImageId = surfaceImageId;
    }
}
