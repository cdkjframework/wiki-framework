package ${packageName}.entity;

import com.cdkj.framework.entity.BaseEntity;

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

@Entity
@Table(name = "${table}", catalog = "${dataBase}")
public class ${className}Entity extends BaseEntity {

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
    <#if item.columnKey>
    @Id
    <#else>
    @Basic
    </#if>
    @Column(name = "${item.tableColumnName}", columnDefinition="${item.columnDescription}", nullable = ${item.nullable}, length=${item.length})
    public ${item.dataType} get${item.funColumnName}() {
        return [this]${item.columnName};
    }

    public void set${item.funColumnName}(${item.dataType} ${item.columnName}) {
        [this]${item.columnName} = ${item.columnName};
    }

    </#if>
</#list>
}
