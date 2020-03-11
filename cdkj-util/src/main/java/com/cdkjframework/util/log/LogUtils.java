package com.cdkjframework.util.log;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.util.date.DateUtils;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.tool.HostUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util
 * @ClassName: LogUtil
 * @Description: 日志操作
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class LogUtils implements BeanPostProcessor {

    /**
     * 日志
     */
    private Logger logger;

    /**
     * 自定义配置
     */
    @Autowired
    private CustomConfig config;
    private static CustomConfig customConfig;

    /**
     * 操作系统
     */
    private final String OS = "win";

    /**
     * 日志级别
     */
    private final List<String> LEVEL = Arrays.asList("ERROR", "WARN", "INFO", "DEBUG", "TRACE");

    /**
     * getLogger
     *
     * @param clazz 类
     */
    public static LogUtils getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * getLogger
     *
     * @param name 类
     */
    public static LogUtils getLogger(String name) {
        return new LogUtils(name);
    }

    /**
     * 构造函数
     */
    public LogUtils() {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        customConfig = config;
        return bean;

    }

    /**
     * 构造函数
     *
     * @param name 输出名称
     */
    public LogUtils(String name) {
        logger = Logger.getLogger(name);
        setLoggerLevel(logger);
    }

    /**
     * 调试输出日志
     *
     * @param msg 错误信息
     */
    public void debug(String msg) {
        debug(null, msg);
    }

    /**
     * 调试输出日志
     *
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    public void debug(Throwable throwable, String msg) {
        if (throwable != null) {
            log(Level.CONFIG, throwable, msg);
        } else {
            log(Level.CONFIG, msg);
        }
    }

    /**
     * 信息输出日志
     *
     * @param msg 错误信息
     */
    public void info(String msg) {
        info(null, msg);
    }

    /**
     * 信息输出日志
     *
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    public void info(Throwable throwable, String msg) {
        if (throwable != null) {
            log(Level.INFO, throwable, msg);
        } else {
            log(Level.INFO, msg);
        }
    }

    /**
     * 警告日志
     *
     * @param msg 日志信息
     */
    public void warn(String msg) {
        warn(null, msg);
    }

    /**
     * 警告日志
     *
     * @param throwable 错误信息
     * @param msg       日志信息
     */
    public void warn(Throwable throwable, String msg) {
        if (throwable != null) {
            log(Level.INFO, throwable, msg);
        } else {
            log(Level.INFO, msg);
        }
    }

    /**
     * 错误输出日志
     *
     * @param msg 错误信息
     */
    public void error(String msg) {
        error(null, msg);
    }

    /**
     * 错误输出日志
     *
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    public void error(Throwable throwable, String msg) {
        if (throwable == null) {
            log(Level.SEVERE, msg);
        } else {
            log(Level.SEVERE, throwable, msg);
        }
    }

    /**
     * 错误输出日志
     *
     * @param ex 错误信息
     */
    public void error(Exception ex) {
        log(Level.SEVERE, ex.getCause(), ex.getMessage());
    }

    /**
     * 写入日志
     *
     * @param level     等级
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    private void log(Level level, Throwable throwable, String msg) {
        try {
            if (throwable == null) {
                logger.log(level, msg);
            } else {
                logger.log(level, msg, throwable);
            }

            //写入日志
            writeLog(level, throwable, msg);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * 写入日志
     *
     * @param level 等级
     * @param msg   错误信息
     */
    private void log(Level level, String msg) {
        try {
            log(level, null, msg);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * 写入日志至文件系统
     *
     * @param level
     * @param throwable 错误信息
     * @param msg
     */
    private synchronized void writeLog(Level level, Throwable throwable, String msg) {
        try {
            // 验证目录存不存在
            String logPath = customConfig.getLogPath();
            if (HostUtils.getOs().startsWith(OS)) {
                logPath = "c:" + logPath;
            }
            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file.exists()) {
                return;
            }
            // 日志文件
            String logFileName = "log-" + level.getName().toLowerCase() +
                    "-" + DateUtils.format(new Date()) + ".log";

            // 验证文件是否存在
            file = new File(logPath + logFileName);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                System.out.println(e);
                return;
            }

            // 日志时间
            StringBuilder builder = new StringBuilder(DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS_SSS));
            builder.append(String.format("    【%s】    ", level.getName()));
            builder.append(String.format("%s:%s ", logger.getName(), msg));
            FileUtils.saveFile(builder.toString(), logPath, "", logFileName);
            //  异常信息
            if (throwable != null) {
                StackTraceElement[] elements = throwable.getStackTrace();
                for (StackTraceElement ele :
                        elements) {
                    builder = new StringBuilder(DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS_SSS));
                    builder.append(String.format("    【%s】    ", level.getName()));
                    builder.append(String.format("%s.%s(%s:%d)", ele.getClassName(),
                            ele.getMethodName(), ele.getFileName(), ele.getLineNumber()));

                    // 保存文件
                    FileUtils.saveFile(builder.toString(), logPath, "", logFileName);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * 是否 debug 模式
     *
     * @return 返回结果
     */
    private void setLoggerLevel(Logger loggerLevel) {
        if (customConfig == null) {
            customConfig = new CustomConfig();
        }
        int index = LEVEL.indexOf(customConfig.getLevel());
        switch (index) {
            case 0:
                loggerLevel.setLevel(Level.SEVERE);
                break;
            case 1:
                loggerLevel.setLevel(Level.WARNING);
                break;
            case 2:
                loggerLevel.setLevel(Level.INFO);
                break;
            case 3:
                loggerLevel.setLevel(Level.CONFIG);
                break;
            default:
                loggerLevel.setLevel(Level.ALL);
                break;
        }
    }


}
