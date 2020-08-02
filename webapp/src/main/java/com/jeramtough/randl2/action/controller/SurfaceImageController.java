package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtweb.component.apiresponse.bean.CommonApiResponse;
import com.jeramtough.randl2.model.params.surfaceimage.UpdateCurrentAdminSurfaceImageParams;
import com.jeramtough.randl2.model.params.surfaceimage.UpdateSurfaceImageParams;
import com.jeramtough.randl2.model.params.surfaceimage.UploadSurfaceImageParams;
import com.jeramtough.randl2.model.dto.SurfaceImageDto;
import com.jeramtough.randl2.service.SurfaceImageService;
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
@RequestMapping("/surfaceImage")
public class SurfaceImageController extends BaseController {

    private final SurfaceImageService surfaceImageService;

    public SurfaceImageController(
            SurfaceImageService surfaceImageService) {
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
            @ApiResponse(code = 6000, message = "上传失败，上传数据为空"),
            @ApiResponse(code = 6001, message = "上传失败，头像图片大小不允许超过500kb"),
            @ApiResponse(code = 6002, message = "上传失败，图片格式只能为jpg或png")
    })
    public CommonApiResponse<String> uploadAndUpdateUserSurfaceImage(
            @RequestParam("file") MultipartFile file) {
        return getSuccessfulApiResponse(surfaceImageService.addUpdateSurfaceImage(file));
    }

    @ApiOperation(value = "更新管理员", notes = "更新当前登录管理员账户的头像")
    @RequestMapping(value = "/updateCurrentAdmin", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 6030, message = "管理员账户头像不允许被修改")
    })
    public CommonApiResponse<SurfaceImageDto> updateAdminSurfaceImageByBase64(
            @RequestBody UpdateCurrentAdminSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.updateCurrentAdminSurfaceImageByBase64(params));
    }


    @ApiOperation(value = "更新普通用户", notes = "更新普通用户的头像")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public CommonApiResponse<String> updateUserSurfaceImageByBase64(
            @RequestBody UpdateSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.updateSurfaceImageByBase64(params));
    }

    @ApiOperation(value = "上传", notes = "上传base64格式的头像图片")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiResponses(value = {
    })
    public CommonApiResponse<SurfaceImageDto> uploadUserSurfaceImageByBase64(
            @RequestBody UploadSurfaceImageParams params) {
        return getSuccessfulApiResponse(
                surfaceImageService.uploadSurfaceImageByBase64(params));
    }

    @ApiOperation(value = "获取1", notes = "根据uid得到用户base64头像")
    @RequestMapping(value = "/obtainByUid", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", required = true)})
    @ApiResponses(value = {
            @ApiResponse(code = 6010, message = "该用户ID不存在")
    })
    public CommonApiResponse<String> getUserSurfaceImage(@RequestParam Long uid) {
        return getSuccessfulApiResponse(surfaceImageService.getUpdateSurfaceImageByUid(uid));
    }

    @ApiOperation(value = "获取2", notes = "根据图片ID得到用户base64头像")
    @RequestMapping(value = "/obtainById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "图片ID", required = true)})
    @ApiResponses(value = {
            @ApiResponse(code = 6020, message = "该图片ID不存在")
    })
    public CommonApiResponse<String> getUserSurfaceImageById(@RequestParam Long fid) {
        return getSuccessfulApiResponse(surfaceImageService.getUpdateSurfaceImageById(fid));
    }


}

