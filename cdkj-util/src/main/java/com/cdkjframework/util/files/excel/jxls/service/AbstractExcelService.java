package com.cdkjframework.util.files.excel.jxls.service;

import com.cdkjframework.core.annotation.FieldMeta;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.excel.jxls.IExcel;
import com.cdkjframework.util.tool.StringUtils;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.files.excel.service
 * @ClassName: AbstractExcelUtil
 * @Description: Excel 抽象工具
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractExcelService<T> implements IExcel<T> {

    /**
     * 导入 XML 模板路径
     */
    protected final String TEMPLATE_XML_CONFIG = "/com/cdkjframework/core/export/xls/template.xml";

    /**
     * 导出 XML 模板路径
     */
    protected final String IMPORT_TEMPLATE_XML_CONFIG = "/com/cdkjframework/core/export/xls/import.xml";
    /**
     * XLS 模板路径
     */
    protected final String TEMPLATE = "/com/cdkjframework/core/export/xls/template.xls";


    /**
     * 将 excel 读取为 list
     *
     * @param inputStream 文件流
     * @param tClass      实体
     * @return 返回数据集结果
     */
    @Override
    public abstract List<T> readExcelToList(InputStream inputStream, Class<T> tClass) throws IOException, SAXException, GlobalException;

    /**
     * 将 list 读取为 excel
     *
     * @param list 数据集
     * @return 返回文件路径
     */
    @Override
    public abstract String readListToExcel(List<T> list);

    /**
     * 读取模板导出数据
     *
     * @param list         数据源
     * @param templatePath 模板路径
     * @return 返回文件路径
     */
    @Override
    public abstract String readTemplateToExcel(List<T> list, String templatePath);

    /**
     * 读取模板导出数据
     *
     * @param list         数据源
     * @param templatePath 模板路径
     * @return 返回文件流
     */
    @Override
    public abstract OutputStream readTemplateToExcelStream(List<T> list, String templatePath);

    /**
     * 将 list 读取为 excel
     *
     * @param list 数据集
     * @return 返回文件流
     */
    @Override
    public abstract OutputStream readListToExcelStream(List<T> list);

    /**
     * 读取 list 标题
     *
     * @param tClass 返回结果
     * @return 返回表头结果
     */
    @Override
    public List<String> readListToHeader(Class<T> tClass) throws GlobalException {
        if (tClass == null) {
            throw new GlobalException("实体为空");
        }
        //读取
        Field[] fields = tClass.getDeclaredFields();

        //表头
        List<String> header = new ArrayList<>();
        for (Field field :
                fields) {
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null || annotations.length == 0) {
                continue;
            }
            FieldMeta meta = field.getAnnotation(FieldMeta.class);
            String des = meta.description();
            if (StringUtils.isNullAndSpaceOrEmpty(des)) {
                continue;
            }
            header.add(des);
        }
        //返回结果
        return header;
    }
}
