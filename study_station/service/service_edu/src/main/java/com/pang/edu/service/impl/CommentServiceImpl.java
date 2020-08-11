package com.pang.edu.service.impl;

import com.pang.edu.entity.Comment;
import com.pang.edu.mapper.CommentMapper;
import com.pang.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-10
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
