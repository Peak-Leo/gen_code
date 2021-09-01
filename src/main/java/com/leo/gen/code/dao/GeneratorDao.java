package com.leo.gen.code.dao;

import java.util.List;
import java.util.Map;

/**
 * 通用sql查询DAO
 *
 * @author leo
 */
public interface GeneratorDao {

    /**
     * 查询当前库或指定库所有表
     * @param map 入参
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> queryAllTable(Map<String, Object> map);

    /**
     * 查询表基础信息
     * @param tableName 表名
     * @return Map<String, String>
     */
    Map<String, String> queryTable(String tableName);

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