package com.peramdy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.bind.RelaxedPropertyResolver;

import javax.sql.DataSource;

/**
 * Created by peramdy on 2017/9/18.
 */
public class DsConfig {

    public static DataSource createDataSource(RelaxedPropertyResolver relaxedPropertyResolver) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(relaxedPropertyResolver.getProperty("url"));
        dataSource.setDriverClassName(relaxedPropertyResolver.getProperty("driver-class-name"));
        dataSource.setUsername(relaxedPropertyResolver.getProperty("username"));
        dataSource.setPassword(relaxedPropertyResolver.getProperty("password"));
        dataSource.setInitialSize(Integer.parseInt(relaxedPropertyResolver.getProperty("initial-size")));
        dataSource.setMinIdle(Integer.valueOf(relaxedPropertyResolver.getProperty("min-idle")));
        dataSource.setMaxWait(Integer.valueOf(relaxedPropertyResolver.getProperty("max-wait")));
        dataSource.setMaxActive(Integer.valueOf(relaxedPropertyResolver.getProperty("max-active")));
        dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(relaxedPropertyResolver.getProperty("min-evictable-idle-time-millis")));
        return dataSource;
    }

}
