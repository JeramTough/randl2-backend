package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.model.dto.RandlRoleDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.role.AddRoleParams;
import com.jeramtough.randl2.common.model.params.role.ConditionRoleParams;
import com.jeramtough.randl2.common.model.params.role.UpdateRoleParams;
import com.jeramtough.randl2.service.randl.RandlRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        @ApiResponse(responseCode = ErrorU.CODE_504.C, description = ErrorU.CODE_504.M),
})
@Tag(name = "Randl角色信息的接口")
@RequestMapping("/randlRole")
public class RandlRoleController extends BaseSwaggerController {

    private final RandlRoleService randlRoleService;


    @Autowired
    public RandlRoleController(RandlRoleService randlRoleService) {
        this.randlRoleService = randlRoleService;
    }

    @RegApi
    @Operation(summary = "新增", description = "新增角色")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_503.C, description =
                    ErrorU.CODE_503.M),
    })
    public CommonApiResponse<String> addRole(@RequestBody AddRoleParams params) {
        return getSuccessfulApiResponse(randlRoleService.addRole(params));
    }

    @RegApi
    @Operation(summary = "删除", description = "删除角色")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_501.C, description =
                    ErrorU.CODE_501.M),
    })
    public CommonApiResponse<String> deleteRole(Long fid) {
        return getSuccessfulApiResponse(randlRoleService.deleteRole(fid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新角色")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_503.C, description =
                    ErrorU.CODE_503.M),
    })
    public CommonApiResponse<String> updateRole(@RequestBody UpdateRoleParams params) {
        return getSuccessfulApiResponse(randlRoleService.updateRole(params));
    }

    @RegApi
    @Operation(summary = "查询一个", description = "查询一个角色")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})

    public CommonApiResponse<RandlRoleDto> getRole(Long fid) {
        return getSuccessfulApiResponse(randlRoleService.getBaseDtoById(fid));
    }

    @RegApi
    @Operation(summary = "查询集合根据appId", description = "查询集合根据appId")
    @RequestMapping(value = "/listByAppId", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "appId", description = "应用Id",
                    required = true, example = "1")})
    public CommonApiResponse<List<RandlRoleDto>> getListByAppId(
            @RequestParam(value = "appId") Long appId) {
        return getSuccessfulApiResponse(randlRoleService.getListByAppId(appId));
    }

    @RegApi
    @Operation(summary = "查询全部", description = "查询全部角色")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlRoleDto>> getAllRole() {
        return getSuccessfulApiResponse(randlRoleService.getAllBaseDto());
    }

    @RegApi
    @Operation(summary = "分页查询", description = "分页查询角色列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlRoleDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlRoleService.getBaseDtoListByPage(queryByPageParams));
    }

    @RegApi
    @Operation(summary = "关键字查询", description = "根据关键字查询得到一个角色信息")
    @Parameters({
            @Parameter(name = "keyword", description = "关键字",
                    required = true, example = "username")})
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlRoleDto>> getOneAdminUser(
            @RequestParam String keyword) {
        return getSuccessfulApiResponse(randlRoleService.getRoleListByKeyword(keyword));
    }

    @RegApi
    @Operation(summary = "条件查询", description = "根据关键字等条件查询得到一个Randl应用信息")
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlRoleDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionRoleParams params) {
        return getSuccessfulApiResponse(
                randlRoleService.pageByCondition(queryByPageParams, params));
    }

}

