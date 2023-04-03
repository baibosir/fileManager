package org.sq.zbnss.shiro.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.sq.zbnss.exception.AppException;
import org.sq.zbnss.exception.NotLoginException;
import org.sq.zbnss.uitl.JWTUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.sq.zbnss.uitl.JWTUtil.verifyToken;

@Slf4j
public class JWTFilter extends BearerHttpAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean res = false;
        HttpServletRequest req = WebUtils.toHttp(request);
        // 先判断是否传入了token
        if (!isLoginAttempt(request, response)){
            req.setAttribute("exception", new NotLoginException("未登录！"));
            req.getRequestDispatcher("/api/loginError").forward(request, response);
            return false;
        }

        // 再看是否过期
        BearerToken token = (BearerToken) createToken(request, response);
        if (JWTUtil.isExpire(token.getToken())){
            // 刷新token
            if (!refreshToken(request, response)){
                req.getRequestDispatcher("/api/loginExpire").forward(request, response);
                return false;
            }
            return true;
        }
        try {
            /*
            这里最终会调用subject.login(token)去处理认证，
            这里因为继承了BearerHttpAuthenticationFilter，
            所以这里会自动包装成一个BearerToken作为参数代入subject.login(token)中，
            怎么包装的呢？原来它会从请求头里获取一个"Authorization"字段的值，拿到这个值去进行包装
             */
//            res = super.onAccessDenied(request, response);
        } catch (Exception e){
            Throwable cause = e.getCause();
            if (cause instanceof TokenExpiredException){
                refreshToken(request, response);
            } else {
                throw e;
            }
        }
        try{
            boolean re = verifyToken(token.getPrincipal().toString());
            if(re){
                return  true;
            }
            throw  new AppException("token验证失败");
        }catch (Exception e){
            throw  new AppException("token验证异常");
        }

    }

    /**
     * 刷新token
     * @param request
     * @param response
     * @return
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response){
        log.info("刷新token...");
        HttpServletRequest req= (HttpServletRequest) request;
        String tokenHeader = req.getHeader(AUTHORIZATION_HEADER);
        String[] tokens = tokenHeader.split(" ");
        String token = tokens[1];
        String userName = JWTUtil.getUserInfo(token);
        String userId = JWTUtil.getUserId(token);
        String newToken = JWTUtil.createToken(userName,userId);
        BearerToken bearerToken = new BearerToken(newToken);
        try {
            getSubject(request, response).login(bearerToken);
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Access-Control-Expose-Headers", "Authorization");
            res.setHeader("Authorization", "Bearer " + newToken);
            return true;
        } catch (Exception e){
            log.error("token刷新失败", e);
            return false;
        }
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
