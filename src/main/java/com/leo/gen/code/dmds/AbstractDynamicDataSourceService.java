package com.leo.gen.code.dmds;

import com.alibaba.druid.pool.DruidDataSource;
import com.leo.gen.code.config.db.property.DbProperty;
import com.leo.gen.code.entity.DataSourceEntity;
import com.leo.gen.code.util.IpAddressUtil;
import com.leo.gen.code.util.SpringUtil;

import java.sql.SQLException;


/**
 * 数据源模板类
 *
 * @author leo
 */
public abstract class AbstractDynamicDataSourceService {

    /**
     * 初始化数据源
     *
     * @param dataSourceEntity 数据源实体
     * @param retryCount       重试次数
     * @param maxWaitMillis    最大等待毫秒数
     * @return Object
     */
    public Object initDataSource(DataSourceEntity dataSourceEntity, Integer retryCount, Integer maxWaitMillis) {
        DbProperty dbProperty = (DbProperty) SpringUtil.getBean("dbProperty");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceEntity.getDriverClassName());
        dataSource.setName(DynamicDataSourceGlobal.CUSTOM.name());
        dataSource.setUrl(IpAddressUtil.getUrl(dataSourceEntity.getUrl(), dataSourceEntity.getDbName()));
        dataSource.setUsername(dataSourceEntity.getUserName());
        dataSource.setPassword(dataSourceEntity.getUserPassword());
        dataSource.setValidationQuery(dbProperty.getValidationQuery());
        dataSource.setMaxActive(dbProperty.getMaxActive());
        dataSource.setTestOnBorrow(dbProperty.isTestOnBorrow());
        dataSource.setTestOnReturn(dbProperty.isTestOnReturn());
        dataSource.setTestWhileIdle(dbProperty.isTestWhileIdle());
        try {
            dataSource.setFilters(dbProperty.getFilters());
        } catch (SQLException ex) {
            // 吃掉
        }
        // 失败后重连的次数
        dataSource.setConnectionErrorRetryAttempts(retryCount == null ? 3 : retryCount);
        // 请求失败之后中断
        dataSource.setBreakAfterAcquireFailure(true);
        // 请求失败后等待时间
        dataSource.setMaxWait(maxWaitMillis == null ? 1000 : maxWaitMillis);
        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringUtil.getBean("dynamicDataSource");
        dynamicDataSource.setCustomDataSource(dataSource);
        dynamicDataSource.afterPropertiesSet();
        DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.CUSTOM);
        Object obj;
        try {
            obj = execute();
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
        return obj;
    }

    /**
     * 执行具体业务
     *
     * @return Object
     */
    protected abstract Object execute();
}
