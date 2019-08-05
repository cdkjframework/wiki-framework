package com.cdkjframework.util.log;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.Application;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class LogUtil {

    /**
     * 日志
     */
    private Logger logger;

    /**
     * 自定义配置
     */
    private CustomConfig customConfig;

    /**
     * getLogger
     *
     * @param clazz 类
     */
    public static LogUtil getLogger(Class clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * getLogger
     *
     * @param name 类
     */
    public static LogUtil getLogger(String name) {
        return new LogUtil(name);
    }

    /**
     * 构造函数
     */
    public LogUtil() {
    }

    /**
     * 构造函数
     *
     * @param name 输出名称
     */
    public LogUtil(String name) {
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
        Log(Level.SEVERE, msg);
    }

    /**
     * 调试输出日志
     *
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    public void debug(Throwable throwable, String msg) {
        if (throwable != null) {
            Log(Level.SEVERE, throwable, msg);
        } else {
            Log(Level.SEVERE, msg);
        }
    }

    /**
     * 信息输出日志
     *
     * @param msg 错误信息
     */
    public void info(String msg) {
        Log(Level.INFO, msg);
    }

    /**
     * 信息输出日志
     *
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    public void info(Throwable throwable, String msg) {
        if (throwable != null) {
            Log(Level.INFO, throwable, msg);
        } else {
            Log(Level.INFO, msg);
        }
    }

    /**
     * 错误输出日志
     *
     * @param msg 错误信息
     */
    public void error(String msg) {
        Log(Level.SEVERE, msg);
    }

    /**
     * 错误异常日志
     *
     * @param ex Exception
     */
    public void error(Exception ex) {
        Log(Level.SEVERE, ex.toString());
        Log(Level.SEVERE, ex.getMessage());
        StackTraceElement[] elements = ex.getStackTrace();
        for (StackTraceElement ele :
                elements) {
            Log(Level.SEVERE, ele.getClassName() + "." + ele.getMethodName() + "(" + ele.getFileName() + ":" + ele.getLineNumber() + ")");
        }
    }

    /**
     * 错误异常日志
     *
     * @param elements StackTraceElement
     */
    public void error(StackTraceElement[] elements) {
        for (StackTraceElement ele :
                elements) {
            Log(Level.SEVERE, ele.getClassName() + "." + ele.getMethodName() + "(" + ele.getFileName() + ":" + ele.getLineNumber() + ")");
        }
    }

    /**
     * 错误输出日志
     *
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    public void error(Throwable throwable, String msg) {
        Log(Level.FINEST, throwable, msg);
    }

    /**
     * 写入日志
     *
     * @param level     等级
     * @param throwable 错误信息
     * @param msg       错误信息
     */
    private void Log(Level level, Throwable throwable, String msg) {
        try {
            logger.log(level, msg, throwable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 写入日志
     *
     * @param level 等级
     * @param msg   错误信息
     */
    private void Log(Level level, String msg) {
        try {
            if (customConfig == null) {
                customConfig = new CustomConfig();
            }

            //验证目录存不存在
//            String logPath = customConfig.getLogPath();
//            if (HostUtil.getOs().startsWith("win")) {
//                logPath = "c:" + logPath;
//            }
//            File file = new File(logPath);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            Date date = new Date();
//            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
//            String logFileName = logPath + level.getName().toLowerCase() + sd.format(date) + ".log";
//            //设置日志输出信息
//            FileHandler fileHandler = new FileHandler(logFileName, 10000, 11, true);
//            fileHandler.setLevel(level);
//            fileHandler.setFormatter(new CdkjLogHander());
//            logger.addHandler(fileHandler);

            logger.log(level, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 写入日志
     *
     * @param msg 错误信息
     */
    private void Log(String msg) {
        try {
            logger.log(Level.SEVERE, msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置日志格式
     */
    class CdkjLogHander extends Formatter {
        @Override
        public String format(LogRecord record) {
            Date date = new Date();
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = sd.format(date);
            return "[" + d + "]" + "[" + record.getLevel() + "]" + record.getClass() + " :" + record.getMessage() + "\n";
        }
    }
}
