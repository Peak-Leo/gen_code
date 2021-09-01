package com.leo.gen.code.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring bean获取工具
 *
 * @author leo
 */
public class SpringUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    public static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext Spring应用上下文环境
     * @throws BeansException BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setGlobalApplicationContext(applicationContext);
    }

    /**
     * 设置上下文
     *
     * @param applicationContext Spring应用上下文环境
     * @throws BeansException BeansException
     */
    public static void setGlobalApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name beanName
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * 如果有多个Bean符合Class, 取出第一个.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 获取类型为requiredType的对象
     * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
     *
     * @param name         bean注册名
     * @param requiredType 返回对象类型
     * @return Object 返回requiredType类型对象
     * @throws BeansException BeansException
     */
    public static Object getBean(String name, Class requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name beanName
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name beanName
     * @return boolean
     * @throws NoSuchBeanDefinitionException exception
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    /**
     * @param name beanName
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException exception
     */
    public static Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name beanName
     * @return String[]
     * @throws NoSuchBeanDefinitionException exception
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }
}