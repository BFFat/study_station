package com.pang.edu.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "章节信息")
public class ChapterVo {
    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
