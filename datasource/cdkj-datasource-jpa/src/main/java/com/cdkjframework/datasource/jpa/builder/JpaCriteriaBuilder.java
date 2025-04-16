package com.cdkjframework.datasource.jpa.builder;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.CollectUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @ProjectName: common
 * @Package: com.cdkjframework.datasource.jpa.builder
 * @ClassName: JapCriteriaBuilder
 * @Description: JPA 查询条件构建器
 * @Author: xiaLin
 * @Date: 2025/4/12 23:30
 * @Version: 1.0
 */
@Component
public class JpaCriteriaBuilder<T> {

  /**
   * 日志
   */
  private final static LogUtils log = LogUtils.getLogger(JpaCriteriaBuilder.class);

  /**
   * 页码
   */
  private Integer pageNumber;

  /**
   * 每页大小
   */
  private Integer pageSize;


  /**
   * 排序列表
   */
  private final List<Sort.Order> rawSortList;

  /**
   * 查询条件
   */
  private Specification<T> specification;

  /**
   * 构造函数
   */
  private JpaCriteriaBuilder() {
    rawSortList = new ArrayList<>();
    specification = Specification.where(null);
  }

  /**
   * 创建JapCriteriaBuilder实例
   *
   * @return JapCriteriaBuilder
   */
  public static <T> JpaCriteriaBuilder<T> Builder() {
    return new JpaCriteriaBuilder<>();
  }

  /**
   * 自动构建规范
   *
   * @param builder JapCriteriaBuilder
   * @param entity  实体
   * @param <V>     实体类型
   * @return Specification
   */
  public <V> JpaCriteriaBuilder<T> autoBuilder(JpaCriteriaBuilder builder, @NotNull V entity) {
    if (Objects.isNull(entity)) {
      throw new GlobalRuntimeException("实体不能为空");
    }
    String pageSize = "pageSize", pageIndex = "pageIndex";
    Field[] fields = entity.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(Boolean.TRUE);
      try {
        Object value = field.get(entity);
        if (StringUtils.isNullAndSpaceOrEmpty(value)) {
          if (pageIndex.equals(field.getName())) {
            builder = builder.page(ConvertUtils.convertInt(value));
          } else if (pageSize.equals(field.getName())) {
            builder = builder.size(ConvertUtils.convertInt(value));
          } else {
            builder = builder.equal(field.getName(), value);
          }
        }
      } catch (IllegalAccessException e) {
        log.error(e);
      }
    }
    // 返回结果
    return builder;
  }

  /**
   * 自动构建规范
   *
   * @param builder      JapCriteriaBuilder
   * @param entity       实体
   * @param ignoreFields 对应字段
   * @param <V>          实体类型
   * @return Specification
   */
  public <V> JpaCriteriaBuilder<T> autoBuilder(JpaCriteriaBuilder builder, @NotNull V entity, List<String> ignoreFields) {
    var groupFields = new List[1];
    {
      CollectUtils.toList();
    }
    return autoBuilder(builder, entity, ignoreFields, groupFields);
  }

  /**
   * 自动构建规范
   *
   * @param builder     JapCriteriaBuilder
   * @param entity      实体
   * @param groupFields 分组字段
   * @param <V>         实体类型
   * @return Specification
   */
  public <V> JpaCriteriaBuilder<T> autoBuilder(JpaCriteriaBuilder builder, @NotNull V entity, List<String>... groupFields) {
    return autoBuilder(builder, entity, CollectUtils.toList(), groupFields);
  }

  /**
   * 自动构建规范
   *
   * @param builder      JapCriteriaBuilder
   * @param entity       实体
   * @param ignoreFields 对应字段
   * @param groupFields  分组字段
   * @param <V>          实体类型
   * @return Specification
   */
  public <V> JpaCriteriaBuilder<T> autoBuilder(JpaCriteriaBuilder builder, @NotNull V entity, List<String> ignoreFields, List<String>... groupFields) {
    if (Objects.isNull(entity)) {
      throw new GlobalRuntimeException("实体不能为空");
    }
    String pageSize = "pageSize", pageIndex = "pageIndex";
    Field[] fields = entity.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(Boolean.TRUE);
      try {
        Object value = field.get(entity);
        if (StringUtils.isNullAndSpaceOrEmpty(value)) {
          if (pageIndex.equals(field.getName())) {
            builder = builder.page(ConvertUtils.convertInt(value));
          } else if (pageSize.equals(field.getName())) {
            builder = builder.size(ConvertUtils.convertInt(value));
          } else {
            builder = builder.equal(field.getName(), value);
          }
        }
      } catch (IllegalAccessException e) {
        log.error(e);
      }
    }
    // 返回结果
    return builder;
  }


  /**
   * 添加等值查询条件
   *
   * @param field 字段
   * @param value 值
   * @param <V>   泛型
   * @return JapCriteriaBuilder
   */
  public <V> JpaCriteriaBuilder<T> equal(@NotNull String field, @NotNull V value) {
    if (isValidValue(value)) {
      specification = specification.and((root, query, criteriaBuilder) ->
          criteriaBuilder.equal(root.get(field), value)
      );
    }
    return this;
  }

  /**
   * 添加模糊查询条件
   *
   * @param field 字段
   * @param value 值
   * @param <V>   泛型
   * @return JapCriteriaBuilder
   */
  public <V> JpaCriteriaBuilder<T> like(@NotNull String field, @NotNull V value) {
    if (isValidValue(value)) {
      specification = specification.and((root, query, criteriaBuilder) -> {
        String pattern = StringUtils.PERCENT + value + StringUtils.PERCENT;
        return criteriaBuilder.like(root.get(field), pattern);
      });
    }
    return this;
  }

  /**
   * 添加in查询条件
   *
   * @param field  字段
   * @param values 值
   * @param <V>    泛型
   * @return JapCriteriaBuilder
   */
  public <V> JpaCriteriaBuilder<T> in(@NotNull String field, @NotNull List<V> values) {
    if (CollectUtils.isNotEmpty(values)) {
      specification = specification.and((root, query, criteriaBuilder) ->
          root.get(field).in(values)
      );
    }
    return this;
  }

  /**
   * 添加大于查询条件
   *
   * @param field 字段
   * @param value 值
   * @param <V>   泛型
   * @return JapCriteriaBuilder
   */
  public <V extends Comparable<? super V>> JpaCriteriaBuilder<T> greaterThan(@NotNull String field, @NotNull V value) {
    if (isValidValue(value)) {
      specification = specification.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThan(root.get(field), value)
      );
    }
    return this;
  }

  /**
   * 添加大于等于查询条件
   *
   * @param field 字段
   * @param value 值
   * @param <V>   泛型
   * @return JapCriteriaBuilder
   */
  public <V extends Comparable<? super V>> JpaCriteriaBuilder<T> greaterThanOrEqualTo(@NotNull String field, @NotNull V value) {
    if (isValidValue(value)) {
      specification = specification.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThanOrEqualTo(root.get(field), value)
      );
    }
    return this;
  }

  /**
   * 添加小于查询条件
   *
   * @param field 字段
   * @param value 值
   * @param <V>   泛型
   * @return JapCriteriaBuilder
   */
  public <V extends Comparable<? super V>> JpaCriteriaBuilder<T> lessThan(@NotNull String field, @NotNull V value) {
    if (isValidValue(value)) {
      specification = specification.and((root, query, criteriaBuilder) ->
          criteriaBuilder.lessThan(root.get(field), value)
      );
    }
    return this;
  }

  /**
   * 添加区间查询条件
   *
   * @param field 字段
   * @param from  区间起始值
   * @param to    区间结束值
   * @param <V>   泛型
   * @return JapCriteriaBuilder
   */
  public <V extends Comparable<? super V>> JpaCriteriaBuilder<T> between(@NotNull String field, @NotNull V from, V to) {
    if (from != null && to != null) {
      specification = specification.and((root, query, criteriaBuilder) ->
          criteriaBuilder.between(root.get(field), from, to)
      );
    }
    return this;
  }

  /**
   * 添加空查询条件
   *
   * @param field 字段
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> isNull(String field) {
    specification = specification.and((root, query, criteriaBuilder) ->
        criteriaBuilder.isNull(root.get(field))
    );
    return this;
  }

  /**
   * 添加非空查询条件
   *
   * @param field 字段
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> isNotNull(String field) {
    specification = specification.and((root, query, criteriaBuilder) ->
        criteriaBuilder.isNotNull(root.get(field))
    );
    return this;
  }

  /**
   * 添加关联查询条件
   *
   * @param joinField 关联字段
   * @param field     字段
   * @param value     值
   * @param joinType  关联类型
   * @param <X>       关联实体类型
   * @return JapCriteriaBuilder
   */
  public <X> JpaCriteriaBuilder<T> joinEqual(String joinField, @NotNull String field, @NotNull Object value, JoinType joinType) {
    if (isValidValue(value)) {
      specification = specification.and((root, query, criteriaBuilder) -> {
        Join<T, X> join = root.join(joinField, joinType);
        return criteriaBuilder.equal(join.get(field), value);
      });
    }
    return this;
  }

  /**
   * 添加自定义查询条件
   *
   * @param condition 自定义查询条件
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> add(Function<Root<T>, Predicate> condition) {
    specification = specification.and((root, query, criteriaBuilder) -> {
      Predicate predicate = condition.apply(root);
      return criteriaBuilder.and(predicate);
    });
    return this;
  }

  /**
   * 或查询
   *
   * @param otherBuilder 其他查询条件
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> or(JpaCriteriaBuilder<T>... otherBuilder) {
    // 创建一个新的 Specification 来存储合并后的查询条件
    Specification<T> combinedSpecification = Specification.where(specification);

    // 遍历所有传入的 JapCriteriaBuilder
    for (JpaCriteriaBuilder<T> builder : otherBuilder) {
      combinedSpecification = combinedSpecification.or(builder.build());
    }

    // 更新当前的 specification 为合并后的结果
    specification = combinedSpecification;
    return this;
  }

  /**
   * 页码
   *
   * @param page 页码
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> page(int page) {
    this.pageNumber = page;
    return this;
  }

  /**
   * 每页大小
   *
   * @param size 每页大小
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> size(int size) {
    this.pageSize = size;
    return this;
  }

  /**
   * 排序
   *
   * @param field     字段
   * @param direction 排序方向
   * @return JapCriteriaBuilder
   */
  public JpaCriteriaBuilder<T> orderBy(@NotNull String field, @NotNull Sort.Direction direction) {
    if (direction != null) {
      rawSortList.add(Sort.Order.by(field).with(direction));
    }
    return this;
  }

  /**
   * 构建分页查询条件
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
   * 构建查询条件
   *
   * @return Specification
   */
  public Specification<T> build() {
    return (root, query, cb) -> {
      // 应用排序条件
      if (!rawSortList.isEmpty()) {
        List<Order> orders = rawSortList.stream()
            .map(sortOrder -> {
              Path<Object> path = root.get(sortOrder.getProperty());
              return sortOrder.getDirection().isAscending() ? cb.asc(path) : cb.desc(path);
            })
            .collect(Collectors.toList());
        query.orderBy(orders);
      }
      // 生成查询条件
      return specification.toPredicate(root, query, cb);
    };
  }

  /**
   * 判断值是否有效
   *
   * @param value 值
   * @param <V>   泛型
   * @return boolean
   */
  private <V> boolean isValidValue(V value) {
    if (value instanceof String) {
      return Objects.nonNull(value) && !((String) value).trim().isEmpty();
    }
    return Objects.nonNull(value);
  }
}
