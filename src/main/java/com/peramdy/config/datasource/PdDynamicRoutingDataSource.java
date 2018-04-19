package com.peramdy.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author pd 2018/4/2.
 * 该类继承自 AbstractRoutingDataSource 类，在访问数据库时会调用该类的 determineCurrentLookupKey() 方法获取数据库实例的 key
 */
public class PdDynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static Logger logger = LoggerFactory.getLogger(PdDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("determineCurrentLookupKey：[{}]", PdDynamicDsContextHolder.getDataSourceKey());
        return PdDynamicDsContextHolder.getDataSourceKey();
    }
}
