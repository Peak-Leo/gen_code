package com.leo.gen.code.controller;

import com.leo.gen.code.controller.vo.TemplateGroupPage;
import com.leo.gen.code.entity.QueryTemplatePageEntity;
import com.leo.gen.code.entity.TemplateEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.service.TemplateGroupService;
import com.leo.gen.code.service.TemplateService;
import com.leo.gen.code.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模板
 *
 * @author leo
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateGroupService templateGroupService;

    @PostMapping("/list")
    public Result<?> queryTemplateByPage(@RequestBody QueryTemplatePageEntity queryTemplatePageEntity) {
        PageResult<TemplateEntity> pageResult = templateService.queryTemplateByPage(queryTemplatePageEntity);
        List<TemplateEntity> list = pageResult.getList();
        if (CollectionUtils.isEmpty(list)) {
            return Result.ok(pageResult);
        }
        List<Long> groupIdList = list.stream().map(TemplateEntity::getGroupId).collect(Collectors.toList());
        List<TemplateGroupEntity> groupList = templateGroupService.selectTemplateGroupByIdList(groupIdList);
        Map<Long, TemplateGroupEntity> map = groupList.stream().collect(
                Collectors.toMap(TemplateGroupEntity::getId, group -> group, (k1, k2) -> k1));

        List<TemplateGroupPage> resultList = new ArrayList<>();
        for (TemplateEntity template : list) {
            TemplateGroupPage templateGroupPage = new TemplateGroupPage();
            BeanUtil.copy(template, templateGroupPage);
            if (map.containsKey(template.getGroupId())) {
                TemplateGroupEntity group = map.get(template.getGroupId());
                templateGroupPage.setGroupName(group.getGroupName());
                templateGroupPage.setPackagePath(PlaceholderUtils.processPackagePath(group.getMainPackage(), group.getModuleName(), template.getPackagePath()));
                templateGroupPage.setTemplateTypeName(Constants.TemplateType.getName(templateGroupPage.getTemplateType()));
                templateGroupPage.setDataStatusDesc(Constants.DataStatus.getName(templateGroupPage.getDataStatus().intValue()));
            }
            resultList.add(templateGroupPage);
        }
        return Result.ok(new PageResult<>(resultList, pageResult.getTotalCount(), pageResult.getPageSize(), pageResult.getCurrPage()));
    }

    @PostMapping("/get")
    public Result<TemplateEntity> queryTemplateById(@RequestBody TemplateEntity templateEntity) {
        return Result.ok(templateService.selectOneById(templateEntity.getId()));
    }

    @PostMapping("/insert")
    public Result<?> insertTemplate(@RequestBody TemplateEntity templateEntity) {
        return Result.ok(templateService.insertTemplate(templateEntity));
    }

    @PostMapping("/delete")
    public Result<?> deleteTemplateById(@RequestBody TemplateEntity templateEntity) {
        return Result.ok(templateService.deleteTemplateById(templateEntity.getId()));
    }

    @PostMapping("/update")
    public Result<?> updateTemplateById(@RequestBody TemplateEntity templateEntity) {
        return Result.ok(templateService.updateTemplateById(templateEntity));
    }
}
