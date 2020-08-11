package com.pang.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pang.edu.entity.Course;
import com.pang.edu.entity.course.CoursePublishVo;
import com.pang.edu.entity.vo.CourseInfoForm;
import com.pang.edu.entity.vo.CourseQuery;
import com.pang.edu.entity.vo.CourseQueryVo;
import com.pang.edu.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    CoursePublishVo selectCoursePublishVoById(String id);

    boolean publishCourseById(String id);

    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    boolean removeCourseById(String id);

    List<Course> selectByTeacherId(String id);

    Map<String, Object> pageListWeb(Page<Course> pageParam, CourseQueryVo courseQuery);

    /**
     * 获取课程信息
     * @param courseId
     * @return
     */
    CourseWebVo selectInfoWebById(String courseId);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);

    CourseInfoForm getCourseInfo(String courseId);
}
