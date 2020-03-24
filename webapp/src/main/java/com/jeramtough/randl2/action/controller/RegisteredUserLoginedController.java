package com.jeramtough.randl2.action.controller;

import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials1;
import com.jeramtough.randl2.bean.registereduser.RegisteredUserCredentials2;
import com.jeramtough.randl2.service.RegisteredUserLoginedService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
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
        @ApiResponse(code = 10001, message = "token校验失败，[%s]’")
})
public class RegisteredUserLoginedController extends BaseController {

    private RegisteredUserLoginedService registeredUserLoginedService;

    @Autowired
    public RegisteredUserLoginedController(
            RegisteredUserLoginedService registeredUserLoginedService) {
        this.registeredUserLoginedService = registeredUserLoginedService;
    }

    @ApiOperation(value = "登录1", notes = "普通注册用户通过密码登录")
    @RequestMapping(value = "/login/byPassword", method = {RequestMethod.POST})
    @ApiResponses(value = {})
    public RestfulApiResponse login1(RegisteredUserCredentials1 credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.loginByPassword(credentials));
    }


    @ApiOperation(value = "登录2", notes = "普通注册用户通过验证码登录")
    @RequestMapping(value = "/login/byVerificationCode", method = {RequestMethod.POST})
    @ApiResponses(value = {})
    public RestfulApiResponse login2(RegisteredUserCredentials2 credentials) {
        return getSuccessfulApiResponse(
                registeredUserLoginedService.loginByVerificationCode(credentials));
    }


    @ApiOperation(value = "绑定", notes = "绑定账号的手机号码或者邮箱"
            , authorizations = {@Authorization("aaaa")})
    @RequestMapping(value = "/logined/bindingPhoneOrEmail", method = {RequestMethod.POST})
    public RestfulApiResponse bindingPhoneOrEmail() {
        return getSuccessfulApiResponse("tttt");
    }

}
