package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.action.controller.BaseSwaggerController;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.model.dto.AppApiDto;
import com.jeramtough.randl2.common.model.params.permission.AddApiParams;
import com.jeramtough.randl2.common.model.params.permission.UpdateApiParams;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.randl2.adminapp.service.AppApiService;
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
@RequestMapping("/appApi")
@Api(tags = {"接口信息的接口"})
public class AppApiController extends BaseSwaggerController {

    private final AppApiService appApiService;

    @Autowired
    public AppApiController(AppApiService appApiService) {
        this.appApiService = appApiService;
    }

    @ApiOperation(value = "新增", notes = "新增系统接口")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> addApi(@RequestBody AddApiParams params) {
        return getSuccessfulApiResponse(appApiService.addApi(params));
    }

    @ApiOperation(value = "删除", notes = "删除系统接口")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> deleteApi(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(appApiService.delete(fid));
    }

    @ApiOperation(value = "更新", notes = "更新系统接口")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateApi(@RequestBody UpdateApiParams params) {
        return getSuccessfulApiResponse(appApiService.updateApi(params));
    }

    @ApiOperation(value = "查询一个", notes = "查询一个系统接口")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<AppApiDto> getApi(Long fid) {
        return getSuccessfulApiResponse(appApiService.getApi(fid));
    }

    @ApiOperation(value = "查询全部", notes = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<AppApiDto>> getAllApi() {
        return getSuccessfulApiResponse(appApiService.getAllApi());
    }

    @ApiOperation(value = "分页查询", notes = "分页查询API信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<AppApiDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                appApiService.getBaseDtoListByPage(queryByPageParams));
    }


    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个api接口信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @ApiResponses(value = {
    })
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<AppApiDto>> getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(appApiService.getApiListByKeyword(keyword));
    }

}
