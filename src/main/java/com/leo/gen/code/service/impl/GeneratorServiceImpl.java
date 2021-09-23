package com.leo.gen.code.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.leo.gen.code.dao.GeneratorDao;
import com.leo.gen.code.entity.QueryTableEntity;
import com.leo.gen.code.entity.TableResultEntity;
import com.leo.gen.code.service.GeneratorService;
import com.leo.gen.code.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用sql查询服务
 *
 * @author leo
 */
@Service("generatorService")
public class GeneratorServiceImpl implements GeneratorService {

    @Resource
    private GeneratorDao generatorDao;

    @Override
    public PageResult<TableResultEntity> queryAllTableByPage(QueryTableEntity queryTableEntity) {
        Page<?> page = PageHelper.startPage(queryTableEntity.getPage(), queryTableEntity.getLimit());
        Map<String, Object> map = Maps.newHashMap();
        map.put("tableName", queryTableEntity.getTableName());
        List<Map<String, Object>> maps = generatorDao.queryAllTable(map);
        List<TableResultEntity> list = new ArrayList<>();
        for (Map<String, Object> objectMap : maps) {
            TableResultEntity tableEntity = new TableResultEntity();
            tableEntity.setTableName((String) objectMap.get("tableName"));
            tableEntity.setTableComment((String) objectMap.get("tableComment"));
            tableEntity.setEngine((String) objectMap.get("engine"));
            tableEntity.setCreateTime(String.valueOf(objectMap.get("createTime")));
            list.add(tableEntity);
        }
        return new PageResult<>(list, (int) page.getTotal(), queryTableEntity.getLimit(), queryTableEntity.getPage());
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColumns(tableName);
    }

    @Override
    public List<String> connectTest() {
        return generatorDao.connectTest();
    }

}
