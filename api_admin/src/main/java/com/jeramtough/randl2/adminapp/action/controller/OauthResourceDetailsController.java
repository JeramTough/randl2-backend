package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthResourceDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthResourceDetailsParams;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
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
 * @since 2021-02-02
 */
@Tag(name = "Oauth2资源服务接口")
@RestController
@RequestMapping("/oauthResourceDetails")
public class OauthResourceDetailsController extends MyBaseController {

    private final OauthResourceDetailsService oauthResourceDetailsService;

    @Autowired
    public OauthResourceDetailsController(
            OauthResourceDetailsService oauthResourceDetailsService) {
        this.oauthResourceDetailsService = oauthResourceDetailsService;
    }

    @RegApi
    @Operation(summary = "查询全部", description = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<OauthResourceDetailsDto>> getAll() {
        return getSuccessfulApiResponse(oauthResourceDetailsService.getAllBaseDto());
    }

    @RegApi
    @Operation(summary = "根据AppId查询一个", description = "根据AppId查询一个")
    @RequestMapping(value = "/oneByAppId", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "appId", description = "AppId",
                    required = true, example = "1")})
    
    public CommonApiResponse<OauthResourceDetailsDto> getOneByAppId(Long appId) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.getOneByAppId(appId));
    }

    @RegApi
    @Operation(summary = "新增", description = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    
    public CommonApiResponse<String> add(@RequestBody AddOauthResourceDetailsParams params) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.add(params));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    
    public CommonApiResponse<String> update(@RequestBody UpdateOauthResourceDetailsParams params) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.update(params));
    }

    @RegApi
    @Operation(summary = "删除", description = "删除")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})
    
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.removeById(fid));
    }

}

