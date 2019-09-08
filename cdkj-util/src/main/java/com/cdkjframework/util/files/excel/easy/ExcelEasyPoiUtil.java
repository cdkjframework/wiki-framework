package com.cdkjframework.util.files.excel.easy;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.StringUtils;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
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
public class ExcelEasyPoiUtil<T> {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(ExcelEasyPoiUtil.class);

    /**
     * 工作薄大小
     */
    private static final int sheetListSize = 50000;

    /**
     * 将 list 转换为 File
     *
     * @param obj 数据集
     * @return 返回路径
     * @throws GlobalException 异常信息
     */
    public static String listExportFile(Object obj) throws GlobalException {
        return listExportFile(obj, null);
    }

    /**
     * 将 list 转换为 File
     *
     * @param obj           数据集
     * @param directoryPath 保存
     * @return 返回路径
     * @throws GlobalException 异常信息
     */
    public static String listExportFile(Object obj, String directoryPath) throws GlobalException {

        //文件路径
        String filePath = "/exportFiles/";

        try {

            //获取文件流
            OutputStream outputStream = listExportOutputStream(obj);
            //转换数据
            ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;
            //转换为 inputStream
            //生成文件名称
            String fileName = GeneratedValueUtils.getUuidNotTransverseLine() + ".xls";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            //保存文件
            if (StringUtils.isNotNullAndEmpty(directoryPath)) {
                FileUtils.saveFile(inputStream, directoryPath, filePath, fileName);
            } else {
                FileUtils.saveFile(inputStream, filePath, fileName);
            }
            //删除历史文件
            long date = System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2;
            FileUtils.beforeDeleteSpecifiedTimeFile(new Date(date), filePath);

            filePath += fileName;
        } catch (GlobalException ex) {
            logUtil.error(ex.getMessage());
            throw new GlobalException("导出数据转换出错误");
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
            throw new GlobalException("导出数据转换出错误");
        }

        //返回结果
        return filePath;
    }

    /**
     * 将 list 转换为 Sheet
     *
     * @param obj 数据集
     * @return 返回流
     */
    public static OutputStream listExportOutputStream(Object obj) throws GlobalException, IOException {
        //创建 OutputStream 流
        OutputStream outputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);

        try {
            // 执行方法
            Workbook workbook = listExportWorkbook(obj);
            //输出文件流
            workbook.write(bufferedOutPut);
        } catch (GlobalException ex) {
            logUtil.error(ex.getMessage());
            throw new GlobalException("导出数据转换出错误");
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
            throw new GlobalException("导出数据转换出错误");
        } finally {
            bufferedOutPut.flush();
            bufferedOutPut.close();
        }

        //返回结果
        return outputStream;
    }

    /**
     * 将 list 转换为 Sheet
     *
     * @param obj 数据集
     * @return 返工作簿
     */
    public static Workbook listExportWorkbook(Object obj) throws GlobalException {
        ArrayList classList;
        try {
            classList = (ArrayList) obj;
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
            throw new GlobalException("不支持的数据类型！");
        }
        //验证实体是否有数据
        if (CollectionUtils.isEmpty(classList)) {
            logUtil.error("没有数据可导出！");
            throw new GlobalException("没有数据可导出！");
        }

        //工作簿
        Workbook workbook;

        try {
            //获取实体信息
            Class clazz = classList.get(0).getClass();

            //读取工作薄个数
            int pageSize = classList.size() / sheetListSize;
            if (classList.size() % sheetListSize > 0) {
                pageSize += 1;
            }

            // 将sheet1和sheet2使用得map进行包装
            List<Map<String, Object>> sheetsList = new ArrayList<>();

            for (int i = 0; i < pageSize; i++) {
                //取出数据
                List list = (List) classList
                        .stream()
                        .skip(i * sheetListSize)
                        .limit(sheetListSize)
                        .collect(Collectors.toList());

                // 创建参数对象（用来设定excel得sheet得内容等信息）
                ExportParams params = new ExportParams();
                // 设置sheet得名称
                params.setSheetName("Sheet" + (i + 1));
                params.setCreateHeadRows(true);
                params.setHeaderHeight(20);

                // 创建sheet1使用得map
                Map<String, Object> dataMap = new HashMap<>(3);
                // title 的参数为 ExportParams 类型
                dataMap.put("title", params);
                // 模版导出对应得实体类型
                dataMap.put("cdkj/framework/entity", clazz);
                // sheet 中要填充得数据
                dataMap.put("data", list);
                //添加工作薄
                sheetsList.add(dataMap);
            }
            // 执行方法
            workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
        } catch (Exception ex) {
            logUtil.error(ex.getMessage());
            throw new GlobalException("导出数据转换出错误");
        }

        //返回结果
        return workbook;
    }
}
