package com.peramdy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by peramdy on 2017/9/12.
 */
@SpringBootApplication
@MapperScan(value = {"classpath:/mapper/*.xml"})
public class PeramdyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PeramdyApplication.class, args);
    }
}
