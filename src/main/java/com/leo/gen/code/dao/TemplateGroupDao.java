package com.leo.gen.code.dao;

import com.leo.gen.code.entity.QueryGroupEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;

import java.util.List;

/**
 * 模板组
 *
 * @author leo
 */
public interface TemplateGroupDao {

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
     * 根据id查数据
     *
     * @param id id
     * @return TemplateGroupEntity
     */
    TemplateGroupEntity selectOneById(Long id);

    /**
     * 根据id集合查询模板组集合信息
     *
     * @param idList id集合
     * @return List<TemplateGroupEntity>
     */
    List<TemplateGroupEntity> selectTemplateGroupByIdList(List<Long> idList);

    /**
     * 查询模板组集合信息
     *
     * @param queryGroupEntity 查询条件
     * @return List<TemplateGroupEntity>
     */
    List<TemplateGroupEntity> queryTemplateGroupList(QueryGroupEntity queryGroupEntity);
}
