package com.leo.gen.code.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.leo.gen.code.dwdb.DynamicDataSource;
import com.leo.gen.code.dwdb.DynamicDataSourceGlobal;
import com.p6spy.engine.spy.P6DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 默认数据源配置bean
 *
 * @author leo
 */
@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.default.url}")
    private String defaultDBUrl;
    @Value("${spring.datasource.default.username}")
    private String defaultDBUser;
    @Value("${spring.datasource.default.password}")
    private String defaultDBPassword;
    @Value("${spring.datasource.default.driverClassName}")
    private String defaultDBDriverName;
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

    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DruidDataSource defaultDataSource = new DruidDataSource();
        defaultDataSource.setName(DynamicDataSourceGlobal.DEFAULT.name());
        defaultDataSource.setUrl(defaultDBUrl);
        defaultDataSource.setUsername(defaultDBUser);
        defaultDataSource.setPassword(defaultDBPassword);
        defaultDataSource.setDriverClassName(defaultDBDriverName);
        defaultDataSource.setValidationQuery(validationQuery);
        defaultDataSource.setMaxActive(maxActive);
        defaultDataSource.setTestOnBorrow(testOnBorrow);
        defaultDataSource.setTestOnReturn(testOnReturn);
        defaultDataSource.setTestWhileIdle(testWhileIdle);
        // 失败后重连的次数
        defaultDataSource.setConnectionErrorRetryAttempts(3);
        // 请求失败之后中断
        defaultDataSource.setBreakAfterAcquireFailure(true);
        // 请求失败后等待时间
        defaultDataSource.setMaxWait(5000);
//        defaultDataSource.setFilters(filters);
        dynamicDataSource.setDefaultDataSource(defaultDataSource);
        return dynamicDataSource;
    }

    @Bean("publicDataSource")
    public P6DataSource publicDataSource(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        return new P6DataSource(dynamicDataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:mapper/*.xml"));
                .getResources("classpath*:mapper/**/*.xml"));
        return bean.getObject();

    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}