package com.cdkjframework.util.fun;

import java.util.Objects;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.fun
 * @ClassName: Function
 * @Description: 函数接口
 * @Author: xiaLin
 * @Version: 1.0
 */
@FunctionalInterface
public interface Function<T, R> {

    /**
     * 申请
     *
     * @param t 类型
     * @return 返回指定类型
     */
    R apply(T t);

    /**
     * 组成
     *
     * @param before 之前
     * @param <V>    值类型
     * @return 一种组合函数，首先应用{@code before}函数，然后应用此函数
     */
    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * 或者
     *
     * @param after 开始值
     * @param <V>   值类型
     * @return 返回一个组合函数，该函数首先应用此函数，然后应用{@code after}函数
     */
    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }
}
