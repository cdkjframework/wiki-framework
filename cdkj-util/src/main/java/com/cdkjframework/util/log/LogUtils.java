package com.cdkjframework.util.log;

import ch.qos.logback.classic.Level;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static ch.qos.logback.classic.Level.*;

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
	 * 换行符号
	 */
	private final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * 目标值
	 */
	private final String TARGET = "{}";
	/**
	 * 替换值
	 */
	private final String REPLACE = "%s";

	/**
	 * 日志
	 */
	private Logger logger;

	/**
	 * 构造函数
	 *
	 * @param name 输出名称
	 */
	private LogUtils(String name) {
		logger = LoggerFactory.getLogger(name);
	}

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
	 * 调试输出日志
	 *
	 * @param format    格式
	 * @param arguments 内容
	 */
	public void debug(String format, Object... arguments) {
		debug(String.format(format.replace(TARGET, REPLACE), arguments));
	}

	/**
	 * 调试输出日志
	 *
	 * @param message 错误信息
	 */
	public void debug(String message) {
		debug(null, message);
	}

	/**
	 * 调试输出日志
	 *
	 * @param throwable 错误信息
	 * @param message   错误信息
	 */
	public void debug(Throwable throwable, String message) {
		if (throwable != null) {
			logThrowable(DEBUG_INT, throwable, message);
		} else {
			logThrowable(DEBUG_INT, message);
		}
	}

	/**
	 * 日志信息输出
	 *
	 * @param format    格式
	 * @param arguments 内容
	 */
	public void info(String format, Object... arguments) {
		info(String.format(format.replace(TARGET, REPLACE), arguments));
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
			logThrowable(INFO_INT, throwable, msg);
		} else {
			logThrowable(INFO_INT, msg);
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
			logThrowable(INFO_INT, throwable, msg);
		} else {
			logThrowable(INFO_INT, msg);
		}
	}

	/**
	 * 错误输出日志
	 *
	 * @param format    格式
	 * @param arguments 内容
	 */
	public void error(String format, Object... arguments) {
		error(String.format(format.replace(TARGET, REPLACE), arguments));
	}

	/**
	 * 错误输出日志
	 *
	 * @param msg 错误信息
	 */
	public void error(String msg) {
		error((Throwable) null, msg);
	}

	/**
	 * 错误输出日志
	 *
	 * @param throwable 错误信息
	 * @param msg       错误信息
	 */
	public void error(Throwable throwable, String msg) {
		if (throwable == null) {
			logThrowable(ERROR_INT, msg);
		} else {
			logThrowable(ERROR_INT, throwable, msg);
		}
	}

	/**
	 * 错误输出日志
	 *
	 * @param stackTraceElements 错误信息
	 * @param msg                错误信息
	 */
	public void error(StackTraceElement[] stackTraceElements, String msg) {
		if (stackTraceElements == null) {
			logThrowable(ERROR_INT, msg);
		} else {
			log(ERROR_INT, stackTraceElements, msg);
		}
	}

	/**
	 * 错误输出日志
	 *
	 * @param ex 错误信息
	 */
	public void error(Exception ex) {
		if (ex.getCause() == null) {
			log(ERROR_INT, ex.getStackTrace(), ex.getMessage());
		} else {
			error(ex.getCause(), ex.getMessage());
		}
	}

	/**
	 * 写入日志
	 *
	 * @param level             等级
	 * @param stackTraceElement 错误信息
	 * @param message           错误信息
	 */
	private void log(int level, StackTraceElement[] stackTraceElement, String message) {
		try {
			StringBuilder builder;
			if (stackTraceElement != null) {
				builder = new StringBuilder(message);
				builder.append(NEW_LINE);
				ch.qos.logback.classic.Level levels = ch.qos.logback.classic.Level.toLevel(level);
				buildExceptionLog(builder, levels, stackTraceElement);
			} else {
				builder = new StringBuilder(message);
			}
			switch (level) {
				case ERROR_INT:
					logger.error(builder.toString());
					break;
				case WARN_INT:
					logger.warn(builder.toString());
					break;
				case INFO_INT:
					logger.info(builder.toString());
					break;
				case DEBUG_INT:
					logger.debug(builder.toString());
					break;
				case TRACE_INT:
					logger.trace(builder.toString());
					break;
				default:
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 写入日志
	 *
	 * @param throwable 错误信息
	 * @param message   错误信息
	 */
	private void logThrowable(int level, Throwable throwable, String message) {
		try {
			switch (level) {
				case ERROR_INT:
					if (throwable == null) {
						logger.error(message);
					} else {
						logger.error(message, throwable);
					}
					break;
				case WARN_INT:
					if (throwable == null) {
						logger.warn(message);
					} else {
						logger.warn(message, throwable);
					}
					break;
				case INFO_INT:
					if (throwable == null) {
						logger.info(message);
					} else {
						logger.info(message, throwable);
					}
					break;
				case DEBUG_INT:
					if (throwable == null) {
						logger.debug(message);
					} else {
						logger.debug(message, throwable);
					}
					break;
				case TRACE_INT:
					if (throwable == null) {
						logger.trace(message);
					} else {
						logger.trace(message, throwable);
					}
					break;
				default:
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 写入日志
	 *
	 * @param level   等级
	 * @param message 错误信息
	 */
	private void logThrowable(int level, String message) {
		try {
			logThrowable(level, null, message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 异常写入文件
	 *
	 * @param builder  日志信息
	 * @param level    等级
	 * @param elements 异常信息
	 */
	private void buildExceptionLog(StringBuilder builder, Level level, StackTraceElement[] elements) throws GlobalException {
		for (StackTraceElement element :
				elements) {
			builder.append(LocalDateUtils.dateTimeCurrentFormatter(LocalDateUtils.DATE_HH_MM_SS_SSS));
			builder.append(String.format("    【%s】    ", level.toString()));
			builder.append(String.format("%s.%s(%s：%d)", element.getClassName(),
					element.getMethodName(), element.getFileName(), element.getLineNumber()));
			builder.append(NEW_LINE);
		}
	}
}
