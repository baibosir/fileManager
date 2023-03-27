package org.sq.zbnss.shiro.matcher;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.stereotype.Component;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.UserService;
import org.sq.zbnss.uitl.RedisUtil;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserService userService;

    public static final String KEY_PREFIX = "shiro:cache:retryLimit:";

    public static final Integer MAX = 5;// 最大登录次数

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        // 获取用户名
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        String key = KEY_PREFIX + userName;
        // 获取用户登录失败次数
        AtomicInteger atomicInteger = (AtomicInteger) redisUtil.get(key);
        if (atomicInteger == null){
            atomicInteger = new AtomicInteger(0);
        }
        User user = userService.selectByUsername(userName);
        if (atomicInteger.incrementAndGet() > MAX){
            // 如果用户登录失败次数大于5次，抛出锁定用户异常，并修改数据库用户状态字段
            if (user != null && user.getStatus() == 1){
                user.setStatus(0);// 设置为锁定状态
                userService.updateById(user);
            }
            log.info("锁定用户"+ userName);
            throw new ExcessiveAttemptsException();
        }
        // 判断用户的账号和密码是否正确
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches){
            // 如果匹配上了
            redisUtil.delete(key);
            // 将用户的状态改为0
            if (user.getStatus() == 0){
                user.setStatus(1);
                userService.updateByUserId(user);
            }
        } else {
            redisUtil.set(key, atomicInteger, 300);
        }
        return matches;
    }
}
