package com.leo.gen.code.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.leo.gen.code.controller.vo.DataSourceResult;
import com.leo.gen.code.controller.vo.QueryTemplateEntity;
import com.leo.gen.code.controller.vo.TemplateGroupResult;
import com.leo.gen.code.dmds.AbstractDynamicDataSourceService;
import com.leo.gen.code.entity.*;
import com.leo.gen.code.service.*;
import com.leo.gen.code.util.PlaceholderUtils;
import com.leo.gen.code.util.Result;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 代码生成实际入口
 * 表列表
 *
 * @author leo
 */
@RestController
public class GeneratorController {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private TemplateGroupService templateGroupService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GeneratorCodeService generatorCodeService;

    /**
     * 查询所有模板组
     *
     * @return Result<List < TemplateGroupResult>>
     */
    @PostMapping("/group/list/all")
    public Result<List<TemplateGroupResult>> getAllTemplateGroup() {
        List<TemplateGroupEntity> list = templateGroupService.selectAllTemplateGroupName();
        List<TemplateGroupResult> templateGroupResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (TemplateGroupEntity templateGroupEntity : list) {
                if (templateGroupEntity == null) {
                    continue;
                }
                templateGroupResultList.add(new TemplateGroupResult(templateGroupEntity.getId(), templateGroupEntity.getGroupName()));
            }
        }
        return Result.ok(templateGroupResultList);
    }

    /**
     * 查询所有数据源
     *
     * @return Result<List < DataSourceResult>>
     */
    @PostMapping("/datasource/list/all")
    public Result<List<DataSourceResult>> getAllDataSource() {
        List<DataSourceEntity> list = dataSourceService.selectAllDataSourceName();
        List<DataSourceResult> dataSourceResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (DataSourceEntity dataSourceEntity : list) {
                if (dataSourceEntity == null) {
                    continue;
                }
                dataSourceResultList.add(new DataSourceResult(dataSourceEntity.getId(), dataSourceEntity.getDataSourceName()));
            }
        }
        return Result.ok(dataSourceResultList);
    }

    /**
     * 查询所有表
     *
     * @param queryTableEntity 入参-查询表条件对象
     * @return Result<PageResult < TableResultEntity>>
     */
    @PostMapping("/generator/list")
    public Result<?> queryTables(@RequestBody QueryTableEntity queryTableEntity) {
        DataSourceEntity dataSourceEntity = dataSourceService.selectOneById(queryTableEntity.getSourceId());

        Object o = new AbstractDynamicDataSourceService() {
            @Override
            protected Object execute() {
                return generatorService.queryAllTableByPage(queryTableEntity);
            }
        }.initDataSource(dataSourceEntity, 3, 1000);

//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(dataSourceEntity.getDriverClassName());
//        dataSource.setName(DynamicDataSourceGlobal.CUSTOM.name());
//        dataSource.setUrl(IpAddressUtil.getUrl(dataSourceEntity.getUrl(), dataSourceEntity.getDbName()));
//        dataSource.setUsername(dataSourceEntity.getUserName());
//        dataSource.setPassword(dataSourceEntity.getUserPassword());
//        dynamicDataSource.setCustomDataSource(dataSource);
//        dynamicDataSource.afterPropertiesSet();
//        DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.CUSTOM);
//        PageResult<TableResultEntity> pageResult = generatorService.queryAllTableByPage(queryTableEntity);
//        DynamicDataSourceHolder.clearDataSource();
        return Result.ok(o);
    }

    /**
     * 生成代码前的预览
     *
     * @param queryTemplateEntity 查询参数
     * @return Result<?>
     */
    @PostMapping("/template/gen/example-list.json")
    public Result<?> queryTemplateGenList(@RequestBody QueryTemplateEntity queryTemplateEntity) {
        Long groupId = queryTemplateEntity.getGroupId();
        TemplateGroupEntity templateGroupEntity = templateGroupService.selectOneById(groupId);
        List<TemplateEntity> templateEntityList = templateService.selectTemplateByGroupId(groupId);
        List<TemplateResultEntity> list = Lists.newArrayList();
        for (String tableName : queryTemplateEntity.getTableNameList()) {
            List<TemplateResultEntity> entityList = buildTemplateResult(tableName, templateGroupEntity, templateEntityList);
            if (CollectionUtils.isNotEmpty(entityList)) {
                list.addAll(entityList);
            }
        }
        return Result.ok(list);
    }

    private List<TemplateResultEntity> buildTemplateResult(String tableName, TemplateGroupEntity group, List<TemplateEntity> templateEntityList) {
        List<TemplateResultEntity> list = Lists.newArrayList();
        for (TemplateEntity template : templateEntityList) {
            TemplateResultEntity templateResultEntity = new TemplateResultEntity();
            templateResultEntity.setTableName(tableName);
            templateResultEntity.setGroupName(group.getTablePrefix());
            templateResultEntity.setTemplateName(template.getTemplateName());
            // mainPackage + "." + moduleName ==> ${moduleName}
            templateResultEntity.setPackagePath(PlaceholderUtils.processPackagePath(group.getMainPackage(), group.getModuleName(), template.getPackagePath()));
            // tableName replace tablePreFix ==> ${className}
            templateResultEntity.setFileName(PlaceholderUtils.processFileName(tableName, group.getTablePrefix(), template.getFileName()));
            list.add(templateResultEntity);
        }
        return list;
    }

    /**
     * 生成代码
     */
    @RequestMapping("/generator/code")
    public void genCodeGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sourceId = request.getParameter("sourceId");
        String groupId = request.getParameter("groupId");
        String tables = request.getParameter("tables");
        GenCodeParam param = new GenCodeParam();
        param.setSourceId(Long.valueOf(sourceId));
        param.setGroupId(Long.valueOf(groupId));
        String[] tabs = JSON.parseObject(tables, String[].class);
        param.setTables(Arrays.asList(tabs));
        byte[] data = generatorCodeService.genCode(param);
        long l = ThreadLocalRandom.current().nextLong(1_000_000_000L, 11_000_000_000L);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code_" + l + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

}
