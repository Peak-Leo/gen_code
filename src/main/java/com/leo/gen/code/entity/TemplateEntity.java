package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板实体
 *
 * @author leo
 */
@Data
public class TemplateEntity implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板组id
     */
    private Long groupId;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 模板内容
     */
    private String context;

    /**
     * 模板类型 1:java,2:xml,3:html,4:javascript
     */
    private Integer templateType;

    /**
     * 生成包名
     */
    private String packagePath;

    /**
     * 生成包模块名
     */
    private String moduleName;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 数据状态 0:禁用,1:启用,2:已删除
     */
    private Long dataStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
