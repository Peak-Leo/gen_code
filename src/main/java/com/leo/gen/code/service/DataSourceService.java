package com.leo.gen.code.service;

import com.leo.gen.code.entity.DataSourceEntity;
import com.leo.gen.code.entity.QueryDataSourceEntity;
import com.leo.gen.code.util.PageResult;

import java.util.List;

/**
 * 数据源服务类
 *
 * @author leo
 */
public interface DataSourceService {

    /**
     * 新增
     *
     * @param dataSourceEntity 数据库配置数据对象
     * @return Integer 执行结果条数
     */
    Integer insertDataSource(DataSourceEntity dataSourceEntity);

    /**
     * 根据id删除
     *
     * @param id id
     * @return Integer 执行结果条数
     */
    Integer deleteDataSourceById(Long id);

    /**
     * 根据id修改
     *
     * @param dataSourceEntity 数据库配置数据对象
     * @return Integer 执行结果条数
     */
    Integer updateDataSourceById(DataSourceEntity dataSourceEntity);

    /**
     * 查询所有数据源
     *
     * @return DataSourceEntity
     */
    List<DataSourceEntity> selectAllDataSourceName();

    /**
     * 根据id查询配置信息
     *
     * @param id id
     * @return DataSourceEntity
     */
    DataSourceEntity selectOneById(Long id);

    /**
     * 分页查询数据源配置信息
     *
     * @param queryDataSourceEntity 查询数据源实体对象
     * @return List<DataSourceEntity>
     */
    PageResult<DataSourceEntity> queryDataSourceByPage(QueryDataSourceEntity queryDataSourceEntity);
}
