/**
* @ProjectName: ${projectName}
* @Package: ${packageName}
* @ClassName: ${className}
* @Description: ${description}
* @Author: ${author}
* @Version: 1.0
*/
export interface ${className}<T = any> {
<#list children as item>
    <#if item.columnShow && item.isExtension==0>
        /**
        * ${item.columnDescription}
        */
        private ${item.dataType} ${item.columnName};
    </#if>
</#list>
}
