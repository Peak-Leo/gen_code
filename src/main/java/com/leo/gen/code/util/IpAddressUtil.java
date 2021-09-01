package com.leo.gen.code.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IP地址工具类
 *
 * @author leo
 */
public class IpAddressUtil {

    public static String getIpFromUrl(String url) {
        // 1.判断是否为空
        if (url == null || "".equals(url.trim())) {
            return "";
        }

        // 2. 如果是以localhost,那么替换成127.0.0.1
        if (url.startsWith("http://" + "localhost")) {
            url = url.replace("http://" + "localhost", "http://" + "127.0.0.1");
        }

        String host = "";
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+(:\\d{0,5})?");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            host = matcher.group();
        }
        return host;
    }

    public static String getUrl(String url, String dbName) {
        String resultUrl;
        if (url.contains("//")) {
            int i = url.indexOf("//");
            String prefix = url.substring(0, i) + "//";
            resultUrl = prefix + getIpFromUrl(url) + "/" + dbName;
        } else {
            resultUrl = url;
        }
        return resultUrl;
    }
}
