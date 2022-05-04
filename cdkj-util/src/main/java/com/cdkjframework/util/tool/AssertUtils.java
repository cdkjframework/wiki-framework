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
     * 对象断言
     *
     * @param t   实体
     * @param <T> 类型
     * @throws GlobalException 异常信息
     */
    public final static <T> void isEmptyMessage(T t) throws GlobalException {
        isEmptyMessage(t, IS_EMPTY_MESSAGE);
    }

    /**
     * 对象断言
     *
     * @param key 验证字段
     * @throws GlobalException 异常信息
     */
    public final static void isEmptyMessage(String key) throws GlobalException {
        isEmptyMessage(key, IS_EMPTY_MESSAGE);
    }

    /**
     * 对象断言
     *
     * @param key     验证字段
     * @param message 消息
     * @throws GlobalException 异常信息
     */
    public final static void isEmptyMessage(String key, String message) throws GlobalException {
        if (StringUtils.isNullAndSpaceOrEmpty(message)) {
            message = IS_EMPTY_MESSAGE;
        }
        if (StringUtils.isNullAndSpaceOrEmpty(key)) {
            throw new GlobalException(message);
        }
    }

    /**
     * 对象断言
     *
     * @param t   实体
     * @param <T> 类型
     * @throws GlobalException 异常信息
     */
    public final static <T> void isEmptyMessage(T t, String message) throws GlobalException {
        if (StringUtils.isNullAndSpaceOrEmpty(message)) {
            message = IS_EMPTY_MESSAGE;
        }

        if (t == null) {
            throw new GlobalException(message);
        }
    }

    /**
     * 断言数据集是为空
     *
     * @param list 数据集
     * @throws GlobalException 异常信息
     */
    public final static void isListEmpty(List<?> list) throws GlobalException {
        isListEmpty(list, IS_EMPTY_MESSAGE);
    }

    /**
     * 断言数据集是为空
     *
     * @param list 数据集
     * @throws GlobalException 异常信息
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
