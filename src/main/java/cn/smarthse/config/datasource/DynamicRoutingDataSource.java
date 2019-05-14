package cn.smarthse.config.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

//该类继承自 AbstractRoutingDataSource 类，在访问数据库时会调用该类的 determineCurrentLookupKey() 方法获取数据库实例的 key


public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Log log = LogFactory.getLog(getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("Current DataSource is " + DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }
}
