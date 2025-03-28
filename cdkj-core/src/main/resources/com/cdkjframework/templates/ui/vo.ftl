import type {BaseModel} from "@/model/BaseModel";
/**
* @ClassName: ${className}
* @Description: ${description}
* @Author: ${author}
* @Version: 1.0
*/
export interface ${className} extends BaseModel<${className}> {
<#list children as item>
    <#if item.columnShow && item.isExtension==0>
        /**
        * ${item.columnDescription}
        */
        ${item.columnName}?: ${item.htmlDataType};
    </#if>
</#list>
}
