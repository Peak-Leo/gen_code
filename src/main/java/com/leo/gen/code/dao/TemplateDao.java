package com.leo.gen.code.dao;

import com.leo.gen.code.entity.QueryTemplatePageEntity;
import com.leo.gen.code.entity.TemplateEntity;

import java.util.List;

/**
 * 数据库配置
 *
 * @author leo
 */
public interface TemplateDao {

    /**
     * 新增
     *
     * @param templateEntity 数据库配置数据对象
     * @return Integer 执行结果条数
     */
    Integer insertTemplate(TemplateEntity templateEntity);

    /**
     * 根据id删除
     *
     * @param id id
     * @return Integer 执行结果条数
     */
    Integer deleteTemplateById(Long id);

    /**
     * 根据id修改
     *
     * @param templateEntity 数据库配置数据对象
     * @return Integer 执行结果条数
     */
    Integer updateTemplateById(TemplateEntity templateEntity);

    /**
     * 根据模板组ID查询模板信息
     *
     * @param groupId 模板组ID
     * @return List<TemplateEntity>
     */
    List<TemplateEntity> selectTemplateByGroupId(Long groupId);

    /**
     * 查询模板信息
     *
     * @param queryTemplatePageEntity 查询条件
     * @return List<TemplateEntity>
     */
    List<TemplateEntity> queryTemplateList(QueryTemplatePageEntity queryTemplatePageEntity);

    /**
     * 根据ID查询模板信息
     *
     * @param id 模板ID
     * @return TemplateEntity
     */
    TemplateEntity selectOneById(Long id);
}
