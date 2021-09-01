package com.leo.gen.code.service;

import com.leo.gen.code.entity.QueryGroupEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.util.PageResult;

import java.util.List;

/**
 * 模板组服务
 *
 * @author leo
 */
public interface TemplateGroupService {

    /**
     * 新增
     *
     * @param templateGroupEntity 模板组数据对象
     * @return Integer 执行结果条数
     */
    Integer insertTemplateGroup(TemplateGroupEntity templateGroupEntity);

    /**
     * 根据id删除
     *
     * @param id id
     * @return Integer 执行结果条数
     */
    Integer deleteTemplateGroupById(Long id);

    /**
     * 根据id修改
     *
     * @param templateGroupEntity 模板组数据对象
     * @return Integer 执行结果条数
     */
    Integer updateTemplateGroupById(TemplateGroupEntity templateGroupEntity);

    /**
     * 查询所有模板组
     *
     * @return TemplateGroupEntity
     */
    List<TemplateGroupEntity> selectAllTemplateGroupName();

    /**
     * 根据ID查询模板组信息
     *
     * @param id id
     * @return TemplateGroupEntity
     */
    TemplateGroupEntity selectOneById(Long id);

    /**
     * 根据id集合查询模板组信息
     *
     * @param idList id集合
     * @return List<TemplateGroupEntity>
     */
    List<TemplateGroupEntity> selectTemplateGroupByIdList(List<Long> idList);

    /**
     * 分页查询模板组信息
     *
     * @param queryGroupEntity 查询条件
     * @return PageResult<TemplateGroupEntity>
     */
    PageResult<TemplateGroupEntity> queryTemplateGroupByPage(QueryGroupEntity queryGroupEntity);
}
