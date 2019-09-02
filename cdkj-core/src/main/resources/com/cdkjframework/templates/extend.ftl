package ${packageName}.entity.extend;

import ${packageName}.entity.${className}Entity;
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
public class ${className}ExtendEntity extends ${className}Entity {

private static final long serialVersionUID = ${serialVersionUID};
<#list children as item>
    <#if item.columnShow && item.isExtension==1>

        /**
        *
        * ${item.columnDescription}
        */
        private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
