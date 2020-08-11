package com.pang.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.pang.commonutils.R;
import com.pang.vod.service.VideoService;
import com.pang.vod.util.AliyunVodSDKUtils;
import com.pang.vod.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description="阿里云视频点播微服务")
@CrossOrigin //跨域
@RestController
@RequestMapping("/eduVod/video")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * @Author: SmallPang
     * @Description: 上传视频
     * @Date: 2020/8/5
     * @Param file:
     * @return: com.pang.commonutils.R
     **/
    @PostMapping("/upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }

    /**
     * @Author: SmallPang
     * @Description: 删除视频
     * @Date: 2020/8/5
     * @Param videoId:
     * @return: com.pang.commonutils.R
     **/
    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId){

        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    /**
     * @Author: SmallPang
     * @Description: 批量删除视频
     * @Date: 2020/8/6
     * @Param videoIdList:
     * @return: com.pang.commonutils.R
     **/
    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List videoIdList){

        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    /**
     * @Author: SmallPang
     * @Description: 获得播放凭证
     * @Date: 2020/8/10
     * @Param videoId:
     * @return: com.pang.commonutils.R
     **/
    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }
}
