package com.leo.gen.code.service;

import com.leo.gen.code.entity.QueryTemplatePageEntity;
import com.leo.gen.code.entity.TemplateEntity;
import com.leo.gen.code.util.PageResult;

import java.util.List;

/**
 * 模板服务
 *
 * @author leo
 */
public interface TemplateService {

    /**
     * 新增
     *
     * @param templateEntity 模板实体对象
     * @return Integer 执行结果条数
     */
    Integer insertTemplate(TemplateEntity templateEntity);

    /**
     * 删除
     *
     * @param id 模板组ID
     * @return Integer 执行结果条数
     */
    Integer deleteTemplateById(Long id);

    /**
     * 更新
     *
     * @param templateEntity 模板实体对象
     * @return Integer 执行结果条数
     */
    Integer updateTemplateById(TemplateEntity templateEntity);

    /**
     * 根据ID查询模板信息
     *
     * @param id 模板ID
     * @return TemplateEntity
     */
    TemplateEntity selectOneById(Long id);

    /**
     * 根据模板组ID查询模板信息
     *
     * @param groupId 模板组ID
     * @return List<TemplateEntity>
     */
    List<TemplateEntity> selectTemplateByGroupId(Long groupId);

    /**
     * 分页查询
     *
     * @param queryTemplatePageEntity 查询条件
     * @return PageResult<TemplateEntity>
     */
    PageResult<TemplateEntity> queryTemplateByPage(QueryTemplatePageEntity queryTemplatePageEntity);
}
