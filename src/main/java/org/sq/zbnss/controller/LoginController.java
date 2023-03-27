package org.sq.zbnss.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;


import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.base.ConstantEnum;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.uitl.JWTUtil;
import org.sq.zbnss.uitl.ResultUtil;
import org.sq.zbnss.uitl.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "登录", tags = "登录")

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class LoginController {
    /**
     * 登录
     */
    @ApiOperation(value = "用户登录系统", tags = "登录")
    @PostMapping("/login")
    public ResponseVo login( String username, String password,  HttpServletResponse response) {
        if (StringUtil.isBlank(username, password)) {
            return ResultUtil.error("账号或密码不能为空");
        }

        // 创建一个subject，是shiro的登录用户主体
        Subject subject = SecurityUtils.getSubject();
        // 认证提交前准备token
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(username);
        token.setPassword(password.toCharArray());
        String tokenString = null;
        User loginUser = new User();
        // 执行登录
        try {
            /*
            这里就会调用Realm去处理登录校验之类的事情，至于用哪个Realm，就看这里传入的token是哪个类的token，
            然后由接管不同类型token的Realm去处理
             */
            subject.login(token);
            User user = (User) subject.getPrincipal();
            loginUser = user;
            tokenString = JWTUtil.createBearerToken(user.getUsername(),user.getUserId());
            response.setHeader(ConstantEnum.AUTHORIZATION.getValue(), tokenString);
        } catch (LockedAccountException e){
            subject.logout();
            return ResultUtil.error( "账号已被锁定，请联系管理员！");
        } catch (UnknownAccountException e){
            subject.logout();
            return ResultUtil.error("未知账号！");
        } catch (ExcessiveAttemptsException e){
            subject.logout();
            return ResultUtil.error("账号或密码错误次数过多！请5分钟后再登录！");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            subject.logout();
            return ResultUtil.error("密码不正确！");
        } catch (AuthenticationException e){
            subject.logout();
            return ResultUtil.error("账号或密码不正确！");
        }
        if (subject.isAuthenticated()){
            JSONObject data = new JSONObject();
            data.put("token",tokenString);
            data.put("user",loginUser);
            return ResultUtil.success("登录成功",data);
        } else {
            subject.logout();
            return ResultUtil.error("登录失败");
        }
    }

    @ApiOperation(value = "登出系统", tags = "登录")
    @PostMapping("/logout")
    public ResponseVo logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success("登出成功");
    }

    @RequestMapping("/loginError")
    public ResponseVo loginError(HttpServletResponse response) {
        return ResultUtil.error("未登录");
    }

    @RequestMapping("/loginExpire")
    public ResponseVo loginExpire(){
        return  ResultUtil.error("token已过期，请重新登录！");
    }
}
