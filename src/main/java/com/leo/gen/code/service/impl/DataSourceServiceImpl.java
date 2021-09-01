package com.leo.gen.code.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leo.gen.code.dao.DataSourceDao;
import com.leo.gen.code.entity.DataSourceEntity;
import com.leo.gen.code.entity.QueryDataSourceEntity;
import com.leo.gen.code.service.DataSourceService;
import com.leo.gen.code.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 数据源服务类
 *
 * @author leo
 */
@Service("dataSourceService")
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceDao dataSourceDao;

    /**
     * 新增
     *
     * @param dataSourceEntity 数据库配置数据对象
     * @return Integer 执行结果条数
     */
    @Override
    public Integer insertDataSource(DataSourceEntity dataSourceEntity) {
        dataSourceEntity.setDataStatus(1);
        dataSourceEntity.setCreateTime(new Date());
        dataSourceEntity.setUpdateTime(new Date());
        return dataSourceDao.insertDataSource(dataSourceEntity);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return Integer 执行结果条数
     */
    @Override
    public Integer deleteDataSourceById(Long id) {
        return dataSourceDao.deleteDataSourceById(id);
    }

    /**
     * 根据id修改
     *
     * @param dataSourceEntity 数据库配置数据对象
     * @return Integer 执行结果条数
     */
    @Override
    public Integer updateDataSourceById(DataSourceEntity dataSourceEntity) {
        dataSourceEntity.setUpdateTime(new Date());
        return dataSourceDao.updateDataSourceById(dataSourceEntity);
    }

    @Override
    public List<DataSourceEntity> selectAllDataSourceName() {
        return dataSourceDao.selectAllDataSourceName();
    }

    @Override
    public DataSourceEntity selectOneById(Long id) {
        return dataSourceDao.selectOneById(id);
    }

    @Override
    public PageResult<DataSourceEntity> queryDataSourceByPage(QueryDataSourceEntity queryDataSourceEntity) {
        Page<?> page = PageHelper.startPage(queryDataSourceEntity.getPage(), queryDataSourceEntity.getLimit());
        List<DataSourceEntity> list = dataSourceDao.queryDataSourceList(queryDataSourceEntity);
        return new PageResult<>(list, (int) page.getTotal(), queryDataSourceEntity.getLimit(), queryDataSourceEntity.getPage());
    }
}
