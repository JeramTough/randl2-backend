package com.jeramtough.authserver.action.controller;


import com.jeramtough.authserver.service.UserRegisterService;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.params.registereduser.DoRegisterOrResetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailByForgetParams;
import com.jeramtough.randl2.common.model.params.registereduser.VerifyPhoneOrEmailForNewParams;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@Api(tags = {"普通注册用户接口"})
@RequestMapping("/registeredUser")
@ApiResponses(value = {

})
public class UserRegisterController extends MyBaseController {

    private final UserRegisterService userRegisterService;


    @Autowired
    public UserRegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @ApiOperation(value = "新账号注册方式校验", notes = "校验手机号码或者邮箱是否允许被注册")
    @RequestMapping(value = "/verify/phoneOrEmailForNew", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7001, message = "手机号码格式错误"),
            @ApiResponse(code = 7002, message = "邮箱地址格式错误"),
            @ApiResponse(code = 7003, message = "已存在重复的手机号码，请换一个"),
            @ApiResponse(code = 7004, message = "已存在重复的邮箱地址，请换一个"),
            @ApiResponse(code = 7005, message = "way参数只能填1(以手机)或2(以邮箱)"),
    })
    public CommonApiResponse<Map<String, Object>> verifyPhoneOrEmailForNew(
            @RequestBody VerifyPhoneOrEmailForNewParams params) {
        return getSuccessfulApiResponse(
                userRegisterService.verifyPhoneOrEmailForNew(params));
    }

    @ApiOperation(value = "找回密码账号校验", notes = "校验手机号码或者邮箱")
    @RequestMapping(value = "/verify/phoneOrEmailByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7005, message = "way参数只能填1(以手机)或2(以邮箱)"),
            @ApiResponse(code = 7010, message = "该手机号或者邮箱地址未注册或绑定过本系统"),
    })
    public CommonApiResponse<Map<String, Object>> verifyPhoneOrEmailByForget(@RequestBody
                                                                                     VerifyPhoneOrEmailByForgetParams params) {
        return getSuccessfulApiResponse(
                userRegisterService.verifyPhoneOrEmailByForget(params));
    }

    @ApiOperation(value = "注册时用户密码校验", notes = "注册时用户密码校验")
    @RequestMapping(value = "/verify/password", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7020, message = "校验失败！两次密码不一致"),
            @ApiResponse(code = 7021, message = "密码长度范围在8-16位；只允许非空白任意字符"),
    })
    public CommonApiResponse<String> verifyPassword(@RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(userRegisterService.verifyPassword(params));
    }

    @ApiOperation(value = "找回密码时用户密码校验", notes = "找回密码时用户密码校验")
    @RequestMapping(value = "/verify/passwordByForget", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7020, message = "校验失败！两次密码不一致"),
            @ApiResponse(code = 7021, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 7022, message = "新密码不能和旧密码一致"),
    })
    public CommonApiResponse<Object> verifyPasswordByForget(
            @RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(userRegisterService.verifyPasswordByForget(params));
    }

    @ApiOperation(value = "确定注册", notes = "注册该用户")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7030, message = "注册未完成或信息以失效，请重新注册"),
            @ApiResponse(code = 7031, message = "验证码校验失败，或验证码未发送或以失效"),
    })
    public CommonApiResponse<Object> registerUser(@RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(userRegisterService.register(params));
    }

    @ApiOperation(value = "确定重置", notes = "重置密码")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7040, message = "重置未完成或信息以失效，请重新开始重置流程"),
            @ApiResponse(code = 7041, message = "验证码校验失败，或验证码未发送或以失效"),
            @ApiResponse(code = 7042, message = "重置失败！账户信息未做过任何修改"),
    })
    public CommonApiResponse<Object> resetUser(@RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(userRegisterService.reset(params));
    }


}

