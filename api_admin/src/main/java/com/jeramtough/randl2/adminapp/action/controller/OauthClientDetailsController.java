package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthClientDetailsDto;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthClientDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthClientDetailsParams;
import com.jeramtough.randl2.service.oauth.OauthClientDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-08-06
 */
@Tag(name = "OAuth2客户端接口")
@RestController
@RequestMapping("/oauthClientDetails")
public class OauthClientDetailsController extends MyBaseController {

    private final OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    public OauthClientDetailsController(
            OauthClientDetailsService oauthClientDetailsService) {
        this.oauthClientDetailsService = oauthClientDetailsService;
    }

    @RegApi
    @Operation(summary = "新增", description = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})

    public CommonApiResponse<String> add(@RequestBody AddOauthClientDetailsParams params) {
        return getSuccessfulApiResponse(oauthClientDetailsService.add(params));
    }

    @RegApi
    @Operation(summary = "删除", description = "删除")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})

    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(oauthClientDetailsService.removeOneById(fid));
    }

    @RegApi
    @Operation(summary = "更新", description = "更新")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public CommonApiResponse<String> update(
            @RequestBody UpdateOauthClientDetailsParams params) {
        return getSuccessfulApiResponse(oauthClientDetailsService.updateByParams(params));
    }

    @RegApi
    @Operation(summary = "查询一个", description = "查询一个")
    @RequestMapping(value = "/one", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "fid", description = "ID",
                    required = true, example = "1")})

    public CommonApiResponse<OauthClientDetailsDto> getOne(Long fid) {
        return getSuccessfulApiResponse(oauthClientDetailsService.getBaseDtoById(fid));
    }

    @RegApi
    @Operation(summary = "根据AppId查询一个", description = "根据AppId查询一个")
    @RequestMapping(value = "/oneByAppId", method = {RequestMethod.GET})
    @Parameters({
            @Parameter(name = "appId", description = "AppId",
                    required = true, example = "1")})

    public CommonApiResponse<OauthClientDetailsDto> getOneByAppId(Long appId) {
        return getSuccessfulApiResponse(oauthClientDetailsService.getOneByAppId(appId));
    }


}

