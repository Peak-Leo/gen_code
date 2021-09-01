CREATE TABLE `t_data_source` (
	`id` BIGINT(20) NOT NULL auto_increment COMMENT '主键',
	`data_source_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '数据源名称',
	`driver_class_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'jdbc驱动类名',
	`url` VARCHAR(200) NOT NULL DEFAULT '' COMMENT 'ip或域名',
	`db_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '数据库',
	`user_name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '用户名',
	`user_password` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '密码',
	`data_status` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '0:禁用,1:启用,2:已删除',
	`create_time` DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='数据库源'
COLLATE='utf8_general_ci'
;

INSERT INTO `t_data_source` (`id`, `data_source_name`, `driver_class_name`, `url`, `db_name`, `user_name`, `user_password`, `data_status`, `create_time`, `update_time`) VALUES (1, 'druid_test', 'com.mysql.jdbc.Driver', 'jdbc:mysql://127.0.0.1:3306', 'test', 'root', 'root', 1, now(), now());


CREATE TABLE `t_template_group` (
	`id` BIGINT(20) NOT NULL auto_increment COMMENT '主键',
	`group_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '模板组名称',
	`module_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '模块名',
	`author` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '作者',
	`table_prefix` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '表前缀',
	`main_package` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '主包名',
	`data_status` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '0:禁用,1:启用,2:已删除',
	`create_time` DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='模板组'
COLLATE='utf8_general_ci'
;

CREATE TABLE `t_template` (
	`id` BIGINT(20) NOT NULL auto_increment COMMENT '主键',
	`group_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '模板组id',
	`template_name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '模板名',
	`context` TEXT NOT NULL DEFAULT '' COMMENT '模板内容',
	`template_type` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '1:java,2:xml,3:html,4:javascript',
	`package_path` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '生成包名',
	`file_name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '文件名',
	`data_status` TINYINT(4) NOT NULL DEFAULT 1 COMMENT '0:禁用,1:启用,2:已删除',
	`create_time` DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
	`update_time` DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '更新时间',
	`module_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '生成包名',
	PRIMARY KEY (`id`)
)
COMMENT='模板信息'
COLLATE='utf8_general_ci'
;


