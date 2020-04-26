package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.bean.registereduser.BindingPhoneOrEmailParams;
import com.jeramtough.randl2.bean.registereduser.LoginByPasswordCredentials;
import com.jeramtough.randl2.bean.registereduser.LoginByVerificationCodeCredentials;
import com.jeramtough.randl2.bean.registereduser.ResetPasswordParams;
import com.jeramtough.randl2.bean.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.service.RegisteredUserLoginService;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Created on 2020/3/22 23:00
 * by @author JeramTough
 * </pre>
 */
@RestController
@Api(tags = {"普通注册用户登录后接口"})
@RequestMapping("/registeredUser")
@ApiResponses({
        @ApiResponse(code = 10000, message = "http头部信息没有Authorization或者格式不是‘Bearer token’"),
        @ApiResponse(code = 10001, message = "token校验失败，[%s]’"),

})
public class RegisteredUserLoginedController extends BaseController {

    private final RegisteredUserLoginService registeredUserLoginService;
    private final RegisteredUserLoginedService registeredUserLoginedService;

    @Autowired
    public RegisteredUserLoginedController(
            RegisteredUserLoginService registeredUserLoginService,
            RegisteredUserLoginedService registeredUserLoginedService) {
        this.registeredUserLoginService = registeredUserLoginService;
        this.registeredUserLoginedService = registeredUserLoginedService;
    }

    @ApiOperation(value = "登录1", notes = "普通注册用户通过密码登录")
    @RequestMapping(value = "/login/byPassword", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 10002, message = "登录失败，该账号或者密码不正确"),
    })
    public RestfulApiResponse login1(LoginByPasswordCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginByPassword(credentials));
    }


    @ApiOperation(value = "登录2", notes = "普通注册用户通过验证码登录")
    @RequestMapping(value = "/login/byVerificationCode", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 10003, message = "登录失败，该账号或者密码并没有注册过本系统")
    })
    public RestfulApiResponse login2(LoginByVerificationCodeCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginByVerificationCode(credentials));
    }

    @ApiOperation(value = "个人资料", notes = "查询当前登录账号的个人资料")
    @RequestMapping(value = "/logined/personalInfo", method = {RequestMethod.GET})
    @ApiResponses(value = {
    })
    public RestfulApiResponse getPersonalInfoByUid() {
        return getSuccessfulApiResponse(registeredUserLoginedService.getPersonalInfo());
    }

    @ApiOperation(value = "更新个人资料", notes = "更新当前登录用户个人信息")
    @RequestMapping(value = "/logined/updatePersonalInfo", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public RestfulApiResponse updateAdminUser(@RequestBody UpdatePersonalInfoParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.updatePersonalInfo(params));
    }

    @ApiOperation(value = "重置密码", notes = "重置当前登录用户的密码")
    @RequestMapping(value = "/logined/resetPassword", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 10004, message = "重置失败，旧密码不正确"),
            @ApiResponse(code = 10005, message = "新密码和重复新密码不一致")
    })
    public RestfulApiResponse resetPassword(
            @RequestBody ResetPasswordParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.resetPassword(params));
    }

    @ApiOperation(value = "更新头像", notes = "更新普通用户的头像")
    @RequestMapping(value = "/logined/updateSurfaceImage", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public RestfulApiResponse updateUserSurfaceImageByBase64(
            @RequestBody UploadSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.updateSurfaceImageByBase64(params));
    }


    @ApiOperation(value = "绑定", notes = "绑定账号的手机号码或者邮箱")
    @RequestMapping(value = "/logined/bindingPhoneOrEmail", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 10006, message = "绑定的号码或者地址不能与当前的重复"),
            @ApiResponse(code = 10007, message = "绑定的号码或者地址已被其他账号所绑定")
    })
    public RestfulApiResponse bindingPhoneOrEmail(
            @RequestBody BindingPhoneOrEmailParams params) {
        return getSuccessfulApiResponse(registeredUserLoginedService.bindPhoneNumberOrEmailAddress(params));
    }

}
