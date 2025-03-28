package com.cdkjframework.mybatispro.core.toolkit;

import com.cdkjframework.constant.Constants;
import com.cdkjframework.mybatispro.core.metadata.TableInfo;
import com.cdkjframework.mybatispro.core.metadata.TableInfoHelper;
import com.cdkjframework.mybatispro.core.metadata.TableFieldInfo;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.mybatispro.core.toolkit
 * @ClassName: SqlScriptUtils
 * @Description: Java 类说明
 * @Author: xiaLin
 * @Version: 1.0
 */
public class SqlScriptUtils implements Constants {
  /**
   * 验证字符串是否是数据库字段
   */
  private static final Pattern P_IS_COLUMN = Pattern.compile("^\\w\\S*[\\w\\d]*$");

  private static final String tp = "[\\w-,]+?";

  private static final Pattern pattern = Pattern.compile(String.format("\\{@((%s)|(%s:\\w+?)|(%s:\\w+?:\\w+?))}", tp, tp, tp));

  /**
   * 判断字符串是否符合数据库字段的命名
   *
   * @param str 字符串
   * @return 判断结果
   */
  public static boolean isNotColumnName(String str) {
    return !P_IS_COLUMN.matcher(str).matches();
  }

  /**
   * 获取真正的字段名
   *
   * @param column 字段名
   * @return 字段名
   */
  public static String getTargetColumn(String column) {
    if (isNotColumnName(column)) {
      return column.substring(1, column.length() - 1);
    }
    return column;
  }

  /**
   * <p>
   * 获取 带 if 标签的脚本
   * </p>
   *
   * @param sqlScript sql 脚本片段
   * @return if 脚本
   */
  public static String convertIf(final String sqlScript, final String ifTest, boolean newLine) {
    String newSqlScript = sqlScript;
    if (newLine) {
      newSqlScript = NEWLINE + newSqlScript + NEWLINE;
    }
    return String.format("<if test=\"%s\">%s</if>", ifTest, newSqlScript);
  }

  /**
   * <p>
   * 获取 带 trim 标签的脚本
   * </p>
   *
   * @param sqlScript       sql 脚本片段
   * @param prefix          以...开头
   * @param suffix          以...结尾
   * @param prefixOverrides 干掉最前一个...
   * @param suffixOverrides 干掉最后一个...
   * @return trim 脚本
   */
  public static String convertTrim(final String sqlScript, final String prefix, final String suffix,
                                   final String prefixOverrides, final String suffixOverrides) {
    StringBuilder sb = new StringBuilder("<trim");
    if (StringUtils.isNotNullAndEmpty(prefix)) {
      sb.append(" prefix=\"").append(prefix).append(QUOTE);
    }
    if (StringUtils.isNotNullAndEmpty(suffix)) {
      sb.append(" suffix=\"").append(suffix).append(QUOTE);
    }
    if (StringUtils.isNotNullAndEmpty(prefixOverrides)) {
      sb.append(" prefixOverrides=\"").append(prefixOverrides).append(QUOTE);
    }
    if (StringUtils.isNotNullAndEmpty(suffixOverrides)) {
      sb.append(" suffixOverrides=\"").append(suffixOverrides).append(QUOTE);
    }
    return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</trim>").toString();
  }

  /**
   * <p>
   * 生成 choose 标签的脚本
   * </p>
   *
   * @param whenTest  when 内 test 的内容
   * @param otherwise otherwise 内容
   * @return choose 脚本
   */
  public static String convertChoose(final String whenTest, final String whenSqlScript, final String otherwise) {
    return "<choose>" + NEWLINE
        + "<when test=\"" + whenTest + QUOTE + RIGHT_CHEV + NEWLINE
        + whenSqlScript + NEWLINE + "</when>" + NEWLINE
        + "<otherwise>" + otherwise + "</otherwise>" + NEWLINE
        + "</choose>";
  }

  /**
   * <p>
   * 生成 foreach 标签的脚本
   * </p>
   *
   * @param sqlScript  foreach 内部的 sql 脚本
   * @param collection collection
   * @param index      index
   * @param item       item
   * @param separator  separator
   * @return foreach 脚本
   */
  public static String convertForeach(final String sqlScript, final String collection, final String index,
                                      final String item, final String separator) {
    StringBuilder sb = new StringBuilder("<foreach");
    if (StringUtils.isNotNullAndEmpty(collection)) {
      sb.append(" collection=\"").append(collection).append(QUOTE);
    }
    if (StringUtils.isNotNullAndEmpty(index)) {
      sb.append(" index=\"").append(index).append(QUOTE);
    }
    if (StringUtils.isNotNullAndEmpty(item)) {
      sb.append(" item=\"").append(item).append(QUOTE);
    }
    if (StringUtils.isNotNullAndEmpty(separator)) {
      sb.append(" separator=\"").append(separator).append(QUOTE);
    }
    return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</foreach>").toString();
  }

  /**
   * <p>
   * 生成 where 标签的脚本
   * </p>
   *
   * @param sqlScript where 内部的 sql 脚本
   * @return where 脚本
   */
  public static String convertWhere(final String sqlScript) {
    return "<where>" + NEWLINE + sqlScript + NEWLINE + "</where>";
  }

  /**
   * <p>
   * 生成 set 标签的脚本
   * </p>
   *
   * @param sqlScript set 内部的 sql 脚本
   * @return set 脚本
   */
  public static String convertSet(final String sqlScript) {
    return "<set>" + NEWLINE + sqlScript + NEWLINE + "</set>";
  }

  /**
   * <p>
   * 安全入参:  #{入参}
   * </p>
   *
   * @param param 入参
   * @return 脚本
   */
  public static String safeParam(final String param) {
    return safeParam(param, null);
  }

  /**
   * <p>
   * 安全入参:  #{入参,mapping}
   * </p>
   *
   * @param param   入参
   * @param mapping 映射
   * @return 脚本
   */
  public static String safeParam(final String param, final String mapping) {
    String target = HASH_LEFT_BRACE + param;
    if (StringUtils.isNullAndSpaceOrEmpty(mapping)) {
      return target + RIGHT_BRACE;
    }
    return target + COMMA + mapping + RIGHT_BRACE;
  }

  /**
   * <p>
   * 非安全入参:  ${入参}
   * </p>
   *
   * @param param 入参
   * @return 脚本
   */
  public static String unSafeParam(final String param) {
    return DOLLAR_LEFT_BRACE + param + RIGHT_BRACE;
  }

  public static String mappingTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
    if (typeHandler != null) {
      return "typeHandler=" + typeHandler.getName();
    }
    return null;
  }

  public static String mappingJdbcType(JdbcType jdbcType) {
    if (jdbcType != null) {
      return "jdbcType=" + jdbcType.name();
    }
    return null;
  }

  public static String mappingNumericScale(Integer numericScale) {
    if (numericScale != null) {
      return "numericScale=" + numericScale;
    }
    return null;
  }

  public static String convertParamMapping(Class<? extends TypeHandler<?>> typeHandler, JdbcType jdbcType, Integer numericScale) {
    if (typeHandler == null && jdbcType == null && numericScale == null) {
      return null;
    }
    String mapping = null;
    if (typeHandler != null) {
      mapping = mappingTypeHandler(typeHandler);
    }
    if (jdbcType != null) {
      mapping = appendMapping(mapping, mappingJdbcType(jdbcType));
    }
    if (numericScale != null) {
      mapping = appendMapping(mapping, mappingNumericScale(numericScale));
    }
    return mapping;
  }

  private static String appendMapping(String mapping, String other) {
    if (mapping != null) {
      return mapping + COMMA + other;
    }
    return other;
  }


  public static List<String> findPlaceholder(String sql) {
    Matcher matcher = pattern.matcher(sql);
    List<String> list = new ArrayList<>();
    while (matcher.find()) {
      list.add(matcher.group());
    }
    return list;
  }

  public static String replaceSqlPlaceholder(String sql, List<String> placeHolder, String escapeSymbol) {
    for (String s : placeHolder) {
      String s1 = s.substring(2, s.length() - 1);
      int i1 = s1.indexOf(COLON);
      String tableName;
      String alisa = null;
      String asAlisa = null;
      if (i1 < 0) {
        tableName = s1;
      } else {
        tableName = s1.substring(0, i1);
        s1 = s1.substring(i1 + 1);
        i1 = s1.indexOf(COLON);
        if (i1 < 0) {
          alisa = s1;
        } else {
          alisa = s1.substring(0, i1);
          asAlisa = s1.substring(i1 + 1);
        }
      }
      sql = sql.replace(s, getSelectBody(tableName, alisa, asAlisa, escapeSymbol));
    }
    return sql;
  }

  @SuppressWarnings("all")
  public static String getSelectBody(String tableName, String alisa, String asAlisa, String escapeSymbol) {
    int notSel = tableName.indexOf("-");
    List<String> notSelColl = null;
    if (notSel > 0) {
      notSelColl = Arrays.asList(tableName.substring(notSel + 1).split(COMMA));
      tableName = tableName.substring(0, notSel);
    }
    TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
    AssertUtils.isEmptyMessage(tableInfo, String.format("can not find TableInfo Cache by \"%s\"", tableName));
    String s = tableInfo.chooseSelect(TableFieldInfo::isSelect, notSelColl);
    if (alisa == null) {
      return s;
    }
    return getNewSelectBody(s, alisa, asAlisa, escapeSymbol);
  }

  public static String getNewSelectBody(String selectBody, String alisa, String asAlisa, String escapeSymbol) {
    String[] split = selectBody.split(COMMA);
    StringBuilder sb = new StringBuilder();
    boolean asA = asAlisa != null;
    for (String body : split) {
      final String sa = alisa.concat(DOT);
      if (asA) {
        int as = body.indexOf(AS);
        String column;
        String property;
        if (as < 0) {
          column = body;
          property = SqlScriptUtils.getTargetColumn(body);
        } else {
          column = body.substring(0, as);
          property = body.substring(as + 4);
          property = SqlScriptUtils.getTargetColumn(property);
        }
        sb.append(sa).append(column).append(AS).append(escapeColumn(asAlisa.concat(DOT).concat(property), escapeSymbol));
      } else {
        sb.append(sa).append(body);
      }
      sb.append(COMMA);
    }
    return sb.deleteCharAt(sb.length() - 1).toString();
  }

  private static String escapeColumn(String column, String escapeSymbol) {
    return escapeSymbol.concat(column).concat(escapeSymbol);
  }
}
