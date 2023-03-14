package org.sq.zbnss.shiro;


import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.sq.zbnss.config.properties.FileUploadProperties;
import org.sq.zbnss.config.properties.StaticizeProperties;
import org.sq.zbnss.uitl.CoreConst;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化、动态更新shiro用户权限
 *
 */
@Service
@AllArgsConstructor
public class ShiroService {


    private final ShiroFilterFactoryBean shiroFilterFactoryBean;

    @PostConstruct
    public void init() {
        updatePermission();
    }

    /**
     * 初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/v2/api-docs-ext", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/webjars/*", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");

        return filterChainDefinitionMap;
    }


    /**
     * 重新加载权限
     */
    public void updatePermission() {
        synchronized (shiroFilterFactoryBean) {

            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            chains.forEach((url, perm) -> manager.createChain(url, StringUtils.deleteWhitespace(perm)));
        }
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/doc.html", "anon");
        chainDefinition.addPathDefinition("/webjars/**/**", "anon");
        return chainDefinition;
    }

}
