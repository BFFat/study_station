package com.pang.edu.service;

import com.pang.edu.entity.subject.OneSubject;
import com.pang.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author pang
 * @since 2020-08-03
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllSubject();
}
