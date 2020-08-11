package com.pang.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pang.commonutils.R;
import com.pang.edu.entity.Course;
import com.pang.edu.entity.Teacher;
import com.pang.edu.service.CourseService;
import com.pang.edu.service.TeacherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduService/index")
@CrossOrigin
public class IndexController {

    private final CourseService courseService;

    private final TeacherService teacherService;

    public IndexController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    //查询前8条热门课程，查询前4条名师
    @GetMapping("/index")
    public R index() {
        //查询前8条热门课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<Teacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("courseList",eduList).data("teacherList",teacherList);
    }
}