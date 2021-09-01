package com.leo.gen.code.controller.vo;

import com.leo.gen.code.entity.TemplateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板分页查询结果对象
 *
 * @author leo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateGroupPage extends TemplateEntity {

    /**
     * 模板组名称
     */
    private String groupName;

    /**
     * 模板类型名称
     */
    private String templateTypeName;

    /**
     * 状态名称
     */
    private String dataStatusDesc;
}



