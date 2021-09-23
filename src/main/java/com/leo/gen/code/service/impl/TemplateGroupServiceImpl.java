package com.leo.gen.code.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leo.gen.code.dao.TemplateGroupDao;
import com.leo.gen.code.entity.QueryGroupEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.service.TemplateGroupService;
import com.leo.gen.code.util.Constants;
import com.leo.gen.code.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 模板组服务类
 * @author leo
 */
@Service("templateGroupService")
public class TemplateGroupServiceImpl implements TemplateGroupService {

    @Resource
    private TemplateGroupDao templateGroupDao;

    @Override
    public Integer insertTemplateGroup(TemplateGroupEntity templateGroupEntity) {
        templateGroupEntity.setDataStatus((long) Constants.DataStatus.OPEN);
        templateGroupEntity.setCreateTime(new Date());
        templateGroupEntity.setUpdateTime(new Date());
        return templateGroupDao.insertTemplateGroup(templateGroupEntity);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return Integer 执行结果条数
     */
    @Override
    public Integer deleteTemplateGroupById(Long id) {
        return templateGroupDao.deleteTemplateGroupById(id);
    }

    /**
     * 根据id修改
     *
     * @param templateGroupEntity 模板组数据对象
     * @return Integer 执行结果条数
     */
    @Override
    public Integer updateTemplateGroupById(TemplateGroupEntity templateGroupEntity) {
        templateGroupEntity.setUpdateTime(new Date());
        return templateGroupDao.updateTemplateGroupById(templateGroupEntity);
    }

    /**
     * 查询所有模板组
     *
     * @return TemplateGroupEntity
     */
    @Override
    public List<TemplateGroupEntity> selectAllTemplateGroupName() {
        return templateGroupDao.selectAllTemplateGroupName();
    }

    @Override
    public TemplateGroupEntity selectOneById(Long id) {
        return templateGroupDao.selectOneById(id);
    }

    @Override
    public List<TemplateGroupEntity> selectTemplateGroupByIdList(List<Long> idList) {
        return templateGroupDao.selectTemplateGroupByIdList(idList);
    }

    @Override
    public PageResult<TemplateGroupEntity> queryTemplateGroupByPage(QueryGroupEntity queryGroupEntity) {
        Page<?> page = PageHelper.startPage(queryGroupEntity.getPage(), queryGroupEntity.getLimit());
        List<TemplateGroupEntity> list = templateGroupDao.queryTemplateGroupList(queryGroupEntity);
        return new PageResult<>(list, (int) page.getTotal(), queryGroupEntity.getLimit(), queryGroupEntity.getPage());
    }
}
