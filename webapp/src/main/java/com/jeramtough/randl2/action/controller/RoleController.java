package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.permission.AddApiParams;
import com.jeramtough.randl2.bean.permission.AddRoleParams;
import com.jeramtough.randl2.bean.permission.UpdateApiParams;
import com.jeramtough.randl2.bean.permission.UpdateRoleParams;
import com.jeramtough.randl2.service.RoleService;
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
@Api(tags = {"角色信息的接口"})
@RequestMapping("/role")
public class RoleController extends BaseSwaggerController {

    private final RoleService roleService;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "新增", notes = "新增系统角色")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 5000, message = "添加系统角色失败，[%s]参数不能为空"),
            @ApiResponse(code = 5001, message = "该系统角色已存在")
    })
    public RestfulApiResponse addRole(@RequestBody AddRoleParams params) {
        return getSuccessfulApiResponse(roleService.addRole(params));
    }

    @ApiOperation(value = "删除", notes = "删除系统角色")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiResponses(value = {
            @ApiResponse(code = 5010, message = "删除系统角色失败！该系统角色不存在"),
    })
    public RestfulApiResponse deleteRole(Long apiId) {
        return getSuccessfulApiResponse(roleService.deleteRole(apiId));
    }

    @ApiOperation(value = "更新", notes = "更新系统角色")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = 5020, message = "更新系统角色失败，[%s]参数不能为空"),
            @ApiResponse(code = 5021, message = "更新系统角色失败！该角色不存在")
    })
    public RestfulApiResponse updateRole(@RequestBody UpdateRoleParams params) {
        return getSuccessfulApiResponse(roleService.updateRole(params));
    }

    @ApiOperation(value = "查询一个", notes = "查询一个系统角色")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiResponses(value = {
            @ApiResponse(code = 5030, message = "查询系统角色失败！该系统角色不存在"),
    })
    public RestfulApiResponse getRole(Long apiId) {
        return getSuccessfulApiResponse(roleService.getRole(apiId));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统角色")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllRole() {
        return getSuccessfulApiResponse(roleService.getAllRole());
    }

}

