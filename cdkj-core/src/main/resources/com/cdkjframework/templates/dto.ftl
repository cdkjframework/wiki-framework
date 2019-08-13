package ${packageName}.dto;

import com.cdkjframework.entity.RequestEntity;
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
 */

@Getter
@Setter
@ToString
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
}
