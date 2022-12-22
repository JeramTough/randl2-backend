package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.OauthResourceDetailsDto;
import com.jeramtough.randl2.common.model.params.oauth.AddOauthResourceDetailsParams;
import com.jeramtough.randl2.common.model.params.oauth.UpdateOauthResourceDetailsParams;
import com.jeramtough.randl2.service.oauth.OauthResourceDetailsService;
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
 * @since 2021-02-02
 */
@Api(tags = {"Oauth2资源服务接口"})
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
    @ApiOperation(value = "查询全部", notes = "查询全部系统接口")
    @RequestMapping(value = "/all", method = {RequestMethod.GET})
    public CommonApiResponse<List<OauthResourceDetailsDto>> getAll() {
        return getSuccessfulApiResponse(oauthResourceDetailsService.getAllBaseDto());
    }

    @RegApi
    @ApiOperation(value = "根据AppId查询一个", notes = "根据AppId查询一个")
    @RequestMapping(value = "/oneByAppId", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "AppId", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<OauthResourceDetailsDto> getOneByAppId(Long appId) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.getOneByAppId(appId));
    }

    @RegApi
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> add(@RequestBody AddOauthResourceDetailsParams params) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.add(params));
    }

    @RegApi
    @ApiOperation(value = "更新", notes = "更新")
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> update(@RequestBody UpdateOauthResourceDetailsParams params) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.update(params));
    }

    @RegApi
    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/remove", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "ID", paramType = "query",
                    required = true, defaultValue = "1")})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> removeOneById(@RequestParam("fid") Long fid) {
        return getSuccessfulApiResponse(oauthResourceDetailsService.removeById(fid));
    }

}

