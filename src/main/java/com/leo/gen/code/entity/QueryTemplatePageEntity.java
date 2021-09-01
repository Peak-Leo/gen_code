package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 模板-查询实体
 *
 * @author leo
 */
@Data
public class QueryTemplatePageEntity implements Serializable {

    /**
     * 模板组id
     */
    private Long groupId;

    /**
     * 模板名
     */
    private String template;

    /**
     * 模板类型 1:java,2:xml,3:html,4:javascript
     */
    private Integer type;

    private Integer page;

    private Integer limit;
}
