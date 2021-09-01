package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板组实体
 *
 * @author leo
 */
@Data
public class TemplateGroupEntity implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板组名称
     */
    private String groupName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 作者
     */
    private String author;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 主包名
     */
    private String mainPackage;

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
