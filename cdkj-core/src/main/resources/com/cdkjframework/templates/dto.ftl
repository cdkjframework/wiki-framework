package ${packageName}.dto;

import com.cdkjframework.entity.base.BaseDto;

import java.io.Serializable;
<#list leading as item>
import ${item};
</#list>

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ${className}Dto extends BaseDto implements Serializable {

    /**
    * 序列版本UID
    */
    private static final long serialVersionUID = ${serialVersionUID};
<#list children as item>
    <#if item.columnShow && item.isExtension==0>
      /**
      * ${item.columnDescription}
      */
      public static final String ${item.tableColumnNameUpperCase} = "${item.columnName}";
    </#if>
</#list>

<#list children as item>
    <#if item.columnShow && item.isExtension==0>
    /**
     * ${item.columnDescription}
     */
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
