package org.sq.zbnss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sq.zbnss.entity.User;

import java.util.List;

/**
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据user参数查询用户列表
     *
     * @param page
     * @param user
     * @return list
     */
    IPage<User> selectUsers(@Param("page") IPage<User> page, @Param("user") User user);

    /**
     * 根据角色id查询用户list
     *
     * @param roleId
     * @return list
     */
    List<User> findByRoleId(String roleId);

    /**
     * 根据角色id查询用户list
     *
     * @param roleIds
     * @return list
     */
    List<User> findByRoleIds(List<String> roleIds);
}
