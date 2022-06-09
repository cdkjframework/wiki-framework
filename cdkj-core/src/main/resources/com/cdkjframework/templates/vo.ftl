package ${packageName}.vo;

import com.cdkjframework.entity.base.BaseVo;
<#list leading as item>
import ${item};
</#list>

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(" ${description}")
public class ${className}Vo extends BaseVo {

    /**
    * 序列版本UID
    */
    private static final long serialVersionUID = ${serialVersionUID};

<#list children as item>
    <#if item.columnShow && item.isExtension==0>
    /**
     * ${item.columnDescription}
     */
    @ApiModelProperty("${item.columnDescription}")
    private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
