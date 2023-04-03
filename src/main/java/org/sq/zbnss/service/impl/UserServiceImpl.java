package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.sq.zbnss.base.UserOnlineVo;
import org.sq.zbnss.dao.UserMapper;
import org.sq.zbnss.dao.UserRoleMapper;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.entity.UserRole;
import org.sq.zbnss.service.LogService;
import org.sq.zbnss.service.UserService;
import org.sq.zbnss.uitl.CoreConst;
import org.sq.zbnss.uitl.Pagination;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

/**
 用户服务
 */
@Service
@AllArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private LogService logService;


    @Override
    public User selectByUsername(String username) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username).eq(User::getStatus, CoreConst.STATUS_VALID));
    }

    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }

    @Override
    public void updateLastLoginTime(User user) {
        Assert.notNull(user, "param: user is null");
        user.setLastLoginTime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public IPage<User> selectUsers(User user, Integer pageNumber, Integer pageSize) {
        System.out.println(user);
        IPage<User> page = new Pagination<>(pageNumber, pageSize);
        return userMapper.selectUsers(page, user);
    }

    @Override
    public User selectByUserId(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public int updateByUserId(User user) {
        Assert.notNull(user, "param: user is null");
        user.setUpdateTime(new Date());
        return userMapper.updateById(user);
    }

    @Override
    public boolean updateStatusBatch(List<String> userIds, Integer status) {
        return update(Wrappers.<User>lambdaUpdate().in(User::getUserId, userIds)
                .set(User::getStatus, status).set(User::getUpdateTime, new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAssignRole(String userId, List<String> roleIds) {
        userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
        for (String roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public List<UserOnlineVo> selectOnlineUsers(UserOnlineVo userOnlineVo) {
        return null;
    }

    @Override
    public void kickout(Serializable sessionId, String username) {

    }

    @Override
    public boolean saveBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        return false;
    }

    @Override
    public User getOne(Wrapper<User> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<User> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<User> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<User> getBaseMapper() {
        return null;
    }

    @Override
    public Class<User> getEntityClass() {
        return null;
    }

    @Override
    public User getUserByUserId(String userId){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

//    @Override
//    public List<UserOnlineVo> selectOnlineUsers(UserOnlineVo userVo) {
//        // 因为我们是用redis实现了shiro的session的Dao,而且是采用了shiro+redis这个插件
//        // 所以从spring容器中获取redisSessionDAO
//        // 来获取session列表.
//        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
//        Iterator<Session> it = sessions.iterator();
//        List<UserOnlineVo> onlineUserList = new ArrayList<UserOnlineVo>();
//        // 遍历session
//        while (it.hasNext()) {
//            // 这是shiro已经存入session的
//            // 现在直接取就是了
//            Session session = it.next();
//            //标记为已提出的不加入在线列表
//            if (session.getAttribute("kickout") != null) {
//                continue;
//            }
//            UserOnlineVo onlineUser = getSessionBo(session);
//            if (onlineUser != null) {
//                /*用户名搜索*/
//                if (StringUtils.isNotBlank(userVo.getUsername())) {
//                    if (onlineUser.getUsername().contains(userVo.getUsername())) {
//                        onlineUserList.add(onlineUser);
//                    }
//                } else {
//                    onlineUserList.add(onlineUser);
//                }
//            }
//        }
//        return onlineUserList;
//    }
//
//    @Override
//    public void kickout(Serializable sessionId, String username) {
//        getSessionBysessionId(sessionId).setAttribute("kickout", true);
//        //读取缓存,找到并从队列中移除
//        Cache<String, Deque<Serializable>> cache = redisCacheManager.getCache(CoreConst.SHIRO_REDIS_CACHE_NAME);
//        Deque<Serializable> deques = cache.get(username);
//        for (Serializable deque : deques) {
//            if (sessionId.equals(deque)) {
//                deques.remove(deque);
//                break;
//            }
//        }
//        cache.put(username, deques);
//    }
//
//
//    private Session getSessionBysessionId(Serializable sessionId) {
//        return sessionManager.getSession(new DefaultSessionKey(sessionId));
//    }

//    private static UserOnlineVo getSessionBo(Session session) {
//        //获取session登录信息。
//        Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
//        if (null == obj) {
//            return null;
//        }
//        //确保是 SimplePrincipalCollection对象。
//        if (obj instanceof SimplePrincipalCollection) {
//            SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
//            obj = spc.getPrimaryPrincipal();
//            if (obj instanceof User) {
//                User user = (User) obj;
//                //存储session + user 综合信息
//                UserOnlineVo userBo = new UserOnlineVo();
//                //最后一次和系统交互的时间
//                userBo.setLastAccess(session.getLastAccessTime());
//                //主机的ip地址
//                userBo.setHost(user.getLoginIpAddress());
//                //session ID
//                userBo.setSessionId(session.getId().toString());
//                //最后登录时间
//                userBo.setLastLoginTime(user.getLastLoginTime());
//                //回话到期 ttl(ms)
//                userBo.setTimeout(session.getTimeout());
//                //session创建时间
//                userBo.setStartTime(session.getStartTimestamp());
//                //是否踢出
//                userBo.setSessionStatus(false);
//                /*用户名*/
//                userBo.setUsername(user.getUsername());
//                return userBo;
//            }
//        }
//        return null;
//    }


}
