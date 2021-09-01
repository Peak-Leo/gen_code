package com.leo.gen.code.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 占位符处理工具类
 *
 * @author leo
 */
@Slf4j
public class PlaceholderUtils {

    /**
     * Prefix for system property placeholders: "${"
     */
    public static final String PLACEHOLDER_PREFIX = "${";
    /**
     * Suffix for system property placeholders: "}"
     */
    public static final String PLACEHOLDER_SUFFIX = "}";

    public static String resolvePlaceholders(String text, Map<String, String> parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return text;
        }
        StringBuffer buf = new StringBuffer(text);
        int startIndex = buf.indexOf(PLACEHOLDER_PREFIX);
        while (startIndex != -1) {
            int endIndex = buf.indexOf(PLACEHOLDER_SUFFIX, startIndex + PLACEHOLDER_PREFIX.length());
            if (endIndex != -1) {
                String placeholder = buf.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                int nextIndex = endIndex + PLACEHOLDER_SUFFIX.length();
                try {
                    String propVal = parameter.get(placeholder);
                    if (propVal != null) {
                        buf.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                        nextIndex = startIndex + propVal.length();
                    } else {
                        log.warn("Could not resolve placeholder '" + placeholder + "' in [" + text + "] ");
                    }
                } catch (Exception ex) {
                    log.warn("Could not resolve placeholder '" + placeholder + "' in [" + text + "]: " + ex);
                }
                startIndex = buf.indexOf(PLACEHOLDER_PREFIX, nextIndex);
            } else {
                startIndex = -1;
            }
        }
        return buf.toString();
    }

    public static String processPackagePath(String mainPackage, String moduleName, String packagePath) {
        // mainPackage + "." + moduleName ==> ${moduleName}
        Map<String, String> map = Maps.newHashMap();
        map.put("moduleName", mainPackage + Constants.POINT + moduleName);
        return resolvePlaceholders(packagePath, map);
    }

    public static String processFileName(String tableName, String tablePrefix, String fileName) {
        // tableName replace tablePreFix ==> ${className}
        Map<String, String> map = Maps.newHashMap();
//        map.put("className", TableConvertBeanNameUtil.firstUpperCamelCase(tableName, tablePrefix));
        map.put("className", GenUtils.tableToJava(tableName,tablePrefix));
        return resolvePlaceholders(fileName, map);
    }
}
