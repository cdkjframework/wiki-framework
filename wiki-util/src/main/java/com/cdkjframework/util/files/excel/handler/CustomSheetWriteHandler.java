package com.cdkjframework.util.files.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.date.LocalDateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.files.excel.converter
 * @ClassName: CustomSheetWriteHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/4/8 21:45
 * @Version: 1.0
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {

    /**
     * 标题
     */
    private String title;

    /**
     * 构建函数
     *
     * @param title 标题
     */
    public CustomSheetWriteHandler(String title) {
        this.title = title;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    /**
     * 创建图纸后
     *
     * @param writeWorkbookHolder 处理
     * @param writeSheetHolder    处理
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = workbook.getSheetAt(IntegerConsts.ZERO);
        // 设置标题
        Row row1 = sheet.createRow(IntegerConsts.ZERO);
        row1.setHeight((short) 800);
        Cell cell = row1.createCell(IntegerConsts.ZERO);
        cell.setCellValue(title);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 400);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(IntegerConsts.ZERO, IntegerConsts.ZERO, IntegerConsts.ZERO, IntegerConsts.NINE));
        // 创建时间
        Row rowTime = sheet.createRow(IntegerConsts.ONE);
        Cell cellTitle = rowTime.createCell(IntegerConsts.ZERO);
        Cell cellTime = rowTime.createCell(IntegerConsts.ONE);
        cellTitle.setCellValue("创建时间");
        cellTime.setCellValue(LocalDateUtils.dateTimeCurrentFormatter(LocalDateUtils.DATE_HH_MM_SS));
    }
}
