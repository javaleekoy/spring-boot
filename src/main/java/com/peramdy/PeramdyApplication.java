package com.peramdy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by peramdy on 2017/9/12.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.peramdy")
//@MapperScan(value = {"classpath:mapper/*.xml"})
@MapperScan("com.peramdy.dao")
public class PeramdyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeramdyApplication.class, args);
    }
}
