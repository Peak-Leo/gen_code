package com.leo.gen.code.service.impl;

import com.leo.gen.code.dmds.AbstractDynamicDataSourceService;
import com.leo.gen.code.entity.DataSourceEntity;
import com.leo.gen.code.entity.GenCodeParam;
import com.leo.gen.code.entity.TemplateEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.service.*;
import com.leo.gen.code.util.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成核心实现类
 *
 * @author leo
 */
@Service("generatorCodeService")
public class GeneratorCodeServiceImpl implements GeneratorCodeService {

    private final TemplateService templateService;

    private final TemplateGroupService templateGroupService;

    private final GeneratorService generatorService;

    private final DataSourceService dataSourceService;

    public GeneratorCodeServiceImpl(TemplateService templateService, TemplateGroupService templateGroupService, GeneratorService generatorService, DataSourceService dataSourceService) {
        this.templateService = templateService;
        this.templateGroupService = templateGroupService;
        this.generatorService = generatorService;
        this.dataSourceService = dataSourceService;
    }

    /**
     * 代码生成
     *
     * @param genCodeParam 入参
     * @return byte[]
     */
    @Override
    public byte[] genCode(GenCodeParam genCodeParam) {

        TemplateGroupEntity group = templateGroupService.selectOneById(genCodeParam.getGroupId());
        List<TemplateEntity> templateList = templateService.selectTemplateByGroupId(genCodeParam.getGroupId());
        DataSourceEntity dataSourceEntity = dataSourceService.selectOneById(genCodeParam.getSourceId());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        try {
            new AbstractDynamicDataSourceService() {
                /**
                 * 执行具体业务
                 *
                 * @return Object
                 */
                @Override
                protected Object execute() {

                    for (String tableName : genCodeParam.getTables()) {
                        //查询表信息
                        Map<String, String> table = generatorService.queryTable(tableName);
                        //查询列信息
                        List<Map<String, String>> columns = generatorService.queryColumns(tableName);
                        //生成代码
                        GenUtils.generatorCode(group, templateList, table, columns, zip);
                    }
                    return null;
                }
            }.initDataSource(dataSourceEntity, 3, 5000);
        } finally {
            IOUtils.closeQuietly(zip);
        }

        return outputStream.toByteArray();
    }
}
