package com.leo.gen.code.dmds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源
 *
 * @author leo
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Object defaultDataSource;

    private Object customDataSource;

    @Override
    public void afterPropertiesSet() {
        //重置AbstractRoutingDataSource的数据源为主数据源(默认数据源)
        setDefaultTargetDataSource(defaultDataSource);
        //缓存系统当前支持的数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceGlobal.DEFAULT.name(), defaultDataSource);
        if (customDataSource != null) {
            targetDataSources.put(DynamicDataSourceGlobal.CUSTOM.name(), customDataSource);
        }
        //设置目标数据源为系统备选数据源
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        //通过DynamicDataSourceHolder, 获取当前线程的数据源KEY
        DynamicDataSourceGlobal currentDataSource = DynamicDataSourceHolder.getDataSource();
        //如果业务规则没有设置数据源key, 或者已经设置该数据源key为默认数据源，则设置当前线程数据源为默认数据源
        if (currentDataSource == null || currentDataSource == DynamicDataSourceGlobal.DEFAULT) {
            log(" enable default datasource ");
            return DynamicDataSourceGlobal.DEFAULT.name();
        }
        //设置当前线程数据源为读数据源
        log(" enable custom datasource ");
        return DynamicDataSourceGlobal.CUSTOM.name();
    }

    private void log(String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(Thread.currentThread().getName() + msg);
        }
    }

    public void setDefaultDataSource(Object defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public void setCustomDataSource(Object customDataSource) {
        this.customDataSource = customDataSource;
    }
}
