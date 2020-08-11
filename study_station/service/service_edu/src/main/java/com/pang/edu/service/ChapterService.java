package com.pang.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.edu.entity.Chapter;
import com.pang.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
public interface ChapterService extends IService<Chapter> {
    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);

    boolean removeByCourseId(String courseId);
}
