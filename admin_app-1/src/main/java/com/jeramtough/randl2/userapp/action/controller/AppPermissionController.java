package com.jeramtough.randl2.userapp.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.params.permission.PermissionParams;
import com.jeramtough.randl2.common.model.dto.PermissionDto;
import com.jeramtough.randl2.userapp.service.AppPermissionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/appPermission")
public class AppPermissionController extends BaseSwaggerController {

    private final AppPermissionService appPermissionService;

    @Autowired
    public AppPermissionController(
            AppPermissionService appPermissionService) {
        this.appPermissionService = appPermissionService;
    }

    @LoggingOperation
    @ApiOperation(value = "设置", notes = "系统管理员增加API接口权限")
    @RequestMapping(value = "/set", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> addPermissions(
            @RequestBody PermissionParams permissionParams) {
        return getSuccessfulApiResponse(appPermissionService.setPermissions(permissionParams));
    }


    /*@ApiOperation(value = "移除", notes = "系统管理员移除API接口权限")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 3000, message = "roleId参数不能为空"),
            @ApiResponse(code = 3001, message = "该角色roleId不存在")
    })
    public RestfulApiResponse removePermissions(
            @RequestBody PermissionParams permissionParams) {
        return getSuccessfulApiResponse(appPermissionService.removePermissions(permissionParams));
    }*/

    @ApiOperation(value = "查询全部", notes = "查询全部角色的API接口权限信息")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<PermissionDto>> getAllPermissions() {
        return getSuccessfulApiResponse(appPermissionService.getPermissions());
    }

    @ApiOperation(value = "根据角色ID查询", notes = "根据角色ID查询API接口权限信息")
    @RequestMapping(value = "/byRole", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query",
                    required = true, defaultValue = "1")})
    public CommonApiResponse<List<PermissionDto>> getAllPermissions(@RequestParam Long roleId) {

        return getSuccessfulApiResponse(appPermissionService.getPermissionListByRoleId(roleId));
    }

}

