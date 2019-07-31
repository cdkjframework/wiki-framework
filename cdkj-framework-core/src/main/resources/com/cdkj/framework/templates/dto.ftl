package ${packageName}.dto;

import com.cdkj.framework.entity.RequestEntity;

<#list leading as item>
import ${item};
</#list>

import javax.persistence.*;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 */

public class ${className}Dto extends RequestEntity {

    private static final long serialVersionUID = ${serialVersionUID};

<#list children as item>
    <#if item.columnShow>
    /**
     * ${item.columnDescription}
     */
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>

<#list children as item>
    <#if item.columnShow>
    public ${item.dataType} get${item.funColumnName}() {
        return [this]${item.columnName};
    }

    public void set${item.funColumnName}(${item.dataType} ${item.columnName}) {
        [this]${item.columnName} = ${item.columnName};
    }

    </#if>
</#list>
}
