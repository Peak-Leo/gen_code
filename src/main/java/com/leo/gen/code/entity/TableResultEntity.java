package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 表结果对象
 * @author leo
 */
@Data
public class TableResultEntity implements Serializable {

    private String tableName;

    private String tableComment;

    private String engine;

    private String createTime;
}
