package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.base.ChangePasswordVo;
import org.sq.zbnss.base.PageResultVo;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.Role;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.RoleService;
import org.sq.zbnss.service.UserService;
import org.sq.zbnss.shiro.MyShiroRealm;
import org.sq.zbnss.uitl.*;

import java.util.*;

/**
 * 后台用户配置
 *
 */

@RequestMapping("/api/user")
@AllArgsConstructor
@RestController
@Api(value = "用户管理", tags = "用户管理")
public class UserController {

    private final MyShiroRealm myShiroRealm;
    private final UserService userService;
    private final RoleService roleService;
    private final MyShiroRealm shiroRealm;


    /**
     * 用户列表数据
     */
    @ApiOperation(value = "获取用户列表", tags = "用户管理")
    @PostMapping("/list")
    @ResponseBody
    public PageResultVo loadUsers(@RequestBody() User user, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        IPage<User> userPage = userService.selectUsers(user, pageNumber, pageSize);
        return ResultUtil.table(userPage.getRecords(), userPage.getTotal());
    }


    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户", tags = "用户管理")
    @PostMapping("/add")
    @ResponseBody
    @ApiOperationSupport(ignoreParameters = {"User.id","userId","salt","status","createTime"
            ,"updateTime","lastLoginTime","loginIpAddress","loginIpAddress"})
    public ResponseVo add(@RequestBody() User userForm, @RequestParam("confirmPassword") String confirmPassword) {
        String username = userForm.getUsername();
        User user = userService.selectByUsername(username);
        if (null != user) {
            return ResultUtil.error("用户名已存在");
        }
        String password = userForm.getPassword();
        //判断两次输入密码是否相等
        if (confirmPassword != null && password != null) {
            if (!confirmPassword.equals(password)) {
                return ResultUtil.error("两次密码不一致");
            }
        }
        userForm.setUserId(UUIDUtil.getUniqueIdByUUId());
        userForm.setStatus(CoreConst.STATUS_VALID);
        Date date = new Date();
        userForm.setCreateTime(date);
        userForm.setUpdateTime(date);
        userForm.setLastLoginTime(date);
        PasswordHelper.encryptPassword(userForm);
        int num = userService.register(userForm);
        if (num > 0) {
            return ResultUtil.success("添加用户成功");
        } else {
            return ResultUtil.error("添加用户失败");
        }
    }

    /**
     * 编辑用户
     */
    @ApiOperation(value = "修改用户", tags = "用户管理")
    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo editUser(@RequestBody()User userForm) {
        int a = userService.updateByUserId(userForm);
        if (a > 0) {
            return ResultUtil.success("编辑用户成功！");
        } else {
            return ResultUtil.error("编辑用户失败");
        }
    }

    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户", tags = "用户管理")
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo deleteUser(@RequestParam("userid") String userId) {
        List<String> userIdsList = Arrays.asList(userId);
        boolean a = userService.updateStatusBatch(userIdsList, CoreConst.STATUS_INVALID);
        if (a) {
            return ResultUtil.success("删除用户成功");
        } else {
            return ResultUtil.error("删除用户失败");
        }
    }

    /**
     * 批量删除用户
     */
    @ApiOperation(value = "批量删除用户", tags = "用户管理")
    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo batchDeleteUser(@RequestParam("ids[]") String[] ids) {
        List<String> userIdsList = Arrays.asList(ids);
        boolean a = userService.updateStatusBatch(userIdsList, CoreConst.STATUS_INVALID);
        if (a) {
            return ResultUtil.success("删除用户成功");
        } else {
            return ResultUtil.error("删除用户失败");
        }
    }

    /**
     * 分配角色列表查询
     */
    @ApiOperation(value = "获取用户角色", tags = "用户管理")
    @PostMapping("/assign/role/list")
    @ResponseBody
    public Map<String, Object> assignRoleList(String userId) {
        List<Role> roleList = roleService.list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, 1));
        Set<String> hasRoles = roleService.findRoleByUserId(userId);
        Map<String, Object> jsonMap = new HashMap<>(2);
        jsonMap.put("rows", roleList);
        jsonMap.put("hasRoles", hasRoles);
        return jsonMap;
    }

    /**
     * 保存分配角色
     */
    @ApiOperation(value = "分配角色", tags = "用户管理")
    @PostMapping("/assign/role")
    @ResponseBody
    public ResponseVo assignRole(String userId, String roleIdStr) {
        ResponseVo responseVo;
        String[] roleIds = roleIdStr.split(",");
        List<String> roleIdsList = Arrays.asList(roleIds);
        try {
            // 给用户分配角色
            userService.addAssignRole(userId, roleIdsList);
            // 重置用户权限
            myShiroRealm.clearAuthorizationByUserId(Collections.singletonList(userId));
            responseVo = ResultUtil.success("分配角色成功");
        } catch (Exception e) {
            responseVo = ResultUtil.error("分配角色失败");
        }
        return responseVo;
    }

    /*修改密码*/
    @ApiOperation(value = "修改密码", tags = "用户管理")
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo changePassword(ChangePasswordVo changePasswordVo) {
        if (!changePasswordVo.getNewPassword().equals(changePasswordVo.getConfirmNewPassword())) {
            return ResultUtil.error("两次密码输入不一致");
        }
        User loginUser = userService.selectByUserId(((User) SecurityUtils.getSubject().getPrincipal()).getUserId());
        User newUser = CopyUtil.getCopy(loginUser, User.class);
        String sysOldPassword = loginUser.getPassword();
        newUser.setPassword(changePasswordVo.getOldPassword());
        String entryOldPassword = PasswordHelper.getPassword(newUser);
        if (sysOldPassword.equals(entryOldPassword)) {
            newUser.setPassword(changePasswordVo.getNewPassword());
            PasswordHelper.encryptPassword(newUser);
            userService.updateById(newUser);
            //*清除登录缓存*//
            List<String> userIds = new ArrayList<>();
            userIds.add(loginUser.getUserId());
            shiroRealm.removeCachedAuthenticationInfo(userIds);
            /*SecurityUtils.getSubject().logout();*/
        } else {
            return ResultUtil.error("您输入的旧密码有误");
        }
        return ResultUtil.success("修改密码成功");
    }

}
