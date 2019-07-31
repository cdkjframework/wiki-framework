package com.cdkj.framework.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.security.GeneralSecurityException;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.exceptions
 * @ClassName: GlobalException
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class GlobalException extends GeneralSecurityException {

    @Generated("异常信息")
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalException.class);
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
