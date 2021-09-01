package com.leo.gen.code.service;

import com.leo.gen.code.entity.QueryTableEntity;
import com.leo.gen.code.entity.TableResultEntity;
import com.leo.gen.code.util.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 通用sql查询服务
 *
 * @author leo
 */
public interface GeneratorService {

    /**
     * 分页查询当前库或指定库所有表
     * @param queryTableEntity 入参
     * @return PageResult<TableResultEntity>
     */
    PageResult<TableResultEntity> queryAllTableByPage(QueryTableEntity queryTableEntity);

    /**
     * 查询表基础信息
     * @param tableName 表名
     * @return Map<String, String>
     */
    Map<String,String> queryTable(String tableName);

    /**
     * 查询表结构-字段信息
     * @param tableName 表名
     * @return List<Map<String, String>>
     */
    List<Map<String, String>> queryColumns(String tableName);

    /**
     * 测试连接
     * @return List<String>
     */
    List<String> connectTest();
}
