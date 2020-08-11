package com.pang.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pang.acl.entity.Permission;
import com.pang.acl.entity.RolePermission;
import com.pang.acl.helper.PermissionHelper;
import com.pang.acl.mapper.PermissionMapper;
import com.pang.acl.service.PermissionService;
import com.pang.acl.service.RolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author pang
 * @since 2020-08-11
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final RolePermissionService rolePermissionService;

    public PermissionServiceImpl(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    //获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);
        //把根据节点id放到list中
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {

        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));

        List<RolePermission> rolePermissionList = new ArrayList<>();
        for(String permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
