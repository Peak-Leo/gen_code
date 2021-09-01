package com.leo.gen.code.util;

/**
 * 常量
 *
 * @author leo
 */
public class Constants {

    public static String POINT = ".";

    /**
     * 数据状态
     * 0-禁用,1-启用,2-已删除
     */
    public static class DataStatus {

        public static final int CLOSE = 0;

        public static final int OPEN = 1;

        public static final int DELETED = 2;

        public static String getName(int dataStatus) {
            switch (dataStatus) {
                case CLOSE:
                    return "禁用";
                case OPEN:
                    return "启用";
                case DELETED:
                    return "已删除";
                default:
                    return "";
            }
        }
    }

    /**
     * 模板类型
     * 1:java,2:xml,3:html,4:javascript
     */
    public static class TemplateType {

        public static final int JAVA = 1;

        public static final int XML = 2;

        public static final int HTML = 3;

        public static final int JS = 4;

        public static String getName(int templateType) {
            switch (templateType) {
                case JAVA:
                    return "java";
                case XML:
                    return "xml";
                case HTML:
                    return "html";
                case JS:
                    return "javascript";
                default:
                    return "other";
            }
        }
    }

}
