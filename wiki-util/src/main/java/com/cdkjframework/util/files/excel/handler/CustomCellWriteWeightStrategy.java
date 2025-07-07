package com.cdkjframework.util.files.excel.handler;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.cdkjframework.constant.IntegerConsts;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.files.excel.converter
 * @ClassName: CustomCellWriteWeightStrategy
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2023/4/7 22:43
 * @Version: 1.0
 */
public class CustomCellWriteWeightStrategy extends AbstractColumnWidthStyleStrategy {

  /**
   * 隐藏物
   */
  private Map<Integer, Map<Integer, Integer>> CACHE = new HashMap<>();


  /**
   * 设置头创建时的列宽
   *
   * @param writeSheetHolder 书写纸夹
   * @param cellDataList     列数据
   * @param cell             列
   * @param head             头
   * @param relativeRowIndex 行号
   * @param isHead           是否有表头
   */
  @Override
  protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
    if (needSetWidth) {
      Map<Integer, Integer> maxColumnWidthMap = CACHE.get(writeSheetHolder.getSheetNo());
      if (maxColumnWidthMap == null) {
        maxColumnWidthMap = new HashMap<>(IntegerConsts.ONE);
        CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
      }

      Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
      if (columnWidth >= 0) {
        if (columnWidth > 254) {
          columnWidth = 254;
        }

        Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
        if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
          maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
          Sheet sheet = writeSheetHolder.getSheet();
          sheet.setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
        }

      }
    }
  }

  /**
   * 计算长度
   *
   * @param cellDataList 列数据
   * @param cell         列
   * @param isHead       是否为头
   * @return 长度
   */
  private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
    if (isHead) {
      return cell.getStringCellValue().getBytes().length;
    } else {
      CellData cellData = cellDataList.get(0);
      CellDataTypeEnum type = cellData.getType();
      if (type == null) {
        return -1;
      } else {
        switch (type) {
          case STRING:
            // 换行符（数据需要提前解析好）
            int index = cellData.getStringValue().indexOf("\n");
            return index != -1 ?
                    cellData.getStringValue().substring(0, index).getBytes().length + 1 : cellData.getStringValue().getBytes().length + 1;
          case BOOLEAN:
            return cellData.getBooleanValue().toString().getBytes().length;
          case NUMBER:
            return cellData.getNumberValue().toString().getBytes().length;
          default:
            return -1;
        }
      }
    }
  }
}
