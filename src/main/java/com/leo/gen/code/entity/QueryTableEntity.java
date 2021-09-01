package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 入参-查询表条件对象
 * @author leo
 */
@Data
public class QueryTableEntity implements Serializable {

    private Long sourceId;

    private String tableName;

    private Integer page;

    private Integer limit;
}
