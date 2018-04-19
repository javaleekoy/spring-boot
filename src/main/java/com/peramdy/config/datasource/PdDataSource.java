package com.peramdy.config.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pd
 */
@Configuration
public class PdDataSource {

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource createDataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource createDataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDs")
    public DataSource dataSource() {
        PdDynamicRoutingDataSource pdDynamicDS = new PdDynamicRoutingDataSource();
        Map<Object, Object> map = new HashMap<>(2);
        map.put("master", createDataSourceOne());
        map.put("slave", createDataSourceTwo());
        // master、slave 数据源为target数据源
        pdDynamicDS.setTargetDataSources(map);
        // master 为默认数据源
        pdDynamicDS.setDefaultTargetDataSource(createDataSourceOne());
        return pdDynamicDS;
    }
}
