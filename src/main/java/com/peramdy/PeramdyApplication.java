package com.peramdy;

import com.peramdy.annotation.UserIdResolver;
import com.peramdy.filter.AddRequestParsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peramdy on 2017/9/12.
 *
 * @author peramdy
 */
@SpringBootApplication
@ComponentScan
public class PeramdyApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(PeramdyApplication.class, args);
    }

    /**
     * @return
     * @description filter 配置
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new AddRequestParsFilter());
        List<String> pathList = new ArrayList<String>();
        pathList.add("/utils/download/*");
        registrationBean.setUrlPatterns(pathList);
        return registrationBean;
    }

    /**
     * @param argumentResolvers
     * @description extends WebMvcConfigurerAdapter  handlerMethodArgumentResolver配置（结合自定义注解）
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new UserIdResolver());
    }


}
