package com.cdkjframework.exceptions;

import com.cdkjframework.util.log.LogUtils;

import javax.annotation.Generated;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.exceptions
 * @ClassName: GlobalException
 * @Description: 公共异常处理类
 * @Author: xiaLin
 * @Version: 1.0
 */

public class GlobalException extends RuntimeException {

    @Generated("异常信息")
    private static final LogUtils LOGGER = LogUtils.getLogger(GlobalException.class);
    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public GlobalException(final String message) {
        super(message);
    }

    /**
     * 构造函数
     */
    @Generated("构造函数")
    public GlobalException() {
        super();
    }
}
