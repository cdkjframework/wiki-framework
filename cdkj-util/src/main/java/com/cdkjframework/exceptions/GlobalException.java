package com.cdkjframework.exceptions;

import com.cdkjframework.constant.BusinessConsts;
import com.cdkjframework.util.log.LogUtils;

import javax.annotation.Generated;
import java.security.GeneralSecurityException;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.exceptions
 * @ClassName: GlobalException
 * @Description: 公共异常处理类
 * @Author: xiaLin
 * @Version: 1.0
 */

public class GlobalException extends GeneralSecurityException {

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
    public GlobalException(Integer code, final String message) {
        super(code + BusinessConsts.errorKey + message);
    }

    /**
     * 构造函数
     */
    public GlobalException(final String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     */
    @Generated("构造函数")
    public GlobalException() {
        super();
    }
}
