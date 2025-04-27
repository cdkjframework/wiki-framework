package ${packageName}.vo;

import com.cdkjframework.entity.base.BaseVo;

import java.io.Serializable;
<#list leading as item>
import ${item};
</#list>

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "${description}")
@EqualsAndHashCode(callSuper = false)
public class ${className}Vo extends BaseVo implements Serializable {

    /**
    * 序列版本UID
    */
    private static final long serialVersionUID = ${serialVersionUID};

<#list children as item>
    <#if item.columnShow && item.isExtension==0>
    /**
     * ${item.columnDescription}
     */
    @Schema(name = "${item.columnDescription}")
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
