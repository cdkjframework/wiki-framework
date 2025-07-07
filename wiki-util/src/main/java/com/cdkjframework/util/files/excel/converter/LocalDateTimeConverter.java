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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.files.excel.converter
 * @ClassName: LocalDateTimeConverter
 * @Description: 日间转换器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

  /**
   * 支持 JAVA 类型键
   *
   * @return 返回类类型
   */
  @Override
  public Class<LocalDateTime> supportJavaTypeKey() {
    return LocalDateTime.class;
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
  public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
    return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(LocalDateUtils.DATE_HH_MM_SS));
  }

  @Override
  public LocalDateTime convertToJavaData(ReadConverterContext<?> context) throws Exception {
    String value = context.getReadCellData().getStringValue();
    return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(LocalDateUtils.DATE_HH_MM_SS));

  }


  /**
   * 转换为Excel数据
   *
   * @param context 当前数据
   * @return 返回例结果
   */
  @Override
  public WriteCellData<?> convertToExcelData(WriteConverterContext<LocalDateTime> context) throws Exception {
    LocalDateTime value = context.getValue();
    return new WriteCellData<>(value.format(DateTimeFormatter.ofPattern(LocalDateUtils.DATE_HH_MM_SS)));
  }
}
