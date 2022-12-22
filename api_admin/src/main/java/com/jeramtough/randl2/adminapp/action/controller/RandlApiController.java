package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlApiDto;
import com.jeramtough.randl2.common.model.params.api.AddApiParams;
import com.jeramtough.randl2.common.model.params.api.ConditionApiParams;
import com.jeramtough.randl2.common.model.params.api.UpdateApiParams;
import com.jeramtough.randl2.service.randl.RandlApiService;
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
@RequestMapping("/randlApi")
@Api(tags = {"Randl接口信息的接口"})
public class RandlApiController extends MyBaseController {

    private final RandlApiService randlApiService;

    @Autowired
    public RandlApiController(RandlApiService randlApiService) {
        this.randlApiService = randlApiService;
    }

    @RegApi
    @ApiOperation(value = "新增", notes = "新增系统接口")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> addApi(@RequestBody AddApiParams params) {
        return getSuccessfulApiResponse(randlApiService.addApi(params));
    }

    @RegApi
    @ApiOperation(value = "删除", notes = "删除系统接口")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> deleteApi(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlApiService.delete(fid));
    }

    @RegApi
    @ApiOperation(value = "更新", notes = "更新系统接口")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateApi(@RequestBody UpdateApiParams params) {
        return getSuccessfulApiResponse(randlApiService.updateApi(params));
    }

    @RegApi
    @ApiOperation(value = "查询一个", notes = "查询一个系统接口")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<RandlApiDto> getApi(Long fid) {
        return getSuccessfulApiResponse(randlApiService.getApi(fid));
    }

    @RegApi
    @ApiOperation(value = "查询全部", notes = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlApiDto>> getAllApi() {
        return getSuccessfulApiResponse(randlApiService.getAllApi());
    }

    @RegApi
    @ApiOperation(value = "查询应用下", notes = "查询指定RandlApp应用下的所有接口")
    @RequestMapping(value = "/listByAppId", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "Randl应用Id", paramType = "query",
                    required = true, defaultValue = "1")})
    public CommonApiResponse<List<RandlApiDto>> getAllByAppId(Long appId) {
        return getSuccessfulApiResponse(randlApiService.getListByAppId(appId));
    }

    @RegApi
    @ApiOperation(value = "分页查询", notes = "分页查询API信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlApiDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlApiService.getBaseDtoListByPage(queryByPageParams));
    }


    @RegApi
    @ApiOperation(value = "关键字查询", notes = "根据关键字查询得到一个api接口信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query",
                    required = true, dataType = "String", defaultValue = "username")})
    @ApiResponses(value = {
    })
    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlApiDto>> getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(randlApiService.getApiListByKeyword(keyword));
    }

    @RegApi
    @ApiOperation(value = "条件查询", notes = "根据关键字等条件查询得到一个Randl应用信息")
    @ApiResponses(value = {})
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlApiDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionApiParams params) {
        return getSuccessfulApiResponse(randlApiService.pageByCondition(queryByPageParams,
                params));
    }

}
