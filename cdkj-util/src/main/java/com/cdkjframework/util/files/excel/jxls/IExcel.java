package com.cdkjframework.util.files.excel.jxls;

import com.cdkjframework.exceptions.GlobalException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.files.excel
 * @ClassName: IExcel
 * @Description: Excel 接口
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface IExcel<T> {

    /**
     * 将 excel 读取为 list
     *
     * @param inputStream 文件流
     * @param tClass      实体
     * @return 返回数据集结果
     */
    List<T> readExcelToList(InputStream inputStream, Class<T> tClass) throws IOException, SAXException, GlobalException;

    /**
     * 将 list 读取为 excel
     *
     * @param list 数据集
     * @return 返回文件路径
     */
    String readListToExcel(List<T> list);

    /**
     * 读取模板导出数据
     *
     * @param list         数据源
     * @param templatePath 模板路径
     * @return 返回文件路径
     */
    String readTemplateToExcel(List<T> list, String templatePath);

    /**
     * 将 list 读取为 excel
     *
     * @param list 数据集
     * @return 返回文件流
     */
    OutputStream readListToExcelStream(List<T> list);

    /**
     * 读取模板导出数据
     *
     * @param list         数据源
     * @param templatePath 模板路径
     * @return 返回文件流
     */
    OutputStream readTemplateToExcelStream(List<T> list, String templatePath);

    /**
     * 读取 list 标题
     *
     * @param tClass 返回结果
     * @return 返回表头结果
     */
    List<String> readListToHeader(Class<T> tClass) throws GlobalException;
}
