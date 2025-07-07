package com.cdkjframework.mybatispro.core.toolkit.support;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit.support
 * @ClassName: SFunction
 * @Description: 支持序列化的 Function
 * @Author: xiaLin
 * @Date: 2025/2/7 10:55
 * @Version: 1.0
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
