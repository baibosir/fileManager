package org.sq.zbnss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@MapperScan("org.sq.zbnss.dao")
public class SqNssApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqNssApplication.class, args);
    }

}
