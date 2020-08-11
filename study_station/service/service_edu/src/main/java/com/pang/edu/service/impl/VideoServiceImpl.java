package com.pang.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.base.exceptionhandler.GuliException;
import com.pang.commonutils.R;
import com.pang.edu.client.VodClient;
import com.pang.edu.entity.Video;
import com.pang.edu.entity.chapter.VideoVo;
import com.pang.edu.mapper.VideoMapper;
import com.pang.edu.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    private final VodClient vodClient;

    public VideoServiceImpl(VodClient vodClient) {
        this.vodClient = vodClient;
    }

    @Override
    public boolean getCountByChapterId(String id) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void saveVideoInfo(VideoVo videoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoVo, video);
        boolean result = this.save(video);

        if(!result){
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    @Override
    public VideoVo getVideoInfoById(String id) {
        //从video表中取数据
        Video video = this.getById(id);
        if(video == null){
            throw new GuliException(20001, "数据不存在");
        }

        //创建videoInfoForm对象
        VideoVo videoVo = new VideoVo();
        BeanUtils.copyProperties(video, videoVo);

        return videoVo;
    }

    @Override
    public void updateVideoInfoById(VideoVo videoVo) {
        //保存课时基本信息
        Video video = new Video();
        BeanUtils.copyProperties(videoVo, video);
        boolean result = this.updateById(video);
        if(!result){
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {
        //删除视频资源 TODO
        //查询云端视频id
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.removeVideo(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "视频删除失败，熔断器...");
            }
        }

        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(queryWrapper);

        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            Video video = videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }

        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.removeVideoList(videoSourceIdList);
        }

        //删除video表示的记录
        QueryWrapper<Video> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper2);
        return null != count && count > 0;
    }
}
