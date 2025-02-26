package com.cdkjframework.mybatispro.core;

import com.cdkjframework.util.tool.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core
 * @ClassName: SharedString
 * @Description: 共享字符串
 * @Author: xiaLin
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SharedString implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1536422416594422874L;

  /**
   * 共享的 string 值
   */
  private String stringValue;

  /**
   * SharedString 里是 ""
   */
  public static SharedString emptyString() {
    return new SharedString(StringUtils.EMPTY);
  }

  /**
   * 置 empty
   */
  public void toEmpty() {
    stringValue = StringUtils.EMPTY;
  }

  /**
   * 置 null
   */
  public void toNull() {
    stringValue = null;
  }
}
