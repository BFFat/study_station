package com.pang.acl.service.impl;

import com.pang.acl.entity.Role;
import com.pang.acl.mapper.RoleMapper;
import com.pang.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
