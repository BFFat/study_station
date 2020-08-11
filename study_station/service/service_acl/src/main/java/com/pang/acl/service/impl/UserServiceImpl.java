package com.pang.acl.service.impl;

import com.pang.acl.entity.User;
import com.pang.acl.mapper.UserMapper;
import com.pang.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
