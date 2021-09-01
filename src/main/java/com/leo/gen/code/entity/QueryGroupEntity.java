package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 模板组-分页查询条件
 * @author leo
 */
@Data
public class QueryGroupEntity implements Serializable {

    private String groupName;

    private Integer page;

    private Integer limit;

}
