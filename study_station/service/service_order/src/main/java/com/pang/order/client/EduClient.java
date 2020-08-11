package com.pang.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @GetMapping("/eduService/course/getDto/{courseId}")
    public com.pang.commonutils.CourseInfoForm getCourseInfoDto(@PathVariable("courseId") String courseId);
}
