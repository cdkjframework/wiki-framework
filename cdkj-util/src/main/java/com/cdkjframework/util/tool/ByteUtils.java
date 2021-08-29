package com.cdkjframework.util.tool;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: ByteUtils
 * @Description: 字节工具
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ByteUtils {

    /**
     * 将字节转换为整数
     *
     * @param bytes 字节
     * @return 返回整数
     */
    public static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }
}
