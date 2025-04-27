import {Form} from "@/components/layout/form/form";
import {TEXT} from "@/utils/enums/type-enums";

export const ${classLowName}Form: Form[] = [{
label: null, card: [
<#list children as item>
    {
    label: '${item.columnDescription}：',
    key: '${item.columnName}',
    type: TEXT,
    width: 100,
    options: [],
    span: 8,
    placeholder: '输入${item.columnDescription}'
    },
</#list>
]
}]