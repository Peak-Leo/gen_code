package com.leo.gen.code.util;

import org.springframework.cglib.beans.BeanCopier;

/**
 * bean工具类
 * @author leo
 */
public class BeanUtil {

    /**
     * 拷贝对象
     *
     * @param src 源对象
     * @param dist 需要赋值的对象
     */
    public static void copy(Object src, Object dist) {
        BeanCopier copier = BeanCopier.create(src.getClass(), dist.getClass(), false);
        copier.copy(src, dist, null);
    }
}
