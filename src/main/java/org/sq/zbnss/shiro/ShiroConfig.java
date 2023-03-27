package org.sq.zbnss.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sq.zbnss.config.CORSFilter;
import org.sq.zbnss.shiro.factory.MySubjectFactory;
import org.sq.zbnss.shiro.filter.CorsFilter;
import org.sq.zbnss.shiro.filter.JWTFilter;
import org.sq.zbnss.shiro.realm.MyModularRealmAuthenticator;
import org.sq.zbnss.shiro.realm.MyRealm;
import org.sq.zbnss.shiro.subject.MySubjectDAO;


import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(MyRealm myRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        // 设置自定义 realm.
        securityManager.setRealm(myRealm);

        //关闭session
        DefaultSubjectDAO subjectDAO=new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator=new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean
    public FilterRegistrationBean replaceTokenFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter( new CORSFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CrosFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/api/login");
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("cors", new CorsFilter());
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        /*
        因为这里配置的路径和拦截规则，是需要按照顺序的，所以使用LinkedHashMap而不是HashMap
         */
        Map<String, String> map = new LinkedHashMap<>();
        // authc:所有url都必须认证通过才可以访问，anon:所有url都可以匿名访问
        map.put("/api/login", "anon");
        map.put("/doc.html", "anon");
        map.put("/webjars/**", "anon");
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/api-docs", "anon");
        /*
        使用BearerHttpAuthenticationFilter过滤器来拦截，并获取请求头里的Authorization字段，
        并将其所携带的jwt token内容包装成一个BearerToken对象，并调用login方法进入realm进行身份验证。
         */
        map.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        // 下面注释的那个，全局配置的禁用session的不管用，需要再覆盖两个Bean注入，注入的方法往下看
//        shiroFilterFactoryBean.setGlobalFilters(Collections.singletonList("noSessionCreation"));//关键：全局配置NoSessionCreationFilter，把整个项目切换成无状态服务。
        return shiroFilterFactoryBean;
    }

    /*
    下面两个Bean(subjectDAO和subjectFactory)，作用是关闭Subject的session
     */

    @Bean
    public SubjectDAO subjectDAO(){
        return new MySubjectDAO();
    }

    @Bean
    public SubjectFactory subjectFactory(){
        return new MySubjectFactory();
    }

    @Bean
    public Authorizer authorizer(){
        return new ModularRealmAuthorizer();
    }

    /**
     * 设置多个realm处理登录时可以抛出异常
     * @return
     */
    @Bean
    public Authenticator authenticator(){
        return new MyModularRealmAuthenticator();
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host + ':' + port);
//        redisManager.setTimeout(timeout);
//        if (StringUtils.isNotBlank(password)) {
//            redisManager.setPassword(password);
//        }
//
//        return redisManager;
//    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    @Bean
//    public RedisCacheManager redisCacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        redisCacheManager.setPrincipalIdFieldName("userId");
//        return redisCacheManager;
//    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setKeyPrefix(CoreConst.SHIRO_REDIS_SESSION_PREFIX);
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }

    /*
    下面的几个自行注入的bean，在springboot里都已经自动装配了，一般不需要自行注入了，先注释掉
     */

//    /**
//     * 管理Shiro中一些bean的生命周期
//     * @return
//     */
//    @Bean("lifecycleBeanPostProcessor")
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    /**
//     * 扫描上下文，寻找所有的Advistor(通知器）
//     * 将这些Advisor应用到所有符合切入点的Bean中。
//     */
//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//        proxyCreator.setProxyTargetClass(true);
//        return proxyCreator;
//    }
//
//    /**
//     * 开启shiro对注解的支持
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
}
