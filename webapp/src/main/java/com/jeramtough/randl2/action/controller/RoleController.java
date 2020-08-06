package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.model.error.ErrorU;
import com.jeramtough.randl2.model.params.QueryByPageParams;
import com.jeramtough.randl2.model.params.permission.AddRoleParams;
import com.jeramtough.randl2.model.params.permission.UpdateRoleParams;
import com.jeramtough.randl2.model.dto.PageDto;
import com.jeramtough.randl2.model.dto.RoleDto;
import com.jeramtough.randl2.service.RoleService;
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
@Api(tags = {"角色信息的接口"})
@RequestMapping("/role")
public class RoleController extends BaseSwaggerController {

    private final RoleService roleService;


    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @LoggingOperation
    @ApiOperation(value = "新增", notes = "新增角色")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> addRole(@RequestBody AddRoleParams params) {
        return getSuccessfulApiResponse(roleService.addRole(params));
    }

    @LoggingOperation
    @ApiOperation(value = "删除", notes = "删除角色")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_501.C, message =
                    ErrorU.CODE_501.M),
    })
    public CommonApiResponse<String> deleteRole(Long fid) {
        return getSuccessfulApiResponse(roleService.deleteRole(fid));
    }

    @LoggingOperation
    @ApiOperation(value = "更新", notes = "更新角色")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateRole(@RequestBody UpdateRoleParams params) {
        return getSuccessfulApiResponse(roleService.updateRole(params));
    }

    @ApiOperation(value = "查询一个", notes = "查询一个角色")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<RoleDto> getRole(Long fid) {
        return getSuccessfulApiResponse(roleService.getRole(fid));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部角色")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RoleDto>> getAllRole() {
        return getSuccessfulApiResponse(roleService.getAllRole());
    }

    @ApiOperation(value = "管理员角色全部", notes = "查询全部系统管理员角色")
    @RequestMapping(value = "/adminAll", method = {RequestMethod.GET})
    public CommonApiResponse<List<RoleDto>> getAllAdminRole() {
        return getSuccessfulApiResponse(roleService.getAllAdminRole());
    }

    @ApiOperation(value = "普通用户角色全部", notes = "查询全部普通用户角色")
    @RequestMapping(value = "/userAll", method = {RequestMethod.GET})
    public CommonApiResponse<List<RoleDto>> getAllUserRole() {
        return getSuccessfulApiResponse(roleService.getAllUserRole());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询角色列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RoleDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                roleService.getBaseDtoListByPage(queryByPageParams));
    }

    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @ApiResponses(value = {@ApiResponse(code = 5040, message = "查询失败！该角色不存在")})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RoleDto>> getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(roleService.getRoleListByKeyword(keyword));
    }

}

