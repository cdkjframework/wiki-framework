package com.cdkjframework.core.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.core.processor
 * @ClassName: AbstractCdkjProcessor
 * @Description: 处理器
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractCdkjProcessor implements BeanPostProcessor, PriorityOrdered {

    /**
     * 初始化前的后处理
     *
     * @param bean     bean
     * @param beanName bean名称
     * @return 返回结果
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        Class clazz = bean.getClass();
        for (Field field : findAllField(clazz)) {
            processField(bean, beanName, field);
        }
        for (Method method : findAllMethod(clazz)) {
            processMethod(bean, beanName, method);
        }
        return bean;
    }

    /**
     * 初始化后的后处理
     *
     * @param bean     bean
     * @param beanName bean名称
     * @return 返回结果
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 子类应该实现这个方法来处理字段
     *
     * @param bean     bean
     * @param beanName bean名称
     * @param field    字段
     */
    protected abstract void processField(Object bean, String beanName, Field field);

    /**
     * 子类应该实现这个方法来处理方法
     *
     * @param bean     bean
     * @param beanName bean名称
     * @param method   方法
     */
    protected abstract void processMethod(Object bean, String beanName, Method method);

    /**
     * 获取索引
     *
     * @return 返回整数
     */
    @Override
    public int getOrder() {
        //make it as late as possible
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 查找所有字段
     *
     * @param clazz 类型
     * @return 返回字段列表
     */
    private List<Field> findAllField(Class clazz) {
        final List<Field> res = new LinkedList<>();
        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                res.add(field);
            }
        });
        return res;
    }

    /**
     * 查询所有方法
     *
     * @param clazz 类型
     * @return 返回字段列表
     */
    private List<Method> findAllMethod(Class clazz) {
        final List<Method> res = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                res.add(method);
            }
        });
        return res;
    }
}
