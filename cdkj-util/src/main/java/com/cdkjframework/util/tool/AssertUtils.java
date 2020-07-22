package com.cdkjframework.util.tool;

import com.cdkjframework.exceptions.GlobalException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.util.tool
 * @ClassName: AssertUtils
 * @Description: 断言工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class AssertUtils {

    /**
     * 默认消息
     */
    private final static String IS_EMPTY_MESSAGE = "不能为空";

    /**
     * 断言数据集是为空
     *
     * @param list 数据集
     */
    public final static void isListEmpty(List<?> list) throws GlobalException {
        isListEmpty(list, IS_EMPTY_MESSAGE);
    }

    /**
     * 断言数据集是为空
     *
     * @param list 数据集
     */
    public final static void isListEmpty(List<?> list, String message) throws GlobalException {
        if (StringUtils.isNullAndSpaceOrEmpty(message)) {
            message = IS_EMPTY_MESSAGE;
        }

        if (CollectionUtils.isEmpty(list)) {
            throw new GlobalException(message);
        }
    }
}
