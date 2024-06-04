package com.cdkjframework.util.files.excel.handler;

import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFont;

import java.util.List;

import static org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFont.*;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.files.excel.handler
 * @ClassName: CustomCellStyleStrategy
 * @Description: 设置表头和填充内容的样式
 * @Author: xiaLin
 * @Date: 2023/4/8 22:15
 * @Version: 1.0
 */
public class CustomCellStyleStrategy extends AbstractCellStyleStrategy {

    /**
     * 设备头部新式
     */
    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.NO_FILL);
        Font contentWriteFont = workbook.createFont();
        contentWriteFont.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(contentWriteFont);
        cellStyle.setBorderBottom(BorderStyle.NONE);
        cellStyle.setBorderTop(BorderStyle.NONE);
        cellStyle.setBorderLeft(BorderStyle.NONE);
        cellStyle.setBorderRight(BorderStyle.NONE);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 设置填充数据样式
     *
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
    }
}
