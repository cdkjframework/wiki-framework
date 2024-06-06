package com.cdkjframework.util.encrypts;

import com.cdkjframework.config.CustomConfig;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.mapper.FieldMappingUtils;
import com.cdkjframework.util.tool.mapper.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.encrypts
 * @ClassName: DesensitizationUtils
 * @Description: 脱敏加密类
 * @Author: xiaLin
 * @Date: 2022/10/5 11:04
 * @Version: 1.0
 */
@Component
public class DesensitizationUtils {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(DesensitizationUtils.class);

    /**
     * 脱敏加密
     *
     * @param keywords 关键词
     * @return 返回结果
     */
    public static String encode(String keywords) {
        StringBuffer buffer = new StringBuffer();
        char[] chars = keywords.toCharArray();
        CustomConfig config = new CustomConfig();
        AesUtils aes = new AesUtils(config);
        for (char c :
                chars) {
            try {
                buffer.append(AesUtils.base64Encode(String.valueOf(c)))
                        .append(StringUtils.COMMA);
            } catch (Exception e) {
                logUtils.error(e);
            }
        }
        // 返回结果
        return buffer.toString();
    }

    /**
     * 脱敏加密
     *
     * @param keywords 关键词
     * @return 返回结果
     */
    public static <T> void encode(T keywords) {
        encode(keywords, new String[IntegerConsts.ZERO]);
    }

    /**
     * 脱敏加密
     *
     * @param keywords 关键词
     * @param fields   字段
     * @return 返回结果
     */
    public static <T> void encode(T keywords, String... fields) {
        entityProcess(keywords, true, fields);
    }

    /**
     * 脱敏解密
     *
     * @param keywords 关键词
     * @return 返回密码结果
     */
    public static String decrypt(String keywords) {
        String[] keys = keywords.split(StringUtils.COMMA);
        StringBuffer buffer = new StringBuffer();
        CustomConfig config = new CustomConfig();
        AesUtils aes = new AesUtils(config);
        for (String key :
                keys) {
            try {
                buffer.append(AesUtils.base64Decrypt(key));
            } catch (Exception e) {
                logUtils.error(e);
            }
        }
        // 返回结果
        return buffer.toString();
    }

    /**
     * 脱敏解密
     *
     * @param keywords 关键词
     * @return 返回密码结果
     */
    public static <T> void decrypt(T keywords) {
        decrypt(keywords, new String[IntegerConsts.ZERO]);
    }

    /**
     * 脱敏解密
     *
     * @param keywords 关键词
     * @param fields   字段
     * @return 返回密码结果
     */
    public static <T> void decrypt(T keywords, String... fields) {
        entityProcess(keywords, false, fields);
    }

    /**
     * 实体处理
     *
     * @param keywords 实体
     * @param isEncode 是否加密
     * @param fields   字段
     * @param <T>      实体类型
     */
    private static <T> void entityProcess(T keywords, boolean isEncode, String... fields) {
        List<Field> fieldList = ReflectionUtils.getDeclaredFields(keywords);
        if (fields == null) {
            fields = new String[IntegerConsts.ZERO];
        }
        for (Field field :
                fieldList) {
            Optional<String> optional = Arrays.stream(fields).filter(f -> f.equals(field.getName()))
                    .findFirst();
            if (fields.length > IntegerConsts.ZERO && !optional.isPresent()) {
                continue;
            }
            Object value = ReflectionUtils.getFieldValue(field, keywords);
            if (StringUtils.isNullAndSpaceOrEmpty(value) || !value.getClass().equals(String.class)) {
                continue;
            }
            String keyValue;
            if (isEncode) {
                keyValue = encode(value.toString());
            } else {
                keyValue = decrypt(value.toString());
            }
            ReflectionUtils.setFieldValue(keywords, field, keyValue);
        }
    }

    public static void main(String[] args) {
        UserEntity user = new UserEntity();
        user.setCellphone("15928658740");
        System.out.println("user:" + user.getCellphone());
        encode(user);
        System.out.println("encodeUser:" + user.getCellphone());
        decrypt(user);
        System.out.println("decryptUser:" + user.getCellphone());
    }

}
