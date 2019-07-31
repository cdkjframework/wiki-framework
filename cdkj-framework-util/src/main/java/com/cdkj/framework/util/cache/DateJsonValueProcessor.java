package com.cdkj.framework.util.cache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @ProjectName: com.cdkj.framework.webcode
 * @Package: com.cdkj.framework.core.util.cache
 * @ClassName: DateJsonValueProcessor
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class DateJsonValueProcessor
//        implements JsonValueProcessor
{
    public static final String Default_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private DateFormat dateFormat;

    public DateJsonValueProcessor(String datePattern) {
        try {
            dateFormat = new SimpleDateFormat(datePattern);
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
        }
    }

//    @Override
//    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
//        return process(value);
//    }
//
//    @Override
//    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
//        return process(value);
//    }
//
//    private Object process(Object value) {
//        return dateFormat.format((Timestamp) value);
//    }
}
