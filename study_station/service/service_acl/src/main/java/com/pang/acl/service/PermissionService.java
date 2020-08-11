package com.pang.acl.service;

import com.pang.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author pang
 * @since 2020-08-11
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllMenu();

    void removeChildById(String id);

    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);
}
