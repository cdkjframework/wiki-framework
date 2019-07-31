<Worksheet ss:Name="${sheetName}">
    <Table ss:ExpandedColumnCount="${columnNum}" ss:ExpandedRowCount="${rowNum}" x:FullColumns="1"
           x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
    <#list rows as row>
    <Row>
        <#list row.cells as cell>
        <Cell <#if cell.style?? >ss:StyleID="${cell.style}"</#if>>
            <Data <#if cell.type??>ss:Type="${cell.type}"</#if>>${cell.text!}</Data>
        </Cell>
        </#list>
    </Row>
    </#list>

    </Table>
</Worksheet>