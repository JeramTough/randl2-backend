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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@RequestMapping("/randlApi")
@Tag(name = "Randl接口信息的接口")
public class RandlApiController extends MyBaseController {

    private final RandlApiService randlApiService;

    @Autowired
    public RandlApiController(RandlApiService randlApiService) {
        this.randlApiService = randlApiService;
    }

    @RegApi
    @Operation(summary = "新增", description = "新增系统接口")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})

    public CommonApiResponse<String> addApi(@RequestBody AddApiParams params) {
        return getSuccessfulApiResponse(randlApiService.addApi(params));
    }

    @RegApi
    @Operation(summary = "删除", description = "删除系统接口")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})

    public CommonApiResponse<String> deleteApi(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlApiService.delete(fid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新系统接口")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})

    public CommonApiResponse<String> updateApi(@RequestBody UpdateApiParams params) {
        return getSuccessfulApiResponse(randlApiService.updateApi(params));
    }

    @RegApi
    @Operation(summary = "查询一个", description = "查询一个系统接口")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})

    public CommonApiResponse<RandlApiDto> getApi(Long fid) {
        return getSuccessfulApiResponse(randlApiService.getApi(fid));
    }

    @RegApi
    @Operation(summary = "查询全部", description = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlApiDto>> getAllApi() {
        return getSuccessfulApiResponse(randlApiService.getAllApi());
    }

    @RegApi
    @Operation(summary = "查询应用下", description = "查询指定RandlApp应用下的所有接口")
    @RequestMapping(value = "/listByAppId", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "appId", description = "Randl应用Id",
                    required = true, example = "1")})
    public CommonApiResponse<List<RandlApiDto>> getAllByAppId(Long appId) {
        return getSuccessfulApiResponse(randlApiService.getListByAppId(appId));
    }

    @RegApi
    @Operation(summary = "分页查询", description = "分页查询API信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlApiDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlApiService.getBaseDtoListByPage(queryByPageParams));
    }


    @RegApi
    @Operation(summary = "关键字查询", description = "根据关键字查询得到一个api接口信息")
    @Parameters({
            @Parameter(name = "keyword", description = "关键字",
                    required = true, example = "username")})

    @RequestMapping(value = "/byKeyword", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlApiDto>> getOneAdminUser(@RequestParam String keyword) {
        return getSuccessfulApiResponse(randlApiService.getApiListByKeyword(keyword));
    }

    @RegApi
    @Operation(summary = "条件查询", description = "根据关键字等条件查询得到一个Randl应用信息")
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlApiDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionApiParams params) {
        return getSuccessfulApiResponse(randlApiService.pageByCondition(queryByPageParams,
                params));
    }

}
