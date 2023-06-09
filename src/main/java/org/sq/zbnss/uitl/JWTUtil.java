package org.sq.zbnss.uitl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    private static final long EXPIRE = 24 * 60 * 60 *1000L;// token的有效时长
    private static final String SECRET = "jwt+shiro+nss";// token的私钥
    private static final String USER_KEY = "userName";
    private static final String USER_ID = "userId";

    @Autowired
    private static UserService userService;

    public static String createBearerToken(String userName,String userId){
        return createToken(userName,userId);
    }

    /**
     * 创建token
     * @param userName 用户名
     * @return 创建的token
     */
    public static String createToken(String userName,String userId){
        // token的过期时间
        long current = System.currentTimeMillis();
        Date date = new Date(current + EXPIRE);
        // jwt的header部分
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // 创建token
        String token;
        try {
            token = JWT.create()
                    .withHeader(map) //header部分
                    .withClaim(USER_KEY, userName)
                    .withClaim(USER_ID,userId) //存储用户信息
                    .withClaim("current", current) //当前的时间戳
                    .withExpiresAt(date) //过期时间
                    .withIssuedAt(new Date(current)) //签发时间
                    .sign(Algorithm.HMAC256(SECRET)); //私钥
        } catch (Exception e){
            log.error("为用户{}创建token失败", userName);
            throw new RuntimeException("为用户创建token失败", e);
        }
        return token;
    }

    /**
     * 校验token
     * @param token 传入的token
     * @return 是否校验通过
     */
    public static boolean verifyToken(String token) throws AuthenticationException {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e){
            log.error("校验token={}失败", token, e);
            throw e;
        }
    }

    /**
     * 从token中获取用户信息
     * @param token 传入的token
     * @return 用户信息
     */
    public static String getUserInfo(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_KEY).asString();
        } catch (Exception e){
            log.error("从token={}中获取用户信息失败", token, e);
            return null;
        }
    }

    public static String getUserId(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asString();
        } catch (Exception e){
            log.error("从token={}中获取用户信息失败", token, e);
            return null;
        }
    }

    public static User getUserInfo(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String[] tokens = tokenHeader.split(" ");
        String token = tokens[1];
        String userName = JWTUtil.getUserInfo(token);
        String userId = JWTUtil.getUserId(token);
        return userService.getUserByUserId(userName);
    }
    /**
     * 判断是否过期
     */
    public static boolean isExpire(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().getTime() < System.currentTimeMillis();
    }

    public static void main(String[] args){
//        String token = JWTUtil.createToken("zhan");
//        System.out.println(token);

        boolean result = JWTUtil.verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjdXJyZW50IjoxNjgwMDQzMjUyMDk4LCJ1c2VyTmFtZSI6InJvb3QiLCJleHAiOjE2ODAxMjk2NTIsInVzZXJJZCI6IjEwMDAwMDEwNTM0NjMyNjAiLCJpYXQiOjE2ODAwNDMyNTJ9.4jMcPUJ7tCaWaVGS_PQbghCzeQEFE23K3vKiGDPzH1\n");
        System.out.println(result);
    }
}
