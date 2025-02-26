package com.cdkjframework.mybatispro.core.conditions.segments;


import com.cdkjframework.mybatispro.core.conditions.ISqlSegment;

import java.util.ArrayList;
import java.util.List;

import static com.cdkjframework.constant.StringConstant.EMPTY;
import static com.cdkjframework.constant.StringConstant.SPACE;
import static com.cdkjframework.mybatispro.core.enums.SqlKeyword.ORDER_BY;
import static com.cdkjframework.util.tool.StringUtils.COMMA;
import static java.util.stream.Collectors.joining;

/**
 * Order By SQL 片段
 *
 * @author miemie
 * @since 2018-06-27
 */
public class OrderBySegmentList extends AbstractISegmentList {

    @Override
    protected boolean transformList(List<ISqlSegment> list, ISqlSegment firstSegment, ISqlSegment lastSegment) {
        list.remove(0);
        final List<ISqlSegment> sqlSegmentList = new ArrayList<>(list);
        list.clear();
        list.add(() -> sqlSegmentList.stream().map(ISqlSegment::getSqlSegment).collect(joining(SPACE)));
        return true;
    }

    @Override
    protected String childrenSqlSegment() {
        if (isEmpty()) {
            return EMPTY;
        }
        return this.stream().map(ISqlSegment::getSqlSegment).collect(joining(COMMA, SPACE + ORDER_BY.getSqlSegment() + SPACE, EMPTY));
    }
}
