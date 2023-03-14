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
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
        //创建一个Docket的对象，相当于是swagger的一个实例
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("1.x版本")
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                // 这里指定Controller扫描包路径:设置要扫描的接口类，一般是Controller类
                .apis(RequestHandlerSelectors.basePackage("org.sq.zbnss.controller"))  //这里采用包扫描的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //这里采用包含注解的方式来确定要显示的接口
//                .apis(RequestHandlerSelectors.any())
                // 配置过滤哪些，设置对应的路径才获取
                .paths(PathSelectors.any())
                .build();
                //防止Controller中参数中没有实体类或者返回值不是实体类导致Swagger Models页面扫描不到的情况
//                .additionalModels(typeResolver.resolve(EmailNotice.class))
//                .additionalModels(typeResolver.resolve(SmsNotice.class))
//                .additionalModels(typeResolver.resolve(WeChatNotice.class));
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

    private Tag[] getTags() {

        return new Tag[] {
                new Tag("alibaba", "阿里巴巴API接口"),
                new Tag("tencent", "腾讯API接口")
        };
    }

}
