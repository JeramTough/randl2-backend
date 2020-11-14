package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.BaseController;
import com.jeramtough.randl2.common.component.logforoperation.annotation.LoggingOperation;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.common.model.dto.SourceSurfaceImageDto;
import com.jeramtough.randl2.service.source.SourceSurfaceImageService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author JeramTough
 * @since 2020-02-06
 */
@Api(tags = {"头像接口"})
@RestController
@RequestMapping("/sourceSurfaceImage")
public class SourceSurfaceImageController extends BaseController {

    private final SourceSurfaceImageService surfaceImageService;

    public SourceSurfaceImageController(
            SourceSurfaceImageService surfaceImageService) {
        this.surfaceImageService = surfaceImageService;
    }

    @ApiOperation(value = "上传|更新", notes = "上传或者更新当前登录用户的头像")
    @RequestMapping(value = "/uploadAndUpdate", method = RequestMethod.POST,
            headers = {"content-type=multipart/form-data"}, consumes = {"multipart/*"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "头像文件", dataType = "MultipartFile",
                    allowMultiple = true,
                    required = true, paramType = "form")})
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_601.C, message = ErrorU.CODE_601.M),
            @ApiResponse(code = ErrorU.CODE_602.C, message = ErrorU.CODE_602.M),
            @ApiResponse(code = ErrorU.CODE_603.C, message = ErrorU.CODE_603.M),
    })
    public CommonApiResponse<String> uploadAndUpdateUserSurfaceImage(
            @RequestParam("file") MultipartFile file) {
        return getSuccessfulApiResponse(surfaceImageService.addUpdateSurfaceImage(file));
    }

    @LoggingOperation
    @ApiOperation(value = "更新管理员", notes = "更新当前登录管理员账户的头像")
    @RequestMapping(value = "/updateCurrentAdmin", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = ErrorU.CODE_604.C, message = ErrorU.CODE_604.M),
    })
    public CommonApiResponse<SourceSurfaceImageDto> updateAdminSurfaceImageByBase64(
            @RequestBody UpdateCurrentAdminSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.updateCurrentAdminSurfaceImageByBase64(params));
    }


    @LoggingOperation
    @ApiOperation(value = "更新普通用户", notes = "更新普通用户的头像")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateUserSurfaceImageByBase64(
            @RequestBody UpdateSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.updateSurfaceImageByBase64(params));
    }

    @LoggingOperation
    @ApiOperation(value = "上传", notes = "上传base64格式的头像图片")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public CommonApiResponse<SourceSurfaceImageDto> uploadUserSurfaceImageByBase64(
            @RequestBody UploadSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.uploadSurfaceImageByBase64(params));
    }

    @ApiOperation(value = "获取1", notes = "根据uid得到用户base64头像")
    @RequestMapping(value = "/obtainByUid", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", required = true)})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> getUserSurfaceImage(@RequestParam Long uid) {
        return getSuccessfulApiResponse(surfaceImageService.getUpdateSurfaceImageByUid(uid));
    }

    @ApiOperation(value = "获取2", notes = "根据图片ID得到用户base64头像")
    @RequestMapping(value = "/obtainById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "图片ID", required = true)})
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> getUserSurfaceImageById(@RequestParam Long fid) {
        return getSuccessfulApiResponse(surfaceImageService.getUpdateSurfaceImageById(fid));
    }


}

