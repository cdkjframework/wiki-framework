package com.cdkjframework.util.files.excel.converter;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.cdkjframework.constant.IntegerConsts;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.files.excel.converter
 * @ClassName: CustomMergeStrategy
 * @Description: 自定义合并策略 该类继承了AbstractMergeStrategy抽象合并策略，需要重写merge()方法
 * @Author: xiaLin
 * @Date: 2023/3/11 22:24
 * @Version: 1.0
 */
public class CustomMergeStrategy extends AbstractMergeStrategy {

    /**
     * 分组，每几行合并一次
     */
    private List<Integer> exportFieldGroupCountList;

    /**
     * 目标合并列 index
     */
    private Integer targetColumnIndex;

    /**
     * 需要开始合并单元格的首行 index
     */
    private Integer rowIndex;

    /**
     * exportDataList 为待合并目标列的值
     *
     * @param exportDataList    导出数据列表
     * @param exportGroupList   导出数据列表
     * @param targetColumnIndex 目标列索引
     */
    public CustomMergeStrategy(List<String> exportDataList, List<Integer> exportGroupList, Integer targetColumnIndex) {
        if (CollectionUtils.isEmpty(exportGroupList)) {
            this.exportFieldGroupCountList = getGroupCountList(exportDataList);
        } else {
            this.exportFieldGroupCountList = exportGroupList;
        }
        this.targetColumnIndex = targetColumnIndex;
    }

    /**
     * 重写合并列表
     *
     * @param sheet            标签
     * @param cell             列
     * @param head             头
     * @param relativeRowIndex 相对行索引
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        if (null == rowIndex) {
            rowIndex = cell.getRowIndex();
        }
        // 仅从首行以及目标列的单元格开始合并，忽略其他
        if (cell.getRowIndex() == rowIndex && cell.getColumnIndex() == targetColumnIndex) {
            mergeGroupColumn(sheet);
        }
    }

    /**
     * 合并组列
     *
     * @param sheet 标签
     */
    private void mergeGroupColumn(Sheet sheet) {
        int rowCount = rowIndex;
        for (Integer count : exportFieldGroupCountList) {
            if (count == IntegerConsts.ONE) {
                rowCount += count;
                continue;
            }
            int lastRow = rowCount + count - IntegerConsts.ONE;
            // 合并单元格
            CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, lastRow, targetColumnIndex, targetColumnIndex);
            sheet.addMergedRegionUnsafe(cellRangeAddress);
            rowCount += count;
        }
    }

    /**
     * 该方法将目标列根据值是否相同连续可合并，存储可合并的行数
     *
     * @param exportDataList 导出数据列表
     * @return 返回结果
     */
    private List<Integer> getGroupCountList(List<String> exportDataList) {
        if (CollectionUtils.isEmpty(exportDataList)) {
            return new ArrayList<>();
        }
        List<Integer> groupCountList = new ArrayList<>();
        int count = IntegerConsts.ONE;
        for (int i = IntegerConsts.ONE; i < exportDataList.size(); i++) {
            if (exportDataList.get(i).equals(exportDataList.get(i - IntegerConsts.ONE))) {
                count++;
            } else {
                groupCountList.add(count);
                count = IntegerConsts.ONE;
            }
        }
        // 处理完最后一条后
        groupCountList.add(count);

        // 返回结果
        return groupCountList;
    }
}
