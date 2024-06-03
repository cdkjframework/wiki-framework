package com.cdkjframework.util.files.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.cdkjframework.util.date.LocalDateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.files.excel.converter
 * @ClassName: LocalDateConverter
 * @Description: 日期转换器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class LocalDateConverter implements Converter<LocalDate> {

  /**
   * 支持 JAVA 类型键
   *
   * @return 返回类类型
   */
  @Override
  public Class<LocalDate> supportJavaTypeKey() {
    return LocalDate.class;
  }

  /**
   * 支持Excel类型键
   *
   * @return 返回单元格数据类型枚举
   */
  @Override
  public CellDataTypeEnum supportExcelTypeKey() {
    return CellDataTypeEnum.STRING;
  }

  /**
   * 转换为 JAVA 数据
   *
   * @param cellData            单元格数据
   * @param contentProperty     内容属性
   * @param globalConfiguration 公共配置
   * @return 返回时间
   */

  @Override
  public LocalDate convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
    return LocalDate.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(LocalDateUtils.DATE));
  }

  /**
   * 转换为 JAVA 数据
   *
   * @param context 当前数据
   * @return 返回时间
   */
  @Override
  public LocalDate convertToJavaData(ReadConverterContext<?> context) throws Exception {
    String value = context.getReadCellData().getStringValue();
    return LocalDate.parse(value, DateTimeFormatter.ofPattern(LocalDateUtils.DATE));
  }

  /**
   * 转换为Excel数据
   *
   * @param context 当前数据
   * @return 返回例结果
   */

  @Override
  public WriteCellData<?> convertToExcelData(WriteConverterContext<LocalDate> context) throws Exception {
    LocalDate value = context.getValue();
    return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(LocalDateUtils.DATE)));
  }

  /**
   * 转换为Excel数据
   *
   * @param value               值
   * @param contentProperty     内容属性
   * @param globalConfiguration 公共配置
   * @return 返回结果
   * @throws Exception 异常
   */
  @Override
  public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
    return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(LocalDateUtils.DATE)));
  }
}
