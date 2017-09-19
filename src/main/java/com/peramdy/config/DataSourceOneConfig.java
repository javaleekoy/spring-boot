package com.peramdy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by peramdy on 2017/9/16.
 */
@Configuration
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactoryOne", basePackages = DataSourceOneConfig.PACKAGE)
public class DataSourceOneConfig implements EnvironmentAware {

    static final String PACKAGE = "com.peramdy.dao.master";
    static final String MAPPER_LOCATION="classpath:mapper/master/*.xml";

    private RelaxedPropertyResolver relaxedPropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "spring.master.datasource.");
    }

    @Bean(name = "dataSourceOne")
    @Primary
    public DataSource dataSource() {
        DataSource dataSource = DsConfig.createDataSource(relaxedPropertyResolver);
        return dataSource;
    }


    @Bean(name = "sqlSessionFactoryOne")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceOne") DataSource dataSource) throws Exception {
        SqlSessionFactory sqlSessionFactory = SfConfig.createSqlSessionFactory(dataSource, MAPPER_LOCATION);
        return sqlSessionFactory;
    }

    @Bean(value = "transactionManagerOne")
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}
