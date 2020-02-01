package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.permission.PermissionParams;
import com.jeramtough.randl2.service.PermissionService;
import io.swagger.annotations.Api;
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
@Api(tags = {"控制接口权限的接口"})
@RequestMapping("/permission")
public class PermissionController extends BaseSwaggerController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(
            PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "添加", notes = "系统管理员增加API接口权限")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 3000, message = "roleId参数不能为空"),
            @ApiResponse(code = 3001, message = "该角色roleId不存在")
    })
    public RestfulApiResponse addPermissions(
            @RequestBody PermissionParams permissionParams) {
        return getSuccessfulApiResponse(permissionService.addPermissions(permissionParams));
    }


    @ApiOperation(value = "移除", notes = "系统管理员移除API接口权限")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 3000, message = "roleId参数不能为空"),
            @ApiResponse(code = 3001, message = "该角色roleId不存在")
    })
    public RestfulApiResponse removePermissions(
            @RequestBody PermissionParams permissionParams) {
        return getSuccessfulApiResponse(permissionService.removePermissions(permissionParams));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部角色的API接口权限信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllPermissions() {
        return getSuccessfulApiResponse(permissionService.getPermissions());
    }

}

