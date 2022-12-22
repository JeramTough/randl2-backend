package com.jeramtough.randl2.adminapp.action.controller;


import com.jeramtough.jtweb.component.apiinfo.annotation.RegApi;
import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.common.action.controller.MyBaseController;
import com.jeramtough.randl2.common.model.dto.SourceSurfaceImageDto;
import com.jeramtough.randl2.common.model.error.ErrorU;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.common.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.service.resource.ResourceSurfaceImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Resource头像接口")
@RestController
@RequestMapping("/sourceSurfaceImage")
public class ResourceSurfaceImageController extends MyBaseController {

    private final ResourceSurfaceImageService surfaceImageService;

    public ResourceSurfaceImageController(
            ResourceSurfaceImageService surfaceImageService) {
        this.surfaceImageService = surfaceImageService;
    }

    @RegApi
    @Operation(summary = "上传|更新", description = "上传或者更新当前登录用户的头像")
    @RequestMapping(value = "/uploadAndUpdate", method = RequestMethod.POST,
            headers = {"content-type=multipart/form-data"}, consumes = {"multipart/*"})
    @Parameters({
            @Parameter(name = "file", description = "头像文件",
                    required = true)})
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_601.C, description = ErrorU.CODE_601.M),
            @ApiResponse(responseCode = ErrorU.CODE_602.C, description = ErrorU.CODE_602.M),
            @ApiResponse(responseCode = ErrorU.CODE_603.C, description = ErrorU.CODE_603.M),
    })
    public CommonApiResponse<String> uploadAndUpdateUserSurfaceImage(
            @RequestParam("file") MultipartFile file) {
        return getSuccessfulApiResponse(surfaceImageService.addUpdateSurfaceImage(file));
    }


    @RegApi
    @Operation(summary = "更新管理员", description = "更新当前登录管理员账户的头像")
    @RequestMapping(value = "/updateCurrentAdmin", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = ErrorU.CODE_604.C, description = ErrorU.CODE_604.M),
    })
    public CommonApiResponse<SourceSurfaceImageDto> updateAdminSurfaceImageByBase64(
            @RequestBody UpdateCurrentAdminSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.updateCurrentAdminSurfaceImageByBase64(params));
    }


    @RegApi
    @Operation(summary = "更新普通用户", description = "更新普通用户的头像")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    
    public CommonApiResponse<String> updateUserSurfaceImageByBase64(
            @RequestBody UpdateSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.updateSurfaceImageByBase64(params));
    }

    @RegApi
    @Operation(summary = "上传", description = "上传base64格式的头像图片")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    
    public CommonApiResponse<SourceSurfaceImageDto> uploadUserSurfaceImageByBase64(
            @RequestBody UploadSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.uploadSurfaceImageByBase64(params));
    }

    @RegApi
    @Operation(summary = "获取1", description = "根据uid得到用户base64头像")
    @RequestMapping(value = "/obtainByUid", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "uid", description = "用户ID", required = true)})
    
    public CommonApiResponse<String> getUserSurfaceImage(@RequestParam Long uid) {
        return getSuccessfulApiResponse(surfaceImageService.getUpdateSurfaceImageByUid(uid));
    }

    @RegApi
    @Operation(summary = "获取2", description = "根据图片ID得到用户base64头像")
    @RequestMapping(value = "/obtainById", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "fid", description = "图片ID", required = true)})
    
    public CommonApiResponse<String> getUserSurfaceImageById(@RequestParam Long fid) {
        return getSuccessfulApiResponse(surfaceImageService.getUpdateSurfaceImageById(fid));
    }


}

