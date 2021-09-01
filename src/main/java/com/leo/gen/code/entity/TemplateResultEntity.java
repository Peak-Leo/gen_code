package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成模板前的预览-结果对象
 * @author leo
 */
@Data
public class TemplateResultEntity implements Serializable {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 模板组名称
     */
    private String groupName;

    /**
     * 生成路径
     */
    private String packagePath;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表前缀
     */
    private String tablePrefix;

    /**
     * 模板名称
     */
    private String templateName;
}
