package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.role.AddRoleParams;
import com.jeramtough.randl2.common.model.params.role.ConditionRoleParams;
import com.jeramtough.randl2.common.model.params.role.UpdateRoleParams;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.randl2.adminapp.service.RandlRoleService;
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
@ApiResponses({
        @ApiResponse(code = ErrorU.CODE_504.C, message = ErrorU.CODE_504.M),
})
@Api(tags = {"角色信息的接口"})
@RequestMapping("/randlRole")
public class RandlRoleController extends BaseSwaggerController {

    private final RandlRoleService randlRoleService;


    @Autowired
    public RandlRoleController(RandlRoleService randlRoleService) {
        this.randlRoleService = randlRoleService;
    }

    @LoggingOperation
    @ApiOperation(value = "新增", notes = "新增角色")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_503.C, message =
                    ErrorU.CODE_503.M),
    })
    public CommonApiResponse<String> addRole(@RequestBody AddRoleParams params) {
        return getSuccessfulApiResponse(randlRoleService.addRole(params));
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
        return getSuccessfulApiResponse(randlRoleService.deleteRole(fid));
    }

    @LoggingOperation
    @ApiOperation(value = "更新", notes = "更新角色")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_503.C, message =
                    ErrorU.CODE_503.M),
    })
    public CommonApiResponse<String> updateRole(@RequestBody UpdateRoleParams params) {
        return getSuccessfulApiResponse(randlRoleService.updateRole(params));
    }

    @ApiOperation(value = "查询一个", notes = "查询一个角色")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<RandlRoleDto> getRole(Long fid) {
        return getSuccessfulApiResponse(randlRoleService.getBaseDtoById(fid));
    }

    @ApiOperation(value = "查询根据appId和uid", notes = "查询一个角色根据appId和uid")
    @RequestMapping(value = "/oneByAppIdAndUid", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", paramType = "query",
                    required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "uid", value = "用户Id", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_502.C, message =
                    ErrorU.CODE_502.M),
    })
    public CommonApiResponse<RandlRoleDto> getRoleByAppIdAndUid(Long appId, Long uid) {
        return getSuccessfulApiResponse(randlRoleService.getRoleByAppIdAndUid(appId, uid));
    }

    @ApiOperation(value = "查询集合根据appId", notes = "查询集合根据appId")
    @RequestMapping(value = "/listByAppId", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用Id", paramType = "query",
                    required = true, defaultValue = "1")})
    public CommonApiResponse<List<RandlRoleDto>> getListByAppId(
            @RequestParam(value = "appId") Long appId) {
        return getSuccessfulApiResponse(randlRoleService.getListByAppId(appId));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部角色")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlRoleDto>> getAllRole() {
        return getSuccessfulApiResponse(randlRoleService.getAllBaseDto());
    }


    @ApiOperation(value = "分页查询", notes = "分页查询角色列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlRoleDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlRoleService.getBaseDtoListByPage(queryByPageParams));
    }

    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlRoleDto>> getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(randlRoleService.getRoleListByKeyword(keyword));
    }

    @ApiOperation(value = "条件查询", notes = "根据关键字等条件查询得到一个Randl应用信息")
    @ApiResponses(value = {})
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlRoleDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionRoleParams params) {
        return getSuccessfulApiResponse(randlRoleService.pageByCondition(queryByPageParams, params));
    }

}

