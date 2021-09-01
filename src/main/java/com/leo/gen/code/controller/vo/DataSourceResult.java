package com.leo.gen.code.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * 数据库配置实体类
 *
 * @author leo
 */
@Data
@AllArgsConstructor
public class DataSourceResult {

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源名称
     */
    private String dataSourceName;
}
