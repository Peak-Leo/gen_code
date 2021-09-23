package com.leo.gen.code.controller;

import com.leo.gen.code.dmds.AbstractDynamicDataSourceService;
import com.leo.gen.code.entity.DataSourceEntity;
import com.leo.gen.code.entity.QueryDataSourceEntity;
import com.leo.gen.code.service.DataSourceService;
import com.leo.gen.code.service.GeneratorService;
import com.leo.gen.code.util.PageResult;
import com.leo.gen.code.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据源
 *
 * @author leo
 */
@RestController
@RequestMapping(("/datasource"))
public class DataSourceController {

    private final DataSourceService dataSourceService;

    private final GeneratorService generatorService;

    public DataSourceController(DataSourceService dataSourceService, GeneratorService generatorService) {
        this.dataSourceService = dataSourceService;
        this.generatorService = generatorService;
    }

    /**
     * 分页查询数据源信息
     *
     * @param queryDataSourceEntity 查询条件
     * @return Result<PageResult < DataSourceEntity>>
     */
    @PostMapping("/list")
    public Result<PageResult<DataSourceEntity>> queryDataSourceByPage(@RequestBody QueryDataSourceEntity queryDataSourceEntity) {
        PageResult<DataSourceEntity> pageResult = dataSourceService.queryDataSourceByPage(queryDataSourceEntity);
        return Result.ok(pageResult);
    }

    @PostMapping("/get")
    public Result<DataSourceEntity> queryDataSourceById(@RequestBody DataSourceEntity dataSourceEntity) {
        DataSourceEntity entity = dataSourceService.selectOneById(dataSourceEntity.getId());
        return Result.ok(entity);
    }

    @PostMapping("/insert")
    public Result<?> insertDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        Integer integer = dataSourceService.insertDataSource(dataSourceEntity);
        return Result.ok(integer);
    }

    @PostMapping("/delete")
    public Result<?> deleteDataSourceById(@RequestBody DataSourceEntity dataSourceEntity) {
        Integer integer = dataSourceService.deleteDataSourceById(dataSourceEntity.getId());
        return Result.ok(integer);
    }

    @PostMapping("/update")
    public Result<?> updateDataSourceById(@RequestBody DataSourceEntity dataSourceEntity) {
        Integer integer = dataSourceService.updateDataSourceById(dataSourceEntity);
        return Result.ok(integer);
    }

    @PostMapping("/connect/test")
    public Result<?> connectTest(@RequestBody DataSourceEntity dataSourceEntity) {
        Object o = null;
        try {
            o = new AbstractDynamicDataSourceService() {
                @Override
                protected Object execute() {
                    return generatorService.connectTest();
                }
            }.initDataSource(dataSourceEntity, 0, 100);
        } catch (Exception exception) {
            return Result.error("连接失败");
        }
        return o == null ? Result.error("连接失败") : Result.ok("连接成功:" + o.toString());
    }
}
