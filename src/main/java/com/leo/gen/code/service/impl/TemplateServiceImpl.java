package com.leo.gen.code.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leo.gen.code.dao.TemplateDao;
import com.leo.gen.code.entity.QueryTemplatePageEntity;
import com.leo.gen.code.entity.TemplateEntity;
import com.leo.gen.code.service.TemplateService;
import com.leo.gen.code.util.Constants;
import com.leo.gen.code.util.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 模板服务
 *
 * @author leo
 */
@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Override
    public Integer insertTemplate(TemplateEntity templateEntity) {
        templateEntity.setDataStatus((long) Constants.DataStatus.OPEN);
        if (StringUtils.isBlank(templateEntity.getContext())){
            templateEntity.setContext("");
        }
        if (StringUtils.isBlank(templateEntity.getModuleName())){
            templateEntity.setModuleName("");
        }
        templateEntity.setCreateTime(new Date());
        templateEntity.setUpdateTime(new Date());
        return templateDao.insertTemplate(templateEntity);
    }

    @Override
    public Integer deleteTemplateById(Long id) {
        return templateDao.deleteTemplateById(id);
    }

    @Override
    public Integer updateTemplateById(TemplateEntity templateEntity) {
        return templateDao.updateTemplateById(templateEntity);
    }

    @Override
    public TemplateEntity selectOneById(Long id) {
        return templateDao.selectOneById(id);
    }

    @Override
    public List<TemplateEntity> selectTemplateByGroupId(Long groupId) {
        return templateDao.selectTemplateByGroupId(groupId);
    }

    @Override
    public PageResult<TemplateEntity> queryTemplateByPage(QueryTemplatePageEntity queryTemplatePageEntity) {
        Page<?> page = PageHelper.startPage(queryTemplatePageEntity.getPage(), queryTemplatePageEntity.getLimit());
        List<TemplateEntity> list = templateDao.queryTemplateList(queryTemplatePageEntity);
        return new PageResult<>(list, (int) page.getTotal(), queryTemplatePageEntity.getLimit(), queryTemplatePageEntity.getPage());
    }


}
