package com.cdkjframework.util.log;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.Application;
import com.cdkjframework.util.date.DateUtils;
import com.cdkjframework.util.tool.HostUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
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
public class LogUtils {

    /**
     * 日志
     */
    private Logger logger;

    /**
     * 自定义配置
     */
    private CustomConfig customConfig;

    /**
     * 操作系统
     */
    private String OS = "win";

    /**
     * 编码
     */
    private String CHARSETNAME = "utf-8";

    /**
     * 追加
     */
    private boolean APPEND = true;

    /**
     * 日志级别
     */
    private List<String> level = Arrays.asList("DEBUG", "INFO", "WARN", "ERROR", "FATAL");

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

    /**
     * 构造函数
     *
     * @param name 输出名称
     */
    public LogUtils(String name) {
        if (Application.applicationContext != null) {
            customConfig = Application.applicationContext.getBean(CustomConfig.class);
        }
        logger = Logger.getLogger(name);
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
        if (!isDebug()) {
            return;
        }
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
        if (!isInfo()) {
            return;
        }
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
        if (!isWarn()) {
            return;
        }
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
        if (!isError()) {
            return;
        }
        if (throwable == null) {
            log(Level.FINEST, msg);
        } else {
            log(Level.FINEST, throwable, msg);
        }
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
    private void writeLog(Level level, Throwable throwable, String msg) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if (customConfig == null) {
                customConfig = new CustomConfig();
            }
            //验证目录存不存在
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

            String logFileName = logPath + "log-" + level.getName().toLowerCase() + "-" + DateUtils.format(new Date()) + ".log";

            //验证文件是否存在
            file = new File(logPath + logFileName);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    file = new File(logPath + logFileName);
                }
            } catch (IOException e) {
                System.out.println(e);
                return;
            }

            //日志时间
            StringBuilder builder = new StringBuilder(DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS_SSS));
            builder.append("   " + level.getName() + "   " + logger.getName() + " : " + msg);
            outputStream(file, builder);
            // 异常信息
            if (throwable != null) {
                StackTraceElement[] elements = throwable.getStackTrace();
                for (StackTraceElement ele :
                        elements) {
                    builder = new StringBuilder(DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS_SSS));
                    builder.append("   " + ele.getClassName() + "." + ele.getMethodName() + "(" + ele.getFileName() + ":" + ele.getLineNumber() + ")");

                    outputStream(file, builder);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 写入日志
     *
     * @param file    文件
     * @param builder 日志信息
     */
    private void outputStream(File file, StringBuilder builder) {
        Writer writer = null;
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, APPEND);
            writer = new OutputStreamWriter(outputStream, CHARSETNAME);
            writer.write(builder.toString());
            String newline = System.getProperty("line.separator");
            //写入换行  
            writer.write(newline);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * 是否 debug 模式
     *
     * @return 返回结果
     */
    private boolean isDebug() {
        int index = level.indexOf(customConfig.getLevel());
        return index == 0;
    }

    /**
     * 是否 INFO 模式
     *
     * @return 返回结果
     */
    private boolean isInfo() {
        int index = level.indexOf(customConfig.getLevel());
        return index <= 1;
    }

    /**
     * 是否 WARN 模式
     *
     * @return 返回结果
     */
    private boolean isWarn() {
        int index = level.indexOf(customConfig.getLevel());
        return index <= 2;
    }

    /**
     * 是否 WARN 模式
     *
     * @return 返回结果
     */
    private boolean isError() {
        int index = level.indexOf(customConfig.getLevel());
        return index <= 3;
    }

    /**
     * 是否 FATAL 模式
     *
     * @return 返回结果
     */
    private boolean isFatal() {
        int index = level.indexOf(customConfig.getLevel());
        return index <= 4;
    }
}
