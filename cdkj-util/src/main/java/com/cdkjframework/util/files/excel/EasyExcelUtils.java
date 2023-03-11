package com.cdkjframework.util.files.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.excel.converter.CustomMergeStrategy;
import com.cdkjframework.util.files.excel.converter.LocalDateConverter;
import com.cdkjframework.util.files.excel.converter.LocalDateTimeConverter;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
     * 默认值
     */
    private static final Map<Integer, List<Object>> MERGE_DATA = null;

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
        ExcelReaderBuilder excelReader = EasyExcel.read(inputStream, clazz, listener)
                .registerConverter(new LocalDateTimeConverter())
                .registerConverter(new LocalDateConverter());
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
        return listExportOutputStream(data, clazz, MERGE_DATA);
    }

    /**
     * 将 list 转换为 OutputStream
     *
     * @param data      数据集
     * @param clazz     类
     * @param mergeData 合并数据
     * @param <T>
     * @return 返回 OutputStream
     * @throws GlobalException 异常信息
     */
    public static <T, S> OutputStream listExportOutputStream(List<T> data, Class<T> clazz, Map<Integer, List<S>> mergeData) {
        // 返回数据
        Map<Integer, Integer> columnWidth = new HashMap<>(IntegerConsts.ONE);
        return listExportOutputStream(data, columnWidth, clazz, mergeData, IntegerConsts.ZERO);
    }

    /**
     * 将 list 转换为 OutputStream
     *
     * @param data      数据集
     * @param clazz     类
     * @param mergeData 合并数据
     * @param type      合并类型（0 自动计算合并列，1 指定合并列）
     * @param <T>
     * @return 返回 OutputStream
     * @throws GlobalException 异常信息
     */
    public static <T, S> OutputStream listExportOutputStream(List<T> data, Class<T> clazz, Map<Integer, List<S>> mergeData, int type) {
        // 返回数据
        Map<Integer, Integer> columnWidth = new HashMap<>(IntegerConsts.ONE);
        return listExportOutputStream(data, columnWidth, clazz, mergeData, type);
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
    public static <T> void listExportOutputStream(OutputStream outputStream, List<T> data, Class<T> clazz) throws IOException {
        listExportOutputStream(outputStream, data, clazz, MERGE_DATA);
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
    public static <T, S> void listExportOutputStream(OutputStream outputStream, List<T> data, Class<T> clazz, Map<Integer, List<S>> mergeData) throws IOException {
        listExportOutputStream(outputStream, data, clazz, MERGE_DATA, IntegerConsts.ZERO);
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
    public static <T, S> void listExportOutputStream(OutputStream outputStream, List<T> data, Class<T> clazz, Map<Integer, List<S>> mergeData, int type) throws IOException {
        // 返回数据
        Map<Integer, Integer> columnWidth = new HashMap<>(IntegerConsts.ONE);
        ByteArrayOutputStream output = (ByteArrayOutputStream) listExportOutputStream(data, columnWidth, clazz, mergeData, type);
        outputStream.write(output.toByteArray());
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
     * @param data      数据集
     * @param clazz     类
     * @param mergeData 合并数据
     * @param <T>
     * @return 返回 InputStream
     */
    public static <T, S> InputStream listExportInputStream(List<T> data, Class<T> clazz, Map<Integer, List<S>> mergeData) {
        //创建 inputStream 流
        return listExportInputStream(data, clazz, mergeData, IntegerConsts.ZERO);
    }

    /**
     * 将 list 转换为 InputStream
     *
     * @param data      数据集
     * @param clazz     类
     * @param mergeData 合并数据
     * @param type      合并类型（0 自动计算合并列，1 指定合并列）
     * @param <T>
     * @return 返回 InputStream
     */
    public static <T, S> InputStream listExportInputStream(List<T> data, Class<T> clazz, Map<Integer, List<S>> mergeData, int type) {
        Map<Integer, Integer> columnWidth = new HashMap<>(IntegerConsts.ONE);
        //创建 inputStream 流
        return listExportInputStream(data, columnWidth, clazz, mergeData, type);
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
    public static <T, S> InputStream listExportInputStream(List<T> data, Map<Integer, Integer> columnWidth, Class<T> clazz, Map<Integer, List<S>> mergeData, int type) {
        OutputStream outputStream = listExportOutputStream(data, columnWidth, clazz, mergeData, type);
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;

        //创建 inputStream 流
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
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
        OutputStream outputStream = listExportOutputStream(data, columnWidth, clazz, MERGE_DATA, IntegerConsts.ZERO);
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;

        //创建 inputStream 流
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
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
    private static <T, S> OutputStream listExportOutputStream(List<T> data, Map<Integer, Integer> columnWidth, Class<T> clazz, Map<Integer, List<S>> mergeData, int type) {
        //创建 OutputStream 流
        OutputStream outputStream = new ByteArrayOutputStream();
        ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream, clazz)
                .registerConverter(new LocalDateTimeConverter())
                .registerConverter(new LocalDateConverter());

        // 设置合并列
        if (!CollectionUtils.isEmpty(mergeData)) {
            Set<Map.Entry<Integer, List<S>>> entrySet = mergeData.entrySet();
            for (Map.Entry<Integer, List<S>> entry :
                    entrySet) {
                switch (type) {
                    case 1:
                        writerBuilder.registerWriteHandler(new CustomMergeStrategy(null, (List<Integer>) entry.getValue(), entry.getKey()));
                        break;
                    default:
                        writerBuilder.registerWriteHandler(new CustomMergeStrategy((List<String>) entry.getValue(), null, entry.getKey()));
                        break;
                }
            }
        }

        writerBuilder.excelType(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = writerBuilder.build();

        int sheetPage = data.size() / SHEET_LIST_SIZE;
        if (data.size() % SHEET_LIST_SIZE > IntegerConsts.ZERO) {
            sheetPage += IntegerConsts.ONE;
        }
        for (int i = IntegerConsts.ZERO; i < sheetPage; i++) {
            List<T> dataList = data.stream().skip(i * SHEET_LIST_SIZE).limit(SHEET_LIST_SIZE)
                    .collect(Collectors.toList());
            // 设置工作薄
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetName("Sheet" + (i + IntegerConsts.ONE));
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

}
