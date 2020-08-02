package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.model.params.registereduser.*;
import com.jeramtough.randl2.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.model.dto.PersonalInfoDto;
import com.jeramtough.randl2.service.RegisteredUserLoginService;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @ApiOperation(value = "游客登录", notes = "游客登录，授予游客身份令牌")
    @RequestMapping(value = "/login/visitor", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 10008, message = "游客身份账号密码不正确")
    })
    public CommonApiResponse<Map<String, Object>> loginForVisitor(LoginForVisitorCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginForVisitor(credentials));
    }

    @ApiOperation(value = "登录1", notes = "普通注册用户通过密码登录")
    @RequestMapping(value = "/login/byPassword", method = {RequestMethod.POST})
    public CommonApiResponse<Map<String, Object>> login1(LoginByPasswordCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginByPassword(credentials));
    }


    @ApiOperation(value = "登录2", notes = "普通注册用户通过验证码登录")
    @RequestMapping(value = "/login/byVerificationCode", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 10003, message = "登录失败，该账号或者密码并没有注册过本系统")
    })
    public CommonApiResponse<Map<String, Object>> login2(LoginByVerificationCodeCredentials credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginService.loginByVerificationCode(credentials));
    }

    @ApiOperation(value = "个人资料", notes = "查询当前登录账号的个人资料")
    @RequestMapping(value = "/logined/personalInfo", method = {RequestMethod.GET})
    @ApiResponses(value = {
    })
    public CommonApiResponse<PersonalInfoDto> getPersonalInfoByUid() {
        return getSuccessfulApiResponse(registeredUserLoginedService.getPersonalInfo());
    }

    @ApiOperation(value = "更新个人资料", notes = "更新当前登录用户个人信息")
    @RequestMapping(value = "/logined/updatePersonalInfo", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateAdminUser(@RequestBody UpdatePersonalInfoParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.updatePersonalInfo(params));
    }

    @ApiOperation(value = "重置密码", notes = "重置当前登录用户的密码")
    @RequestMapping(value = "/logined/resetPassword", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 10004, message = "重置失败，旧密码不正确"),
            @ApiResponse(code = 10005, message = "新密码和重复新密码不一致")
    })
    public CommonApiResponse<String> resetPassword(
            @RequestBody ResetPasswordParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.resetPassword(params));
    }

    @ApiOperation(value = "更新头像", notes = "更新普通用户的头像")
    @RequestMapping(value = "/logined/updateSurfaceImage", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateUserSurfaceImageByBase64(
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
    public CommonApiResponse<String> bindingPhoneOrEmail(
            @RequestBody BindingPhoneOrEmailParams params) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.bindPhoneNumberOrEmailAddress(params));
    }

}
