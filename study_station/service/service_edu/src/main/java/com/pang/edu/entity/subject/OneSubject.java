package com.pang.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类 一级分类中有多个二级分类
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
