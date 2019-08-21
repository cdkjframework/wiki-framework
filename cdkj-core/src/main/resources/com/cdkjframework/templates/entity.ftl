package ${packageName}.entity;

import com.cdkjframework.entity.BaseEntity;
<#list leading as item>
import ${item};
</#list>

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @ProjectName: ${projectName}
 * @Package: ${packageName}
 * @ClassName: ${className}
 * @Description: ${description}
 * @Author: ${author}
 * @Version: 1.0
 * @Entity
 */

@Getter
@Setter
@ToString
@Table(name = "${table}", catalog = "${dataBase}")
public class ${className}Entity extends ${className}ExtendsEntity {

    private static final long serialVersionUID = ${serialVersionUID};

<#list children as item>
    <#if item.columnShow>
    /**
     * ${item.columnDescription}
     */
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
