package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlAppDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.app.AddAppParams;
import com.jeramtough.randl2.common.model.params.app.ConditionAppParams;
import com.jeramtough.randl2.common.model.params.app.UpdateAppParams;
import com.jeramtough.randl2.service.randl.RandlAppService;
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
 * @since 2020-10-03
 */
@ApiResponses({
        @ApiResponse(responseCode = ErrorU.CODE_701.C, description = ErrorU.CODE_701.M),
})
@Tag(name = "RandlAPP应用接口")
@RestController
@RequestMapping("/randlApp")
public class RandlAppController extends MyBaseController {

    private final RandlAppService randlAppService;

    @Autowired
    public RandlAppController(RandlAppService randlAppService) {
        this.randlAppService = randlAppService;
    }

    @RegApi
    @Operation(summary = "新增", description = "新增RandlApp应用")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    
    public CommonApiResponse<String> add(@RequestBody AddAppParams params) {
        return getSuccessfulApiResponse(randlAppService.add(params));
    }

    @RegApi
    @Operation(summary = "删除", description = "删除RandlApp应用")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlAppService.removeOneById(fid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新RandlApp应用")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public CommonApiResponse<String> update(@RequestBody UpdateAppParams params) {
        return getSuccessfulApiResponse(randlAppService.update(params));
    }

    @RegApi
    @Operation(summary = "查询一个", description = "查询一个RandlApp应用")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID", 
                    required = true, example = "1")})
    public CommonApiResponse<RandlAppDto> getOne(Long fid) {
        return getSuccessfulApiResponse(randlAppService.getBaseDtoById(fid));
    }

    @RegApi
    @Operation(summary = "查询全部", description = "查询全部RandlApp应用")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlAppDto>> getAll() {
        return getSuccessfulApiResponse(randlAppService.getAllBaseDto());
    }

    @RegApi
    @Operation(summary = "查询全部但只有name属性", description = "查询全部RandlApp应用，但只有name属性")
    @RequestMapping(value = "/allOnlyName", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlAppDto>> getAllOnlyName() {
        return getSuccessfulApiResponse(randlAppService.getAllOnlyName());
    }

    @RegApi
    @Operation(summary = "分页查询", description = "分页查询APP信息列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlAppDto>> getAdminUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlAppService.getBaseDtoListByPage(queryByPageParams));
    }


    @RegApi
    @Operation(summary = "条件查询", description = "根据关键字等条件查询得到一个Randl应用信息")
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlAppDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionAppParams params) {
        return getSuccessfulApiResponse(randlAppService.pageByCondition(queryByPageParams, params));
    }

}

