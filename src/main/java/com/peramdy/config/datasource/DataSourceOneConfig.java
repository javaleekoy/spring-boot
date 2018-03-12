package com.peramdy.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author peramdy on 2017/9/16.
 */
@Configuration
@EnableTransactionManagement
public class DataSourceOneConfig implements EnvironmentAware {

    private RelaxedPropertyResolver relaxedPropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
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


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(value = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}
