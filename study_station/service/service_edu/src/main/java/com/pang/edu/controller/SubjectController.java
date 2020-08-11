package com.pang.edu.controller;


import com.pang.commonutils.R;
import com.pang.edu.entity.subject.OneSubject;
import com.pang.edu.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author pang
 * @since 2020-08-03
 */
@CrossOrigin
@RestController
@RequestMapping("/eduService/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * @Author: SmallPang
     * @Description: 添加课程分类
     * @Date: 2020/8/3
     * @Param file:
     * @return: com.pang.commonutils.R
     **/
    @ApiOperation(value = "Excel批量导入")
    @PostMapping(value = "/addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    @GetMapping(value = "/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> oneSubjectList = subjectService.getAllSubject();
        return R.ok().data("items", oneSubjectList);
    }
}

