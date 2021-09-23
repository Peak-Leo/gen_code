package com.leo.gen.code.config.db.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class DbProperty {

    @Value("${spring.datasource.default.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.default.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.default.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.default.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.default.filters}")
    private String filters;
    @Value("${spring.datasource.default.maxActive}")
    private int maxActive;
}
