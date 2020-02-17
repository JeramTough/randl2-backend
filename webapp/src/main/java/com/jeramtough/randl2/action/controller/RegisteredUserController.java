package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.service.RegisteredUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@RequestMapping("/registeredUser")
@ApiResponses(value = {
        @ApiResponse(code = 7000, message = "注册信息以失效，请重新注册"),
})
public class RegisteredUserController extends BaseController {

    private final RegisteredUserService registeredUserService;

    @Autowired
    public RegisteredUserController(
            RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    @ApiOperation(value = "新账号登录方式校验", notes = "校验手机号码或者邮箱是否允许被注册")
    @RequestMapping(value = "/verify/phoneOrEmailForNew", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7001, message = "手机号码格式错误"),
            @ApiResponse(code = 7002, message = "邮箱地址格式错误"),
            @ApiResponse(code = 7003, message = "已存在重复的手机号码，请换一个"),
            @ApiResponse(code = 7004, message = "已存在重复的邮箱地址，请换一个"),
            @ApiResponse(code = 7005, message = "way参数只能填1(以手机)或2(以邮箱)"),
    })
    public RestfulApiResponse verifyPhoneOrEmailForNew(
            @RequestBody VerifyPhoneOrEmailForNewParams params) {
        return getSuccessfulApiResponse(
                registeredUserService.verifyPhoneOrEmailForNew(params));
    }

    @ApiOperation(value = "找回密码账号校验", notes = "校验手机号码或者邮箱")
    @RequestMapping(value = "/verify/phoneOrEmailByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7010, message = "该手机号未注册过本系统"),
            @ApiResponse(code = 7011, message = "该邮箱地址未注册过本系统"),
    })
    public void verifyPhoneOrEmailByForget() {

    }

    @ApiOperation(value = "密码校验", notes = "校验密码")
    @RequestMapping(value = "/verify/password", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7020, message = "添加管理员用户失败！两次密码不一致"),
            @ApiResponse(code = 7021, message = "密码长度范围在8-16位；只允许非空白任意字符"),
    })
    public RestfulApiResponse verifyPassword(VerifyPasswordParams params) {
        return getSuccessfulApiResponse(registeredUserService.verifyPassword(params));
    }

    @ApiOperation(value = "注册", notes = "注册该用户")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7030, message = "注册信息以失效，请重新注册"),
    })
    public void registerUser() {

    }

    @ApiOperation(value = "重置", notes = "重置密码")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7040, message = "注册验证码错误！"),
    })
    public void resetUser() {

    }

}

