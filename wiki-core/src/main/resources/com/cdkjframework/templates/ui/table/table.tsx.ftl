import {TableColumns} from "@/components/layout/table/table-columns";
import {CUSTOM, FORMAT, FORMAT_DATETIME, NUMBER} from "@/utils/enums/type-enums";

export const ${classLowName}Columns: TableColumns[] = [
{
label: '序号',
type: NUMBER
},
<#list children as item>
    {
    label: '${item.columnDescription}：',
    key: '${item.columnName}',
    type: TEXT,
    width: 100
    },
</#list>
{
label: '操作',
type: CUSTOM,
align: 'center',
fixed: 'right',
width: 200,
buttons: [
{method: 'edit', text: '编辑'},
{method: 'delete', text: '删除'}
]
}
]