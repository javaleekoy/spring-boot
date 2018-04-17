package com.peramdy;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * @author pd 2017/9/12.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PeramdyApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PeramdyApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
    }
}
