package com.leo.gen.code.controller;

import com.leo.gen.code.entity.QueryGroupEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.service.TemplateGroupService;
import com.leo.gen.code.util.PageResult;
import com.leo.gen.code.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模板组
 *
 * @author leo
 */
@RestController
@RequestMapping("/group")
public class TemplateGroupController {

    private final TemplateGroupService templateGroupService;

    public TemplateGroupController(TemplateGroupService templateGroupService) {
        this.templateGroupService = templateGroupService;
    }

    @PostMapping("/list")
    public Result<?> queryTemplateGroupByPage(@RequestBody QueryGroupEntity queryGroupEntity) {
        PageResult<TemplateGroupEntity> pageResult = templateGroupService.queryTemplateGroupByPage(queryGroupEntity);
        return Result.ok(pageResult);
    }

    @PostMapping("/get")
    public Result<TemplateGroupEntity> queryTemplateGroupById(@RequestBody TemplateGroupEntity templateGroupEntity) {
        return Result.ok(templateGroupService.selectOneById(templateGroupEntity.getId()));
    }

    @PostMapping("/insert")
    public Result<?> insertTemplateGroupById(@RequestBody TemplateGroupEntity templateGroupEntity) {
        return Result.ok(templateGroupService.insertTemplateGroup(templateGroupEntity));
    }

    @PostMapping("/update")
    public Result<?> updateTemplateGroupById(@RequestBody TemplateGroupEntity templateGroupEntity) {
        return Result.ok(templateGroupService.updateTemplateGroupById(templateGroupEntity));
    }

    @PostMapping("/delete")
    public Result<?> deleteTemplateGroupById(@RequestBody TemplateGroupEntity templateGroupEntity) {
        return Result.ok(templateGroupService.deleteTemplateGroupById(templateGroupEntity.getId()));
    }
}
