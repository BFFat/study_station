package com.pang.edu.controller;


import com.pang.commonutils.R;
import com.pang.edu.entity.chapter.VideoVo;
import com.pang.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
@Api(description = "课时管理")
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public R save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody VideoVo videoVo){

        videoService.saveVideoInfo(videoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public R getVideoInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        VideoVo videoVo = videoService.getVideoInfoById(id);
        return R.ok().data("item", videoVo);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoVo videoVo,

            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        videoService.updateVideoInfoById(videoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){

        boolean result = videoService.removeVideoById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

