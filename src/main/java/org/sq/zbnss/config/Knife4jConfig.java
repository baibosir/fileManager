package org.sq.zbnss.config;


import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用: 自动生成API文档和在线接口调试工具
 */

@Configuration
//该注解是Springfox-swagger框架提供的使用Swagger注解，该注解必须加
@EnableSwagger2
//knife4j提供的增强扫描注解,Ui提供了例如动态参数、参数过滤、接口排序等增强功能
@EnableKnife4j
@EnableOpenApi
public class Knife4jConfig {

    /**
     *     创建一个Docket的对象，相当于是swagger的一个实例 ： 配置开发和测试环境下开启Swagger，生产发布时关闭
     *
     *     RequestHandlerSelectors,配置要扫描接口的方式
     *     basePackage：指定扫描的包路径
     *     any：扫描全部
     *     none：全部不扫描
     *     withClassAnnotation:扫描类上的注解，如RestController
     *     withMethodAnnotation:扫描方法上的注解，如GetMapping
     *
     * @return
     */
    @Autowired
    TypeResolver typeResolver;


    @Bean
    public Docket createRestApi(Environment environment)
    {
        //设置显示的swagger环境信息,判断是否处在自己设定的环境当中,为了安全生产环境不开放Swagger
        Profiles profiles=Profiles.of("dev","test");
        boolean flag=environment.acceptsProfiles(profiles);

        /*添加接口请求头参数配置 没有的话 可以忽略*/
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").defaultValue("设置token默认值").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        //创建一个Docket的对象，相当于是swagger的一个实例
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("1.x版本")
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.sq.zbnss.controller"))  //这里采用包扫描的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //这里采用包含注解的方式来确定要显示的接口
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);

    }

    ///配置相关的api信息
    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .description("API调试文档")
                //作者信息
                .contact(new Contact("bb", "http://ip地址:12026/doc.html", "110@qq.com"))
                .version("v1.0")
                .title("nss服务API文档")
                //服务Url
                .termsOfServiceUrl("")
                .build();
    }




}
