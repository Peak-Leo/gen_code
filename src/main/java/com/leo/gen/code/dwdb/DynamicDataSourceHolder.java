package com.leo.gen.code.dwdb;

/**
 * 数据源切换控制类
 *
 * @author leo
 */
public class DynamicDataSourceHolder {

    /**
     * 使用ThreadLocal记录当前线程的数据源KEY
     */
    private static ThreadLocal<DynamicDataSourceGlobal> DATA_SOURCE_HOLDER = new ThreadLocal<>();

    private DynamicDataSourceHolder() {
    }

    /**
     * 设置数据源KEY
     *
     * @param dataSource 数据源切换枚举
     */
    public static void putDataSource(DynamicDataSourceGlobal dataSource) {
        DATA_SOURCE_HOLDER.set(dataSource);
    }

    /**
     * 获取当前线程的数据源KEY
     *
     * @return DynamicDataSourceGlobal
     */
    public static DynamicDataSourceGlobal getDataSource() {
        return DATA_SOURCE_HOLDER.get();
    }

    /**
     * 移除当前线程的数据源key
     */
    public static void clearDataSource() {
        DATA_SOURCE_HOLDER.remove();
    }
}