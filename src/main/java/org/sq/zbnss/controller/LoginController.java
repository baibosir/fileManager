package org.sq.zbnss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;


import org.springframework.web.bind.annotation.*;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.uitl.ResultUtil;
import org.sq.zbnss.uitl.StringUtil;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseVo login(HttpServletRequest request, String username, String password, boolean rememberMe) {
        if (StringUtil.isBlank(username, password)) {
            return ResultUtil.error("账号或密码不能为空");
        }
//        String sessionCode = (String) request.getSession().getAttribute("captcha");
//        if (code == null || !sessionCode.equals(code.trim().toLowerCase())) {
//            return R.failed("验证码不正确");
//        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            SecurityUtils.getSubject().login(token);
            return ResultUtil.success("登录成功");
        } catch (UnknownAccountException e) {
            return ResultUtil.error("用户不存在");
        } catch (IncorrectCredentialsException e) {
            return ResultUtil.error("密码错误");
        } catch (ExcessiveAttemptsException eae) {
            return ResultUtil.error("操作频繁，请稍后再试");
        }
    }

}
