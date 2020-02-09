package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.bean.adminuser.RegisterAdminUserParams;
import com.jeramtough.randl2.bean.adminuser.UpdateAdminUserParams;
import com.jeramtough.randl2.service.AdminUserService;
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
@Api(tags = {"系统管理员接口"})
@RequestMapping("/adminUser")
public class AdminUserController extends BaseController {

    private AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }


    @ApiOperation(value = "登录", notes = "系统管理员登录")
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query",
                    required = true, dataType = "String", defaultValue = "superadmin"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query",
                    required = true, dataType = "String", defaultValue = "superadmin")})
    @ApiResponses(value = {
            @ApiResponse(code = 1001, message = "登录失败，请检查账号或密码"),
            @ApiResponse(code = 1002, message = "登录失败! [%s]参数不能为空"),
            @ApiResponse(code = 1004, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 1003, message = "用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符"),})
    public RestfulApiResponse login(@RequestParam(required = false) String username,
                                    @RequestParam(required = false) String password) {
        AdminUserCredentials adminUserCredentials = new AdminUserCredentials(username,
                password);
        return getSuccessfulApiResponse(adminUserService.adminLogin(adminUserCredentials));
    }

    @ApiOperation(value = "增加", notes = "添加一个管理员用户")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 1010, message = "执行失败! [%s]参数不能为空"),
            @ApiResponse(code = 1011, message = "添加管理员用户失败！两次密码不一致"),
            @ApiResponse(code = 1012, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 1013, message = "添加失败！存在同名用户名"),
            @ApiResponse(code = 1014, message = "用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符"),
            @ApiResponse(code = 1015, message = "手机号码格式错误"),
            @ApiResponse(code = 1016, message = "邮箱地址格式错误"),
            @ApiResponse(code = 1017, message = "已存在重复的手机号码，请换一个"),
            @ApiResponse(code = 1018, message = "已存在重复的邮箱地址，请换一个")
    })
    public RestfulApiResponse addAdminUser1(@RequestBody RegisterAdminUserParams params) {
        return getSuccessfulApiResponse(adminUserService.addAdminUser(params));
    }

    @ApiOperation(value = "查询全部", notes = "得到全部管理员用户信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllAdminUser() {
        return getSuccessfulApiResponse(adminUserService.getAllAdminUser());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询管理员用户信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public RestfulApiResponse getAdminUserByPage(
             QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                adminUserService.getAdminUserListByPage(queryByPageParams));
    }

    @ApiOperation(value = "查询一个", notes = "得到一个管理员用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户Id", paramType = "query",
                    required = true, dataType = "Long", defaultValue = "1")})
    @ApiResponses(value = {@ApiResponse(code = 1040, message = "查询失败！该用户不存在")})
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    public RestfulApiResponse getOneAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(adminUserService.getOneAdminUser(uid));
    }

    @ApiOperation(value = "移除", notes = "移除系统管理员账号")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "管理员用户Id", paramType = "query",
                    required = true)})
    @ApiResponses(value = {@ApiResponse(code = 1020, message = "移除管理员用户失败！请检查该用户是否存在")})
    public RestfulApiResponse removeAdminUser(@RequestParam Long uid) {
        return getSuccessfulApiResponse(adminUserService.removeAdminUser(uid));
    }

    @ApiOperation(value = "更新", notes = "更新系统管理员账号信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 1030, message = "更新失败! [%s]参数不能为空"),
            @ApiResponse(code = 1031, message = "更新失败！该管理员用户不存在！"),
            @ApiResponse(code = 1032, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 1033, message = "更新失败！存在同名用户名"),
            @ApiResponse(code = 1034, message = "用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符"),
            @ApiResponse(code = 1035, message = "手机号码格式错误"),
            @ApiResponse(code = 1036, message = "邮箱地址格式错误"),
            @ApiResponse(code = 1037, message = "已存在重复的手机号码，请换一个"),
            @ApiResponse(code = 1038, message = "已存在重复的邮箱地址，请换一个")
    })
    public RestfulApiResponse updateAdminUser(@RequestBody UpdateAdminUserParams params) {
        return getSuccessfulApiResponse(adminUserService.updateAdminUser(params));
    }


    @ApiOperation(value = "退出登录", notes = "系统管理员退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public RestfulApiResponse logout() {

        return getSuccessfulApiResponse(adminUserService.adminLogout());
    }

}

