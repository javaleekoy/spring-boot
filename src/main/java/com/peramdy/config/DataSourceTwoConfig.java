package com.peramdy.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by peramdy on 2017/9/18.
 */
@Configuration
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactoryTwo", basePackages = DataSourceTwoConfig.PACKAGE)
public class DataSourceTwoConfig implements EnvironmentAware {

    static final String PACKAGE = "com.peramdy.dao.slaver";
    static final String MAPPER_LOCATION = "classpath:mapper/slaver/*.xml";

    private RelaxedPropertyResolver relaxedPropertyResolver;

    @Override
    public void setEnvironment(Environment environment) {

        this.relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "spring.slaver.datasource.");

    }

    @Bean(name = "dataSourceTwo")
    public DataSource dataSource() {
        DataSource dataSource = DsConfig.createDataSource(relaxedPropertyResolver);
        return dataSource;
    }


    @Bean(name = "sqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceTwo") DataSource dataSource) throws Exception {
        SqlSessionFactory sqlSessionFactory = SfConfig.createSqlSessionFactory(dataSource, MAPPER_LOCATION);
        return sqlSessionFactory;
    }

    @Bean(value = "transactionManagerTwo")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }


}
