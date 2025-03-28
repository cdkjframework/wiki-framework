package com.cdkjframework.mybatispro.core.metadata;

import com.cdkjframework.util.tool.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序元素载体
 *
 * @author HCL
 * Create at 2019/5/27
 */
@Getter
@Setter
public class OrderItem implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 需要进行排序的字段
   */
  private String column;
  /**
   * 是否正序排列，默认 true
   */
  private boolean asc = true;

  public static OrderItem asc(String column) {
    return build(column, true);
  }

  public static OrderItem desc(String column) {
    return build(column, false);
  }

  public static List<OrderItem> ascs(String... columns) {
    return Arrays.stream(columns).map(OrderItem::asc).collect(Collectors.toList());
  }

  public static List<OrderItem> descs(String... columns) {
    return Arrays.stream(columns).map(OrderItem::desc).collect(Collectors.toList());
  }

  private static OrderItem build(String column, boolean asc) {
    return new OrderItem().setColumn(column).setAsc(asc);
  }

  public OrderItem setColumn(String column) {
    this.column = StringUtils.replaceAllBlank(column);
    return this;
  }

  public OrderItem setAsc(boolean asc) {
    this.asc = asc;
    return this;
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "column='" + column + '\'' +
        ", asc=" + asc +
        '}';
  }

}
