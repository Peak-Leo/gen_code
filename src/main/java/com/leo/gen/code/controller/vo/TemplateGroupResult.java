package com.leo.gen.code.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板组-结果对象
 *
 * @author leo
 */
@Data
@AllArgsConstructor
public class TemplateGroupResult implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 模板组名称
     */
    private String groupName;
}
