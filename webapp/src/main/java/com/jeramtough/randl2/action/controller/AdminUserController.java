package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.adminuser.AdminUserCredentials;
import com.jeramtough.randl2.bean.adminuser.RegisterAdminUserParams;
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
public class AdminUserController extends BaseSwaggerController {

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
    @ApiResponses(value = {@ApiResponse(code = 1001, message = "登录失败，请检查账号或密码")})
    public RestfulApiResponse login(@RequestParam String username,
                                    @RequestParam String password) {
        AdminUserCredentials adminUserCredentials = new AdminUserCredentials(username,
                password);
        return getSuccessfulApiResponse(adminUserService.adminLogin(adminUserCredentials));
    }

    @ApiOperation(value = "增加一个管理员用户", notes = "添加一个管理员用户")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username"),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "query",
                    required = true, dataType = "String", defaultValue = "password"),
            @ApiImplicitParam(name = "repeatedPassword", value = "重复密码", paramType = "query",
                    required = true, dataType = "String", defaultValue = "password")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 1010, message = "添加管理员用户失败! [%s]参数不能为空"),
            @ApiResponse(code = 1011, message = "添加管理员用户失败！两次密码不一致"),
            @ApiResponse(code = 1012, message = "密码长度范围在8-16位；只允许非空白任意字符"),
            @ApiResponse(code = 1013, message = "添加失败！存在同名用户名"),
            @ApiResponse(code = 1014, message = "用户名长度范围在5-16位；只能为数字或者字母；不能含有特殊字符")
    })
    public RestfulApiResponse addAdminUser(@RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String repeatedPassword) {
        RegisterAdminUserParams params = new RegisterAdminUserParams(username, password,
                repeatedPassword);
        return getSuccessfulApiResponse(adminUserService.addAdminUser(params));
    }

    @ApiOperation(value = "退出登录", notes = "系统管理员退出登录")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public RestfulApiResponse logout() {

        return getSuccessfulApiResponse(adminUserService.adminLogout());
    }

}

