package com.pang.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.base.exceptionhandler.GuliException;
import com.pang.edu.entity.subject.OneSubject;
import com.pang.edu.entity.Subject;
import com.pang.edu.entity.subject.TwoSubject;
import com.pang.edu.entity.excel.SubjectData;
import com.pang.edu.listener.SubjectExcelListener;
import com.pang.edu.mapper.SubjectMapper;
import com.pang.edu.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-03
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    /**
     * @Author: SmallPang
     * @Description: 添加课程分类
     * @Date: 2020/8/3
     * @Param file:
     * @return: void
     **/
    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        //最终要的到的数据列表
        ArrayList<OneSubject> subjectNestedVoArrayList = new ArrayList<>();

        //获取一级分类数据记录
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据记录
        QueryWrapper<Subject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", 0);
        queryWrapper2.orderByAsc("sort", "id");
        List<Subject> subSubjects = baseMapper.selectList(queryWrapper2);

        //填充一级分类vo数据
        int count = subjects.size();
        for (int i = 0; i < count; i++) {
            Subject subject = subjects.get(i);

            //创建一级类别vo对象
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneSubject);
            subjectNestedVoArrayList.add(oneSubject);

            //填充二级分类vo数据
            ArrayList<TwoSubject> subjectVoArrayList = new ArrayList<>();
            int count2 = subSubjects.size();
            for (int j = 0; j < count2; j++) {

                Subject subSubject = subSubjects.get(j);
                if(subject.getId().equals(subSubject.getParentId())){

                    //创建二级类别vo对象
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subSubject, twoSubject);
                    subjectVoArrayList.add(twoSubject);
                }
            }
            oneSubject.setChildren(subjectVoArrayList);
        }


        return subjectNestedVoArrayList;
    }


}
