package com.cdkjframework.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.builder
 * @ClassName: Builder
 * @Description: 通用的 Builder 模式构建器
 * @Author: xiaLin
 * @Date: 2024/4/23 9:12
 * @Version: 1.0
 */
public class Builder<T> {

  /**
   * 供应商
   * 实例化器
   */
  private final Supplier<T> instantiator;

  /**
   * 修饰符
   */
  private List<Consumer<T>> modifiers = new ArrayList<>();

  /**
   * 构建函数
   */
  public Builder(Supplier<T> instantiator) {
    this.instantiator = instantiator;
  }

  /**
   * 构建方法
   *
   * @param <T> 实体类型
   * @return 返回结果
   */
  public static <T> Builder<T> of(Supplier<T> instantiator) {
    return new Builder<>(instantiator);
  }

  /**
   * 构建数据
   *
   * @param consumer 自定义数据
   * @param s        参数 1
   * @param <S>      参数 1 类型
   * @return 返回结果
   */
  public <S> Builder<T> with(Consumer1<T, S> consumer, S s) {
    Consumer<T> c = instance -> consumer.accept(instance, s);
    modifiers.add(c);
    return this;
  }

  /**
   * 构建数据
   *
   * @param consumer 自定义数据
   * @param s        参数 1
   * @param r        参数 2
   * @param <S>      参数 1 类型
   * @param <R>      参数 1 类型
   * @return 返回结果
   */
  public <S, R> Builder<T> with(Consumer2<T, S, R> consumer, S s, R r) {
    Consumer<T> c = instance -> consumer.accept(instance, s, r);
    modifiers.add(c);
    return this;
  }

  /**
   * 构建数据
   *
   * @param consumer 自定义数据
   * @param s        参数 1
   * @param r        参数 2
   * @param x        参数 3
   * @param <S>      参数 1 类型
   * @param <R>      参数 1 类型
   * @param <X>      参数 1 类型
   * @return 返回结果
   */
  public <S, R, X> Builder<T> with(Consumer3<T, S, R, X> consumer, S s, R r, X x) {
    Consumer<T> c = instance -> consumer.accept(instance, s, r, x);
    modifiers.add(c);
    return this;
  }

  /**
   * 构建结果
   *
   * @return 返回构建结果
   */
  public T build() {
    T value = instantiator.get();
    modifiers.forEach(modifier -> modifier.accept(value));
    modifiers.clear();
    return value;
  }

  /**
   * 1 参数 Consumer
   */
  @FunctionalInterface
  public interface Consumer1<T, S> {
    void accept(T t, S s);
  }

  /**
   * 2 参数 Consumer
   */
  @FunctionalInterface
  public interface Consumer2<T, S, R> {
    void accept(T t, S s, R r);
  }

  /**
   * 3 参数 Consumer
   */
  @FunctionalInterface
  public interface Consumer3<T, S, R, X> {
    void accept(T t, S s, R r, X x);
  }
}
