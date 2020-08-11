package com.pang.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pang.commonutils.R;
import com.pang.edu.entity.Course;
import com.pang.edu.entity.Teacher;
import com.pang.edu.entity.vo.TeacherQuery;
import com.pang.edu.service.CourseService;
import com.pang.edu.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
@RestController
@RequestMapping("/eduService/teacher")
@CrossOrigin
public class TeacherController {

    private final TeacherService teacherService;

    private final CourseService courseService;

    public TeacherController(TeacherService teacherService, CourseService courseService) {
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    /**
     * @Author: SmallPang
     * @Description: 讲师查询
     * @Date: 2020/7/28

     * @return: com.pang.commonutils.R
     **/
    @ApiOperation(value = "所有讲师列表")
    @GetMapping(value = "/findAll")
    public R findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * @Author: SmallPang
     * @Description: 逻辑删除讲师
     * @Date: 2020/7/28
     * @Param id:
     * @return: com.pang.commonutils.R
     **/
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping(value = "/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag)
            return R.ok();
        else
            return R.error();
    }

    /**
     * @Author: SmallPang
     * @Description: 讲师分页查询
     * @Date: 2020/7/28
     * @Param page:
     * @Param limit:
     * @return: com.pang.commonutils.R
     **/
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageTeacher/{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit){

        Page<Teacher> pageParam = new Page<>(page, limit);

        teacherService.page(pageParam, null);
        List<Teacher> records = pageParam.getRecords(); //数据list集合
        long total = pageParam.getTotal(); //总记录数

        return  R.ok().data("total", total).data("rows", records);
    }

    /**
     * @Author: SmallPang
     * @Description: 条件分页查询
     * @Date: 2020/7/28
     * @Param current:
     * @Param limit:
     * @Param teacherQuery:
     * @return: com.pang.commonutils.R
     **/
    @PostMapping(value = "/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            //required = false: 参数值可以为空
            @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> pageTeacher = new Page<>(current, limit);
//        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
//        String name = teacherQuery.getName();
//        Integer level = teacherQuery.getLevel();
//        String begin = teacherQuery.getBegin();
//        String end = teacherQuery.getEnd();
//        if (!StringUtils.isEmpty(name)) {
//            queryWrapper.like("name", name);
//        }
//        if (!StringUtils.isEmpty(String.valueOf(level))) {
//            queryWrapper.eq("level", level);
//        }
//        if (!StringUtils.isEmpty(begin)) {
//            queryWrapper.ge("gmt_create", begin);
//        }
//        if (!StringUtils.isEmpty(end)) {
//            queryWrapper.le("gmt_create", end);
//        }

//        teacherService.page(pageTeacher, queryWrapper);
        teacherService.pageQuery(pageTeacher, teacherQuery);
        long total = pageTeacher.getTotal();
        List<Teacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * @Author: SmallPang
     * @Description: 添加讲师
     * @Date: 2020/7/28
     * @Param teacher:
     * @return: com.pang.commonutils.R
     **/
    @PostMapping(value = "/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * @Author: SmallPang
     * @Description: 通过id查找讲师
     * @Date: 2020/7/28
     * @Param id:
     * @return: com.pang.commonutils.R
     **/
    @GetMapping(value = "/getTeacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        Teacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<Course> courseList = courseService.selectByTeacherId(id);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }

    /**
     * @Author: SmallPang
     * @Description: 讲师修改
     * @Date: 2020/7/28
     * @Param teacher:
     * @return: com.pang.commonutils.R
     **/
    @PostMapping(value = "/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<Teacher> pageParam = new Page<Teacher>(page, limit);

        Map<String, Object> map = teacherService.pageListWeb(pageParam);

        return  R.ok().data(map);
    }
}

