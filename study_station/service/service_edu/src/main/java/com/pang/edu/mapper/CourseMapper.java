package com.pang.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pang.edu.entity.Course;
import com.pang.edu.entity.course.CoursePublishVo;
import com.pang.edu.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo selectCoursePublishVoById(String id);

    CourseWebVo selectInfoWebById(String id);
}
