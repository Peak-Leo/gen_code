package com.leo.gen.code.config.bean;

import com.leo.gen.code.util.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leo
 */
@Configuration
public class BeanConfiguration {

    /**
     * springbean工具类
     *
     * @return
     */
    @Bean("springUtil")
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }

}
