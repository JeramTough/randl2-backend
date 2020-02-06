package com.jeramtough.randl2.action.controller;


import com.jeramtough.jtlog.facade.L;
import com.jeramtough.jtweb.component.apiresponse.bean.RestfulApiResponse;
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

    @ApiOperation(value = "上传|更新", notes = "上传或者更新头像")
    @RequestMapping(value = "/upload", method = RequestMethod.POST,
            headers = {"content-type=multipart/form-data"}, consumes = {"multipart/*"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "头像文件", dataType = "file",
                    required = true, paramType = "form")})
    @ApiResponses(value = {
            @ApiResponse(code = 6000, message = "上传失败，上传数据为空"),
            @ApiResponse(code = 6001, message = "上传失败，头像图片大小不允许超过100kb"),
            @ApiResponse(code = 6002, message = "上传失败，图片格式只能为jpg或png")
    })
    public RestfulApiResponse uploadUserSurfaceImage(
            @RequestParam("file") MultipartFile file) {
        return getSuccessfulApiResponse(surfaceImageService.addUpdateSurfaceImage(file));
    }


}

