package com.pang.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.commonutils.JwtUtils;
import com.pang.commonutils.R;
import com.pang.edu.client.OrderClient;
import com.pang.edu.entity.Course;
import com.pang.edu.entity.chapter.ChapterVo;
import com.pang.edu.entity.course.CoursePublishVo;
import com.pang.edu.entity.vo.CourseInfoForm;
import com.pang.edu.entity.vo.CourseQuery;
import com.pang.edu.entity.vo.CourseQueryVo;
import com.pang.edu.entity.vo.CourseWebVo;
import com.pang.edu.service.ChapterService;
import com.pang.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-04
 */
@Api(description = "课程管理")
@CrossOrigin
@RestController
@RequestMapping("/eduService/course")
public class CourseController {

    private final CourseService courseService;

    private final ChapterService chapterService;

    private final OrderClient orderClient;

    public CourseController(CourseService courseService, ChapterService chapterService, OrderClient orderClient) {
        this.courseService = courseService;
        this.chapterService = chapterService;
        this.orderClient = orderClient;
    }

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm){

        String courseId = courseService.saveCourseInfo(courseInfoForm);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId", courseId);
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm,

            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        CoursePublishVo courseInfoForm = courseService.selectCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        boolean rtnNum = courseService.publishCourseById(id);
        if (rtnNum) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery){

        Page<Course> pageParam = new Page<>(page, limit);

        courseService.pageQuery(pageParam, courseQuery);
        List<Course> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){

        boolean result = courseService.removeCourseById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }

    @ApiOperation(value = "分页显示课程数据")
    @GetMapping(value = "/getPageList/{page}/{limit}")
    public R getPageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.page(pageParam, null);
        List<Course> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery){
        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam, courseQuery);
        return  R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "/course-info/{id}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

    //根据课程id查询课程信息
    @GetMapping("getDto/{courseId}")
    public com.pang.commonutils.CourseInfoForm getCourseInfoDto(@PathVariable String courseId) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfo(courseId);
        com.pang.commonutils.CourseInfoForm courseInfo = new com.pang.commonutils.CourseInfoForm();
        BeanUtils.copyProperties(courseInfoForm, courseInfo);
        return courseInfo;
    }

    //根据id查询课程详情信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id, HttpServletRequest request) {
        //课程查询课程基本信息
        CourseInfoForm courseInfoForm = courseService.getCourseInfoFormById(id);
        //查询课程里面大纲数据
        List<ChapterVo> chapterVideoList = chapterService.nestedList(id);

        //远程调用，判断课程是否被购买
        boolean buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), id);

        return R.ok().data("courseFrontInfo",courseInfoForm).data("chapterVideoList",chapterVideoList).data("isbuy",buyCourse);
    }
}

