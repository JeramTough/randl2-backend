package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.jtweb.model.dto.PageDto;
import com.jeramtough.jtweb.model.params.QueryByPageParams;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.RandlPersonalInfoDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.personalinfo.UpdatePersonalInfoParams;
import com.jeramtough.randl2.service.randl.RandlPersonalInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-01-26
 */
@RestController
@Tag(name = "Randl普通用户个人信息接口")
@RequestMapping("/randlPersonalInfo")
public class RandlPersonalInfoController extends MyBaseController {

    private final RandlPersonalInfoService randlPersonalInfoService;

    @Autowired
    public RandlPersonalInfoController(
            RandlPersonalInfoService randlPersonalInfoService) {
        this.randlPersonalInfoService = randlPersonalInfoService;
    }

    @RegApi
    @Operation(summary = "查询一个", description = "查询一个普通用户个人信息")
    @RequestMapping(value = "/oneByUid", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "uid", description = "用户ID", required = true, example =
                    "1")})
    public CommonApiResponse<RandlPersonalInfoDto> getPersonalInfoByUid(Long uid) {
        return getSuccessfulApiResponse(randlPersonalInfoService.getPersonalInfoByUid(uid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新普通用户个人信息")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_102.C, description = ErrorU.CODE_102.M),
    })
    public CommonApiResponse<String> updateAdminUser(
            @RequestBody UpdatePersonalInfoParams params) {
        return getSuccessfulApiResponse(randlPersonalInfoService.updatePersonalInfo(params));
    }

    @RegApi
    @Operation(summary = "分页查询", description = "分页查询Randl用户个人信息")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public CommonApiResponse<PageDto<Map<String, Object>>> getRandlUserByPage(
            QueryByPageParams queryByPageParams) {
        return getSuccessfulApiResponse(
                randlPersonalInfoService.getRandlPersonalInfoDtoListByPage(queryByPageParams));
    }

}

