package com.cdkjframework.util.files.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.files.excel
 * @ClassName: ExcelListener
 * @Description: 解析监听器
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ExcelListener<T> extends AnalysisEventListener {

  /**
   * 数据列表
   */
  private List<T> dataList = new ArrayList<>();

  /**
   * 分析单行时触发调用函数。
   *
   * @param data    一行值
   * @param context
   */
  @Override
  public void invoke(Object data, AnalysisContext context) {
    //数据存储到list，供批量处理，或后续自己业务逻辑处理。
    dataList.add((T) data);
  }

  /**
   * 要做的分析后
   *
   * @param context
   */
  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {

  }

  public List<T> getDataList() {
    return dataList;
  }

  public void setDataList(List<T> dataList) {
    this.dataList = dataList;
  }
}
