package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.sq.zbnss.dao.RoleMapper;
import org.sq.zbnss.dao.UserMapper;
import org.sq.zbnss.entity.Role;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.RoleService;
import org.sq.zbnss.uitl.Pagination;
import org.sq.zbnss.uitl.UUIDUtil;

import java.util.*;

/**
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    @Override
    public Set<String> findRoleByUserId(String userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public IPage<Role> selectRoles(Role role, Integer pageNumber, Integer pageSize) {
        IPage<Role> page = new Pagination<>(pageNumber, pageSize);
        return roleMapper.selectRoles(page, role);
    }

    @Override
    public int insert(Role role) {
        role.setRoleId(UUIDUtil.getUniqueIdByUUId());
        role.setStatus(1);
        role.setCreateTime(new Date());
        return roleMapper.insert(role);
    }

    @Override
    public int updateStatusBatch(List<String> roleIds, Integer status) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("roleIds", roleIds);
        params.put("status", status);
        return roleMapper.updateStatusBatch(params);
    }

    @Override
    public Role findById(Integer roleId) {
        return roleMapper.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleId, roleId));
    }

    @Override
    public int updateByRoleId(Role role) {
        Map<String, Object> params = new HashMap<>(3);
        params.put("name", role.getName());
        params.put("description", role.getDescription());
        params.put("role_id", role.getRoleId());
        return roleMapper.updateByRoleId(params);
    }

    @Override
    public void addAssignPermission(String roleId, List<String> permissionIdsList) {

    }


    @Override
    public List<User> findByRoleId(String roleId) {
        return userMapper.findByRoleId(roleId);
    }

    @Override
    public List<User> findByRoleIds(List<String> roleIds) {
        return userMapper.findByRoleIds(roleIds);
    }

}
