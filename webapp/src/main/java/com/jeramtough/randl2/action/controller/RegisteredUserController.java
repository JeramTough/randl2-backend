package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.bean.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.bean.registereduser.UpdateRegisteredUserParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPasswordParams;
import com.jeramtough.randl2.bean.registereduser.VerifyPhoneOrEmailForNewParams;
import com.jeramtough.randl2.service.RegisteredUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RestfulApiResponse verifyPassword(@RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(registeredUserService.verifyPassword(params));
    }

    @ApiOperation(value = "注册", notes = "注册该用户")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7030, message = "注册未完成或信息以失效，或请重新注册"),
            @ApiResponse(code = 7031, message = "验证码校验失败，或验证码未发送或以失效"),
            @ApiResponse(code = 7032, message = "发送验证码的手机号或邮箱地址与注册的不符"),
    })
    public RestfulApiResponse registerUser() {
        return getSuccessfulApiResponse(registeredUserService.register());
    }

    @ApiOperation(value = "重置", notes = "重置密码")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7040, message = "注册验证码错误！"),
    })
    public void resetUser() {

    }


    @ApiOperation(value = "查询全部", notes = "得到全部普通注册用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllAdminUser() {
        return getSuccessfulApiResponse(registeredUserService.getAllBaseDto());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询普通注册用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public RestfulApiResponse getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                registeredUserService.getBaseDtoListByPage(queryByPageParams));
    }

    @ApiOperation(value = "移除", notes = "移除系统管理员账号")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "管理员用户Id", paramType = "query",
                    required = true)})
    @ApiResponses(value = {@ApiResponse(code = 7050, message = "移除普通注册用户失败！请检查该用户是否存在")})
    public RestfulApiResponse removeAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(registeredUserService.removeRegisteredUser(uid));
    }

    @ApiOperation(value = "更新", notes = "更新普通注册用户账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7060, message = "更新失败！该注册用户不存在！"),
            @ApiResponse(code = 7061, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 7062, message = "更新失败！存在同名账号名"),
            @ApiResponse(code = 7063, message = "用户名长度范围在5-12位；只能为数字或者字母；不能含有特殊字符"),
            @ApiResponse(code = 7064, message = "手机号码格式错误"),
            @ApiResponse(code = 7065, message = "邮箱地址格式错误"),
            @ApiResponse(code = 7066, message = "已存在重复的手机号码，请换一个"),
            @ApiResponse(code = 7067, message = "已存在重复的邮箱地址，请换一个"),
    })
    public RestfulApiResponse updateAdminUser(@RequestBody UpdateRegisteredUserParams params) {
        return getSuccessfulApiResponse(registeredUserService.updateRegisteredUser(params));
    }

}

