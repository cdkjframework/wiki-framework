package com.cdkjframework.exceptions;

import com.cdkjframework.constant.BusinessConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.exceptions
 * @ClassName: GlobalRuntimeException
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class GlobalRuntimeException extends RuntimeException {

    @Generated("异常信息")
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRuntimeException.class);
    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public GlobalRuntimeException(final String message) {
        super(message);
    }

    /**
     * 构造函数
     */
    public GlobalRuntimeException(Exception ex, final String message) {
        super(message, ex.getCause());
    }

    /**
     * 构造函数
     */
    public GlobalRuntimeException(Integer code, final String message) {
        super(code + BusinessConsts.ERROR_KEY + message);
    }

    /**
     * 构造函数
     */
    public GlobalRuntimeException(final String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     */
    @Generated("构造函数")
    public GlobalRuntimeException() {
        super();
    }
}
