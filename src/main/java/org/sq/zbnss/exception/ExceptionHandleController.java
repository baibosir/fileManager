package org.sq.zbnss.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.sq.zbnss.base.ResponseStatus;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.uitl.ResultUtil;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常统一处理
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(AppException.class)
    public String handleAppException(Exception e, HttpServletRequest request) {
        request.setAttribute("javax.servlet.error.status_code", ResponseStatus.ERROR.getCode());
        Map<String, Object> map = new HashMap<>(2);
        map.put("status", ResponseStatus.ERROR.getCode());
        map.put("msg", StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : ResponseStatus.ERROR.getMessage());
        log.error("拦截到系统异常AppException: {}", e.getMessage(), e);
        request.setAttribute("ext", map);
        return "forward:/error";
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public String handleArticle(Exception e, HttpServletRequest request) {
        request.setAttribute("javax.servlet.error.status_code", ResponseStatus.NOT_FOUND.getCode());
        return "forward:/error";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String handleAuth(HttpServletRequest request) {
        request.setAttribute("javax.servlet.error.status_code", ResponseStatus.FORBIDDEN.getCode());
        return "forward:/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {
        log.error("异常: {}", e.getMessage(), e);
        request.setAttribute("javax.servlet.error.status_code", ResponseStatus.ERROR.getCode());
        return "forward:/error";
    }

    @ExceptionHandler
    public ResponseVo handleBindException(BindException e) {
        log.debug("捕获到BindException：{}", e.getMessage());
        // 以下代码，如果有多种错误时，将获取所有错误信息，并响应
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            stringBuilder.append(fieldError.getDefaultMessage());
        }
        return ResultUtil.error( stringBuilder.toString());
    }

}
