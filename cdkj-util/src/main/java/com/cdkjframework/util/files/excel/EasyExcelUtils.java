package com.cdkjframework.util.files.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.files.excel.easy
 * @ClassName: ExcelEasyPoiUtil
 * @Description: EasyPoi Excel 操作
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class EasyExcelUtils {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(EasyExcelUtils.class);

    /**
     * 文件后缀
     */
    private static final String excelSuffix = "xlsx";

    /**
     * 工作薄大小
     */
    private static final int SHEET_LIST_SIZE = 50000;

    /**
     * 读取 Excel 文件流转换为 list
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @param clazz       类
     * @param <T>
     * @return 返回泛型数据
     */
    public static <T> List<T> readExcelToList(InputStream inputStream, String fileName, Class<T> clazz) {
        // 解析每行结果在listener中处理
        ExcelListener listener = new ExcelListener();
        ExcelReaderBuilder excelReader = EasyExcel.read(inputStream, clazz, listener);
        if (StringUtils.isNotNullAndEmpty(fileName) && fileName.toLowerCase().endsWith(excelSuffix)) {
            excelReader.excelType(ExcelTypeEnum.XLSX);
        } else {
            excelReader.excelType(ExcelTypeEnum.XLS);
        }
        excelReader.build().readAll();
        return listener.getDataList();
    }

    /**
     * 将 list 转换为 OutputStream
     *
     * @param data  数据集
     * @param clazz 类
     * @param <T>
     * @return 返回 OutputStream
     * @throws GlobalException 异常信息
     */
    public static <T> OutputStream listExportOutputStream(List<T> data, Class<T> clazz) {
        // 返回数据
        Map<Integer, Integer> columnWidth = new HashMap<>(IntegerConsts.ONE);
        return listExportOutputStream(data, columnWidth, clazz);
    }

    /**
     * 将 list 转换为 OutputStream
     *
     * @param data        数据集
     * @param columnWidth 列宽度
     * @param clazz       类
     * @param <T>
     * @return 返回 OutputStream
     * @throws GlobalException 异常信息
     */
    public static <T> OutputStream listExportOutputStream(List<T> data, Map<Integer, Integer> columnWidth, Class<T> clazz) {
        //创建 OutputStream 流
        OutputStream outputStream = new ByteArrayOutputStream();
        ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream, clazz);
        writerBuilder.excelType(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = writerBuilder.build();

        int sheetPage = data.size() / SHEET_LIST_SIZE;
        if (data.size() % SHEET_LIST_SIZE > 0) {
            sheetPage += 1;
        }
        for (int i = 0; i < sheetPage; i++) {
            List<T> dataList = data.stream().skip(i * SHEET_LIST_SIZE).limit(SHEET_LIST_SIZE)
                    .collect(Collectors.toList());
            // 设置工作薄
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetName("Sheet" + (i + 1));
            if (columnWidth != null) {
                writeSheet.setColumnWidthMap(columnWidth);
            }
            writeSheet.setClazz(clazz);
            excelWriter.write(dataList, writeSheet);
        }
        excelWriter.finish();

        // 返回数据
        return outputStream;
    }

    /**
     * 将 list 转换为 InputStream
     *
     * @param data  数据集
     * @param clazz 类
     * @param <T>
     * @return 返回 InputStream
     */
    public static <T> InputStream listExportInputStream(List<T> data, Class<T> clazz) {
        Map<Integer, Integer> columnWidth = new HashMap<>(IntegerConsts.ONE);
        //创建 inputStream 流
        return listExportInputStream(data, columnWidth, clazz);
    }


    /**
     * 将 list 转换为 InputStream
     *
     * @param data        数据集
     * @param columnWidth 列宽度
     * @param clazz       类
     * @param <T>
     * @return 返回 InputStream
     */
    public static <T> InputStream listExportInputStream(List<T> data, Map<Integer, Integer> columnWidth, Class<T> clazz) {
        OutputStream outputStream = listExportOutputStream(data, columnWidth, clazz);
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;

        //创建 inputStream 流
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
