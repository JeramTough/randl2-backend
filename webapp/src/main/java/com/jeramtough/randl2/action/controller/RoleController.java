package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
import com.jeramtough.randl2.bean.QueryByPageParams;
import com.jeramtough.randl2.bean.permission.AddRoleParams;
import com.jeramtough.randl2.bean.permission.UpdateRoleParams;
import com.jeramtough.randl2.service.RoleService;
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
            @ApiResponse(code = 5001, message = "该系统角色已存在"),
            @ApiResponse(code = 5002, message = "角色名只能为英文字母")
    })
    public RestfulApiResponse addRole(@RequestBody AddRoleParams params) {
        return getSuccessfulApiResponse(roleService.addRole(params));
    }

    @ApiOperation(value = "删除", notes = "删除系统角色")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
            @ApiResponse(code = 5010, message = "删除系统角色失败！该系统角色不存在"),
    })
    public RestfulApiResponse deleteRole(Long fid) {
        return getSuccessfulApiResponse(roleService.deleteRole(fid));
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
            @ApiResponse(code = 5030, message = "查询系统角色失败！该系统角色不存在"),
    })
    public RestfulApiResponse getRole(Long fid) {
        return getSuccessfulApiResponse(roleService.getRole(fid));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统角色")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public RestfulApiResponse getAllRole() {
        return getSuccessfulApiResponse(roleService.getAllRole());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询角色列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public RestfulApiResponse getAdminUserByPage(
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
    public RestfulApiResponse getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(roleService.getRoleListByKeyword(keyword));
    }

}

