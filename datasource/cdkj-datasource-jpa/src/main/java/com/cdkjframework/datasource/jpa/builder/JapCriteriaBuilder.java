package com.cdkjframework.datasource.jpa.builder;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.tool.CollectUtils;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


/**
 * @ProjectName: common
 * @Package: com.cdkjframework.datasource.jpa.builder
 * @ClassName: JapCriteriaBuilder
 * @Description: JPA 查询条件构建器
 * @Author: xiaLin
 * @Date: 2025/4/12 23:30
 * @Version: 1.0
 */
public class JapCriteriaBuilder<T> {

  /**
   * 分页页码
   */
  private Integer pageNumber;

  /**
   * 分页大小
   */
  private Integer pageSize;

  /**
   * 构造器
   */
  private final List<Predicate> predicates = new ArrayList<>();

  /**
   * 存储原始排序配置（类型安全）
   */
  private final List<Sort.Order> rawSortList = new ArrayList<>();

  /**
   * 存储原始排序配置
   */
  private final List<Order> orders = new ArrayList<>();

  /**
   * 构造器
   */
  private final Root<T> root;

  /**
   * 构造器
   */
  private final CriteriaBuilder cb;

  /**
   * 构造器
   */
  private final CriteriaQuery<?> query;

  /**
   * 构造器
   */
  private JapCriteriaBuilder(Root<T> root, CriteriaBuilder cb, CriteriaQuery<?> query) {
    this.root = root;
    this.cb = cb;
    this.query = query;
  }

  /**
   * 创建构建器实例
   */
  public static <T> JapCriteriaBuilder<T> of(Root<T> root,
                                             CriteriaBuilder cb,
                                             CriteriaQuery<?> query) {
    return new JapCriteriaBuilder<>(root, cb, query);
  }

  /**
   * 等于条件
   */
  public <V> JapCriteriaBuilder<T> equal(String field, V value) {
    if (isValidValue(value)) {
      predicates.add(cb.equal(root.get(field), value));
    }
    return this;
  }

  /**
   * 等于条件
   */
  public <V> JapCriteriaBuilder<T> equal(Path<V> fieldPath, V value) {
    if (isValidValue(value)) {
      predicates.add(cb.equal(fieldPath, value));
    }
    return this;
  }

  /**
   * 等于条件
   */
  public <V> JapCriteriaBuilder<T> equal(SingularAttribute<T, V> attr, V value) {
    if (isValidValue(value)) {
      predicates.add(cb.equal(root.get(attr), value));
    }
    return this;
  }

  /**
   * Like条件
   */
  public JapCriteriaBuilder<T> like(String field, String value) {
    if (isValidValue(value)) {
      predicates.add(cb.like(root.get(field), "%" + value + "%"));
    }
    return this;
  }

  /**
   * In条件
   */
  public <V> JapCriteriaBuilder<T> in(String field, Collection<V> values) {
    if (!CollectUtils.isEmpty(values)) {
      predicates.add(root.get(field).in(values));
    }
    return this;
  }

  /**
   * 大于等于条件
   */
  public <V extends Comparable<? super V>> JapCriteriaBuilder<T> greaterThanOrEqualTo(String field, String value) {
    if (isValidValue(value)) {
      predicates.add(cb.greaterThanOrEqualTo(root.get(field), value));
    }
    return this;
  }

  /**
   * 关联实体查询
   */
  public <X> JapCriteriaBuilder<T> joinEqual(String joinField, String field, Object value, JoinType joinType) {
    if (isValidValue(value)) {
      Join<T, X> join = root.join(joinField, joinType);
      predicates.add(cb.equal(join.get(field), value));
    }
    return this;
  }

  /**
   * 自定义条件
   */
  public JapCriteriaBuilder<T> add(Function<Root<T>, Predicate> condition) {
    Predicate predicate = condition.apply(root);
    if (predicate != null) {
      predicates.add(predicate);
    }
    return this;
  }

  /**
   * 逻辑或
   */
  public JapCriteriaBuilder<T> or(JapCriteriaBuilder<T> otherBuilder) {
    Predicate otherPredicate = otherBuilder.build().toPredicate(root, query, cb);
    predicates.add(cb.or(cb.and(predicates.toArray(new Predicate[0])), otherPredicate));
    return this;
  }


  /**
   * 设置分页信息
   *
   * @param page 页码
   * @return this
   */
  public JapCriteriaBuilder<T> page(int page) {
    this.pageNumber = page;
    return this;
  }

  /**
   * 设置分页大小
   *
   * @param size 页大小
   * @return this
   */
  public JapCriteriaBuilder<T> size(int size) {
    this.pageSize = size;
    return this;
  }

  /**
   * 添加排序方法
   *
   * @param field     字段名
   * @param direction 排序方向
   * @return JapCriteriaBuilder
   */
  public JapCriteriaBuilder<T> orderBy(String field, Sort.Direction direction) {
    validateFieldExists(field);
    if (direction != null) {
      Path<?> path = root.get(field);
      orders.add(direction.isAscending() ?
          cb.asc(path) :
          cb.desc(path));

      // 存储原始排序配置
      rawSortList.add(Sort.Order.by(field).with(direction));
    }
    return this;
  }

  /**
   * 添加排序方法 支持关联实体排序
   *
   * @param joinField 关联字段
   * @param sortField 排序字段
   * @param direction 排序方向
   * @return JapCriteriaBuilder
   */
  public JapCriteriaBuilder<T> orderByJoin(String joinField, String sortField, Sort.Direction direction) {
    validateFieldExists(joinField);
    validateFieldExists(sortField);
    Join<T, ?> join = root.join(joinField, JoinType.LEFT);
    Path<Object> path = join.get(sortField);
    orders.add(direction.isAscending() ?
        cb.asc(path) :
        cb.desc(path));

    // 存储原始排序配置
    rawSortList.add(Sort.Order.by(sortField).with(direction));

    return this;
  }

  /**
   * 生成 Pageable 对象
   *
   * @return Pageable
   */
  public Pageable toPageable() {
    if (CollectUtils.isEmpty(rawSortList)) {
      throw new GlobalRuntimeException("分页查询必须指定排序条件");
    }
    return PageRequest.of(
        Objects.requireNonNullElse(pageNumber, IntegerConsts.ZERO),
        Objects.requireNonNullElse(pageSize, IntegerConsts.FIFTEEN),
        Sort.by(rawSortList)
    );
  }

  /**
   * 构建 Specification
   */
  public Specification<T> build() {
    return (root, query, cb) -> {
      // 应用排序条件
      if (!orders.isEmpty()) {
        query.orderBy(orders);
      }
      // 查询条件
      Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
      return predicateArray.length == 0 ? cb.conjunction() : cb.and(predicateArray);
    };
  }

  /**
   * 值有效性校验
   */
  private <V> boolean isValidValue(V value) {
    if (value instanceof String) {
      return Objects.nonNull(value) && !((String) value).trim().isEmpty();
    }
    return Objects.nonNull(value);
  }

  /**
   * 字段是否存在校验 (内部方法)
   *
   * @param field 字段名
   */
  private void validateFieldExists(String field) {
    try {
      root.get(field);
    } catch (IllegalArgumentException e) {
      throw new GlobalRuntimeException("无效的字段: " + field, e);
    }
  }

  /**
   * 值有效性校验
   *
   * @param predicate 断言
   * @param value     值
   * @param <V>       值类型
   * @return this
   */
  private <V> JapCriteriaBuilder<T> addPredicateIfValid(Predicate predicate, V value) {
    if (isValidValue(value)) {
      predicates.add(predicate);
    }
    return this;
  }
}