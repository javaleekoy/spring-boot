package com.peramdy;

import filter.AddRequestParsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peramdy on 2017/9/12.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.peramdy")
public class PeramdyApplication
        extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

    private final static Logger logger = LoggerFactory.getLogger(PeramdyApplication.class);

    /**
     * 请求接口
     */
    private static int port;

    /**
     * ip地址
     */
    private static String ip;

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {

        try {
            container.setAddress(InetAddress.getByName(ip));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        container.setPort(port);

    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
            logger.info(String.format("----->启动地址：%s，端口地址：%d", ip, port));
        } else {
            logger.error("------->启动参数不能为空!");
            System.exit(0);
        }

        SpringApplication.run(PeramdyApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean  registrationBean=new FilterRegistrationBean(new AddRequestParsFilter());
        List<String> pathList=new ArrayList<String>();
        pathList.add("/stu/download/*");
        registrationBean.setUrlPatterns(pathList);
        return registrationBean;
    }
}
