package ${packageName}.entity;

import com.cdkjframework.entity.BaseEntity;
<#list leading as item>
import ${item};
</#list>

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 * @Entity
 */

@Data
<#if jpa>
@Entity
</#if>
@EqualsAndHashCode(callSuper = false)
@Table(name = "${table}", catalog = "${dataBase}")
public class ${className}Entity extends BaseEntity {

    /**
    * 序列版本UID
    */
    private static final long serialVersionUID = ${serialVersionUID};

<#list children as item>
    <#if item.columnShow && item.isExtension==0>
    /**
     * ${item.columnDescription}
     */
      <#if jpa>
      @Column(name = "${item.tableColumnName}")
      </#if>
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
