package com.leo.gen.code.service.impl;

import com.leo.gen.code.dwdb.AbstractDynamicDataSourceService;
import com.leo.gen.code.entity.DataSourceEntity;
import com.leo.gen.code.entity.GenCodeParam;
import com.leo.gen.code.entity.TemplateEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.service.*;
import com.leo.gen.code.util.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateGroupService templateGroupService;

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private DataSourceService dataSourceService;

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


//    public byte[] genCode(GenCodeParam genCodeParam) {
//
//        TemplateGroupEntity group = templateGroupService.selectOneById(genCodeParam.getGroupId());
//        List<TemplateEntity> templateList = templateService.selectTemplateByGroupId(genCodeParam.getGroupId());
//        DataSourceEntity dataSourceEntity = dataSourceService.selectOneById(genCodeParam.getSourceId());

//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(dataSourceEntity.getDriverClassName());
//        dataSource.setName(DynamicDataSourceGlobal.CUSTOM.name());
//        dataSource.setUrl(IpAddressUtil.getUrl(dataSourceEntity.getUrl(), dataSourceEntity.getDbName()));
//        dataSource.setUsername(dataSourceEntity.getUserName());
//        dataSource.setPassword(dataSourceEntity.getUserPassword());
//        // 失败后重连的次数
//        dataSource.setConnectionErrorRetryAttempts(3);
//        // 请求失败之后中断
//        dataSource.setBreakAfterAcquireFailure(true);
//        // 请求失败后等待时间
//        dataSource.setMaxWait(5000);
//        DynamicDataSource dynamicDataSource = (DynamicDataSource) SpringUtil.getBean("dynamicDataSource");
//        dynamicDataSource.setCustomDataSource(dataSource);
//        dynamicDataSource.afterPropertiesSet();
//        DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.CUSTOM);

//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ZipOutputStream zip = new ZipOutputStream(outputStream);
//
//        try {
//            for (String tableName : genCodeParam.getTables()) {
//                //查询表信息
//                Map<String, String> table = generatorService.queryTable(tableName);
//                //查询列信息
//                List<Map<String, String>> columns = generatorService.queryColumns(tableName);
//                //生成代码
//                GenUtils.generatorCode(group, templateList, table, columns, zip);
//            }
//        } finally {
//            DynamicDataSourceHolder.clearDataSource();
//            IOUtils.closeQuietly(zip);
//        }
//        return outputStream.toByteArray();
//    }
}
