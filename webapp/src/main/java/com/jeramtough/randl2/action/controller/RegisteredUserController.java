package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.model.params.QueryByPageParams;
import com.jeramtough.randl2.model.params.registereduser.*;
import com.jeramtough.randl2.model.dto.PageDto;
import com.jeramtough.randl2.model.dto.RegisteredUserDto;
import com.jeramtough.randl2.service.RegisteredUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
public class RegisteredUserController extends BaseController {

    private final RegisteredUserService registeredUserService;

    @Autowired
    public RegisteredUserController(
            RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
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
                registeredUserService.verifyPhoneOrEmailForNew(params));
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
                registeredUserService.verifyPhoneOrEmailByForget(params));
    }

    @ApiOperation(value = "注册时用户密码校验", notes = "注册时用户密码校验")
    @RequestMapping(value = "/verify/password", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7020, message = "校验失败！两次密码不一致"),
            @ApiResponse(code = 7021, message = "密码长度范围在8-16位；只允许非空白任意字符"),
    })
    public CommonApiResponse<String> verifyPassword(@RequestBody VerifyPasswordParams params) {
        return getSuccessfulApiResponse(registeredUserService.verifyPassword(params));
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
        return getSuccessfulApiResponse(registeredUserService.verifyPasswordByForget(params));
    }

    @ApiOperation(value = "确定注册", notes = "注册该用户")
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7030, message = "注册未完成或信息以失效，请重新注册"),
            @ApiResponse(code = 7031, message = "验证码校验失败，或验证码未发送或以失效"),
    })
    public CommonApiResponse<RegisteredUserDto> registerUser(@RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(registeredUserService.register(params));
    }

    @ApiOperation(value = "确定重置", notes = "重置密码")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 7000, message = "事务ID以失效，注册信息失效，请重新开始注册"),
            @ApiResponse(code = 7040, message = "重置未完成或信息以失效，请重新开始重置流程"),
            @ApiResponse(code = 7041, message = "验证码校验失败，或验证码未发送或以失效"),
            @ApiResponse(code = 7042, message = "重置失败！账户信息未做过任何修改"),
    })
    public CommonApiResponse<RegisteredUserDto> resetUser(@RequestBody DoRegisterOrResetParams params) {
        return getSuccessfulApiResponse(registeredUserService.reset(params));
    }


    @ApiOperation(value = "查询全部", notes = "得到全部普通注册用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RegisteredUserDto>> getAllAdminUser() {
        return getSuccessfulApiResponse(registeredUserService.getAllBaseDto());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询普通注册用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RegisteredUserDto>> getAdminUserByPage(
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
    public CommonApiResponse<String> removeAdminUser(@RequestParam Long uid) {
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
    public CommonApiResponse<String> updateRegisteredUser(
            @RequestBody UpdateRegisteredUserParams params) {
        return getSuccessfulApiResponse(registeredUserService.updateRegisteredUser(params));
    }

    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个管理员用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @ApiResponses(value = {@ApiResponse(code = 7070, message = "查询失败！该用户不存在")})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RegisteredUserDto>> getRegisteredUsersByKeyword(@RequestParam String keyword) {
        return getSuccessfulApiResponse(
                registeredUserService.getRegisteredUsersByKeyword(keyword));
    }


}

