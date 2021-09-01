package com.leo.gen.code.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 页面入参-生成模板预览查询条件对象
 * @author leo
 */
@Data
public class QueryTemplateEntity implements Serializable {

    private Long groupId;

    private List<String> tableNameList;
}
