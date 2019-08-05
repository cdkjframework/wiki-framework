package com.cdkjframework.util.files.excel.jxls.service;

import com.cdkjframework.exceptions.GlobalException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: com.cdkjframework.core
 * @Package: com.cdkjframework.core.util.files.excel
 * @ClassName: ExcelUtil
 * @Description: Excel 工具
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class ExcelUtil<T> extends AbstractExcelService<T> {

    /**
     * 将 excel 读取为 list
     *
     * @param inputStream 文件流
     * @param tClass      实体
     * @return 返回数据集结果
     */
    @Override
    public List<T> readExcelToList(InputStream inputStream, Class<T> tClass) throws IOException, SAXException, GlobalException {
        if (inputStream == null || inputStream.available() == 0) {
            throw new GlobalException("没有文件可读取！");
        }

        //读取 XLS 模板路径
        ClassPathResource templateResource = new ClassPathResource(TEMPLATE);
        //读取 XML 模板路径
        ClassPathResource templateXmlConfigResource = new ClassPathResource(IMPORT_TEMPLATE_XML_CONFIG);
        try (InputStream xmlStream = templateXmlConfigResource.getInputStream()) {
            //读取文件流
            XLSReader reader = ReaderBuilder.buildFromXML(xmlStream);
            //读取数据
            Map mapList = new HashMap(1);
            mapList.put("list", new HashMap<>());
            reader.read(inputStream, mapList);
            int n = mapList.size();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将 list 读取为 excel
     *
     * @param list 数据集
     * @return 返回文件路径
     */
    @Override
    public String readListToExcel(List<T> list) {
        return null;
    }

    /**
     * 读取模板导出数据
     *
     * @param list         数据源
     * @param templatePath 模板路径
     * @return 返回文件路径
     */
    @Override
    public String readTemplateToExcel(List<T> list, String templatePath) {
        return null;
    }

    /**
     * 读取模板导出数据
     *
     * @param list         数据源
     * @param templatePath 模板路径
     * @return 返回文件流
     */
    @Override
    public OutputStream readTemplateToExcelStream(List<T> list, String templatePath) {
        return null;
    }

    /**
     * 将 list 读取为 excel
     *
     * @param list 数据集
     * @return 返回文件流
     */
    @Override
    public OutputStream readListToExcelStream(List<T> list) {
        return null;
    }

    /**
     * 读取 list 标题
     *
     * @param tClass 返回结果
     * @return 返回表头结果
     */
    @Override
    public List<String> readListToHeader(Class<T> tClass) throws GlobalException {
        return super.readListToHeader(tClass);
    }
}
