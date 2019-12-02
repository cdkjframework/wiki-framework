package com.cdkjframework.datasource.mybatis;

import com.cdkjframework.util.log.LogUtils;
import org.apache.ibatis.logging.Log;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.SPRING_DATASOURCE.relational.mybatis
 * @ClassName: LogbackImpl
 * @Description: myBatis 日志输出
 * @Author: xiaLin
 * @Version: 1.0
 */

public class LogbackImpl implements Log {

    /**
     * 日志
     */
    private LogUtils logUtils;

    /**
     * 构造函数
     *
     * @param clazz 类
     */
    public LogbackImpl(String clazz) {
        this.logUtils = LogUtils.getLogger(clazz);
    }

    /**
     * 是否为默认 Debug
     *
     * @return 返回结果
     */
    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    /**
     * 不打印结果集，要打印结果集需要把logback日志级别调为trace
     *
     * @return 返回结果
     */
    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    /**
     * 错误日志
     *
     * @param message   消息
     * @param throwable 异常信息
     */
    @Override
    public void error(String message, Throwable throwable) {
        logUtils.error(throwable, message);
    }

    /**
     * 错误日志信息
     *
     * @param message 消息
     */
    @Override
    public void error(String message) {
        logUtils.error(message);
    }

    /**
     * 调试
     *
     * @param message 消息
     */
    @Override
    public void debug(String message) {
        logUtils.debug(message);
    }

    @Override
    public void trace(String message) {
        logUtils.debug(message);
    }

    @Override
    public void warn(String message) {
        logUtils.warn(message);
    }
}
