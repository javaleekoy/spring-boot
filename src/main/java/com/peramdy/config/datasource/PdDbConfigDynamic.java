package com.peramdy.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author pd
 */
@Configuration
@MapperScan(basePackages = PdDbConfigDynamic.PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplateDynamic")
public class PdDbConfigDynamic {

    static final String PACKAGE = "com.peramdy.dao.dynamic";

    static final String MAPPER_LOCATION = "classpath:mapper/dynamic*//*.xml";

    public static Logger logger = LoggerFactory.getLogger(PdDbConfigDynamic.class);

    @Autowired
    @Qualifier(value = "dynamicDs")
    private DataSource dynamicDs;

    @Bean
    public SqlSessionFactory sqlSessionFactoryDynamic() throws Exception {
        logger.info("sqlSessionFactoryDynamic：[{}]", dynamicDs.toString());
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /** 配置数据源，若没有则不能实现切换（重点） **/
        sqlSessionFactoryBean.setDataSource(dynamicDs);
        /** 设置扫描路径 **/
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * SqlSessionTemplate是SqlSession的实现类，较SqlSession的默认实现类DefaultSqlSession来说，是线程安全的
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionTemplateDynamic")
    public SqlSessionTemplate sqlSessionTemplateDynamic() throws Exception {
        logger.info("SqlSessionTemplate");
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryDynamic());
        return template;
    }

    /**
     * 事务生效需要添加 @Transactional 注解
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDs);
    }

}
