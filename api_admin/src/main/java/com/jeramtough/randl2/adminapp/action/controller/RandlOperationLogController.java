package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.component.optlog.annotation.IgnoreOptLog;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlOperationLogDto;
import com.jeramtough.randl2.common.model.params.optlog.ConditionOptionLogParams;
import com.jeramtough.randl2.service.randl.RandlOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2021-02-22
 */
@IgnoreOptLog
@Tag(name = "操作日志接口")
@RestController
@RequestMapping("/randlOperationLog")
public class RandlOperationLogController extends MyBaseController {

    private final RandlOperationLogService randlOperationLogService;

    @Autowired
    public RandlOperationLogController(
            RandlOperationLogService randlOperationLogService) {
        this.randlOperationLogService = randlOperationLogService;
    }


    @RegApi
    @Operation(summary = "删除", description = "删除操作日志")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    public CommonApiResponse<String> deleteApi(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(randlOperationLogService.removeOneById(fid));
    }


    @RegApi
    @Operation(summary = "查询一个", description = "查询一个操作日志")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    
    public CommonApiResponse<RandlOperationLogDto> getOne(Long fid) {
        return getSuccessfulApiResponse(randlOperationLogService.getBaseDtoById(fid));
    }

    @RegApi
    @Operation(summary = "查询全部", description = "查询全部操作日志")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<RandlOperationLogDto>> getAllApi() {
        return getSuccessfulApiResponse(randlOperationLogService.getAllBaseDto());
    }

    @RegApi
    @Operation(summary = "分页查询", description = "分页查询操作日志列表")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlOperationLogDto>> page(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlOperationLogService.getBaseDtoListByPage(queryByPageParams));
    }

    @RegApi
    @Operation(summary = "条件分页查询", description = "根据关键字等条件查询操作日志")
    @RequestMapping(value = "/condition", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<RandlOperationLogDto>> getRandlUserByCondition(
            QueryByPageParams queryByPageParams, ConditionOptionLogParams params) {
        return getSuccessfulApiResponse(randlOperationLogService.pageByCondition(queryByPageParams, params));
    }

}

