package com.cdkjframework.entity.center;

import com.cdkjframework.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.entity.center
 * @ClassName: CenterTableLayoutEntity
 * @Description: 表格配置
 * @Author: xiaLin
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
@Component
@Table(name = "center_table_layout", schema = "configure")
public class CenterTableLayoutEntity extends BaseEntity {

    /**
     * 页面表格名称
     */
    @Column(name = "table_name", columnDefinition = "页面表格名称", unique = true, length = 500)
    private String tableName;
    /**
     * 字段名称
     */
    @Column(name = "field_name", columnDefinition = "字段名称", length = 500)
    private String fieldName;

    /**
     * 字段编码
     */
    @Column(name = "field_code", columnDefinition = "字段编码", length = 500)
    private String fieldCode;

    /**
     * 字段类型
     */
    @Column(name = "field_type", columnDefinition = "字段类型", length = 500)
    private String fieldType;

    /**
     * 字段转换值
     */
    @Column(name = "field_value", columnDefinition = "字段转换值", length = 500)
    private String fieldValue;

    /**
     * 固定位置
     */
    @Column(name = "fixed", columnDefinition = "固定位置", length = 500)
    private String fixed;

    /**
     * 宽度
     */
    @Column(name = "width", columnDefinition = "宽度", length = 18, scale = 2)
    private BigDecimal width;

    /**
     * 宽度
     */
    @Column(name = "btn_list", columnDefinition = "宽度", length = 500)
    private String btnList;

    /**
     * 对齐方式
     */
    @Column(name = "align", columnDefinition = "对齐方式", length = 500)
    private String align;

    /**
     * 开启文本
     */
    @Column(name = "on_text", columnDefinition = "开启文本", length = 500)
    private String onText;


    /**
     * 开启文本值
     */
    @Column(name = "on_value", columnDefinition = "开启文本值", length = 500)
    private String onValue;


    /**
     * 关闭文本
     */
    @Column(name = "off_text", columnDefinition = "关闭文本", length = 500)
    private String offText;


    /**
     * 关闭文本值
     */
    @Column(name = "off_value", columnDefinition = "关闭文本值", length = 500)
    private String offValue;

    /**
     * 选中的
     */
    @Column(name = "checked", columnDefinition = "选中的")
    private Integer checked;

    /**
     * 排序
     */
    @Column(name = "sortable", columnDefinition = "排序")
    private Integer sortable;

}
