package com.peramdy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author peramdy on 2017/9/12.
 */
@SpringBootApplication
@MapperScan(value = "com.peramdy.mapper")
public class PdApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PdApplication.class);
        springApplication.run(args);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PdApplication.class).bannerMode(Banner.Mode.CONSOLE);
    }
}
