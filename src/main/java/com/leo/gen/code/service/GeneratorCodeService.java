package com.leo.gen.code.service;

import com.leo.gen.code.entity.GenCodeParam;

/**
 * 代码生成服务接口
 * @author leo
 */
public interface GeneratorCodeService {

    /**
     * 代码生成
     * @param genCodeParam 入参
     * @return byte[]
     */
    byte[] genCode(GenCodeParam genCodeParam);
}
