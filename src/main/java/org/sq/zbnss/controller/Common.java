package org.sq.zbnss.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.UserService;
import org.sq.zbnss.uitl.JWTUtil;

import javax.servlet.http.HttpServletRequest;
@RestController
@AllArgsConstructor
public class Common {

    @Autowired
    private  UserService userService;

    public  User getLoginUser(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String[] tokens = tokenHeader.split(" ");
        String token = tokens[1];
        String userId = JWTUtil.getUserId(token);
        User loginUser = userService.getUserByUserId(userId);
        return loginUser;
    }
}
