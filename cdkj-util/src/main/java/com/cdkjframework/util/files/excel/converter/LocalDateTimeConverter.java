package com.cdkjframework.util.files.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.cdkjframework.util.date.LocalDateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ProjectName: cdkj-framework
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
    public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) {
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(LocalDateUtils.DATE_HH_MM_SS));
    }

    /**
     * 转换为Excel数据
     *
     * @param value               值
     * @param contentProperty     内容属性
     * @param globalConfiguration 公共配置
     * @return 返回例结果
     */
    @Override
    public CellData<String> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new CellData<>(value.format(DateTimeFormatter.ofPattern(LocalDateUtils.DATE_HH_MM_SS)));
    }
}
