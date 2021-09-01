package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库配置实体类
 *
 * @author leo
 */
@Data
public class DataSourceEntity implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * jdbc驱动类名
     */
    private String driverClassName;

    /**
     * ip或域名
     */
    private String url;

    /**
     * 数据库
     */
    private String dbName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 数据状态 0:禁用,1:启用,2:已删除
     */
    private Integer dataStatus;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 更新日期
     */
    private Date updateTime;
}
