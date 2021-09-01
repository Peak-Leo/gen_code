package com.leo.gen.code.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 代码生成-入参对象
 * @author leo
 */
@Data
public class GenCodeParam implements Serializable {

    private Long sourceId;

    private Long groupId;

    private List<String> tables;
}
