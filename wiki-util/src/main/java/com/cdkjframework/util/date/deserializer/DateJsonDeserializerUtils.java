package com.cdkjframework.util.date.deserializer;

import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.util.tool.deserializer
 * @ClassName: DateJsonDeserializerUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class DateJsonDeserializerUtils extends JsonDeserializer<Date> {

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(DateJsonDeserializerUtils.class);

    /**
     * 日志转换格式
     */
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 序列化
     *
     * @param jsonParser 对象
     * @param context    参数
     * @return 返回结果
     * @throws IOException             异常信息
     * @throws JsonProcessingException 异常信息
     */
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        try {
            if (jsonParser != null && !StringUtils.isNullAndSpaceOrEmpty(jsonParser.getText())) {
                return FORMAT.parse(jsonParser.getText());
            } else {
                return null;
            }
        } catch (Exception e) {
            logUtil.error(e.getCause(),e.getMessage());
        }

        return null;
    }
}
