package com.pang.edu.service;

import com.pang.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.edu.entity.chapter.VideoVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
public interface VideoService extends IService<Video> {

    boolean getCountByChapterId(String id);

    void saveVideoInfo(VideoVo videoVo);

    VideoVo getVideoInfoById(String id);

    void updateVideoInfoById(VideoVo videoVo);

    boolean removeVideoById(String id);

    boolean removeByCourseId(String courseId);
}
