package ${packageName}.entity;

import com.cdkjframework.entity.BaseEntity;

import java.io.Serializable;
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
 */

@Data
<#if jpa>
@Entity
</#if>
@EqualsAndHashCode(callSuper = false)
@Table(name = "${table}", catalog = "${dataBase}")
public class ${className}Entity extends BaseEntity implements Serializable {

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
      @Column(name = "${item.tableColumnName}", columnDefinition = "${item.columnType}(${item.length})")
      </#if>
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
