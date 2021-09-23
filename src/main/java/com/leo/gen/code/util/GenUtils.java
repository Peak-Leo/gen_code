package com.leo.gen.code.util;

import com.google.common.collect.Maps;
import com.leo.gen.code.entity.ColumnEntity;
import com.leo.gen.code.entity.TableEntity;
import com.leo.gen.code.entity.TemplateEntity;
import com.leo.gen.code.entity.TemplateGroupEntity;
import com.leo.gen.code.exception.GenCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成工具类
 *
 * @author leo
 */
@Slf4j
public class GenUtils {

    /**
     * 生成代码
     */
    public static void generatorCode(TemplateGroupEntity group, List<TemplateEntity> templateList, Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip) {

        boolean hasBigDecimal = false;
        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), group.getTablePrefix());
        tableEntity.setClassName(className);
        tableEntity.setClassNameLow(StringUtils.uncapitalize(className));

        Configuration config = getConfig();

        //列信息
        List<ColumnEntity> columsList = new ArrayList<>();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrNameLow(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);
            if (!hasBigDecimal && attrType.equals("BigDecimal")) {
                hasBigDecimal = true;
            }
            //是否主键
            if ("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }

            columsList.add(columnEntity);
        }
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassNameLow());
        map.put("pathName", tableEntity.getClassNameLow().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("mainPath", group.getMainPackage());
        map.put("package", group.getMainPackage());
        map.put("moduleName", group.getModuleName());
        map.put("author", group.getAuthor());
        map.put("email", group.getAuthor());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        for (TemplateEntity templateEntity : templateList) {
            StringWriter w = new StringWriter();
            try {
                Velocity.evaluate(context, w, templateEntity.getId() + templateEntity.getTemplateName(), templateEntity.getContext());
            } catch (ParseErrorException pee) {
                log.error("ParseErrorException", pee);
            } catch (MethodInvocationException mee) {
                log.error("MethodInvocationException", mee);
            } catch (Exception e) {
                log.error("Exception", e);
            }

            try {
                //添加到zip
                String fileName = getFileName(group.getMainPackage(), group.getModuleName(), tableEntity.getClassName(), templateEntity.getPackagePath(), templateEntity.getFileName());
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(w.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(w);
                zip.closeEntry();
            } catch (IOException e) {
                throw new GenCodeException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }

    /**
     * 获取文件名称
     * @param mainPackage 主包名
     * @param moduleName 模块名称
     * @param className 类名
     * @param packagePathModel 包路径模板
     * @param fileNameModel 文件名称模板
     * @return String 解析后的文件名称
     */
    private static String getFileName(String mainPackage, String moduleName, String className, String packagePathModel, String fileNameModel) {
        // mainPackage + "." + moduleName ==> ${moduleName}.web   packagePath
        // ${className}Controller.java  fileName
        Map<String, String> mapModule = Maps.newHashMap();
        mapModule.put("moduleName", mainPackage + Constants.POINT + moduleName);
        String packagePath = PlaceholderUtils.resolvePlaceholders(packagePathModel, mapModule);
        packagePath = packagePath.replace(".", File.separator);
        Map<String, String> mapClass = Maps.newHashMap();
        mapClass.put("className", className);
        String fileName = PlaceholderUtils.resolvePlaceholders(fileNameModel, mapClass);
        if (fileNameModel.contains(".java") || fileNameModel.contains(".xml")) {
            return packagePath + File.separator + StringUtils.capitalize(fileName);
        }
        return packagePath + File.separator + StringUtils.uncapitalize(fileName);
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new GenCodeException("获取配置文件失败，", e);
        }
    }

}