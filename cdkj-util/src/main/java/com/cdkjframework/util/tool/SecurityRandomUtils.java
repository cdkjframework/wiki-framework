package com.cdkjframework.util.tool;

import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.tool
 * @ClassName: SecurityRandomUtils
 * @Description: 安全随机数工具类
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class SecurityRandomUtils {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(SecurityRandomUtils.class);

    /**
     * 安全随机数生成
     */
    private static SecureRandom secureInstance = null;

    /**
     * 位置
     */
    private static final int WIND_POS = (2 << 9) >> 3;

    /**
     * 字节数
     */
    private static final int NUMBER_BYTES = 10;

    /**
     * SHA标记
     */
    private static final String SHA_TAGS = "SHA1PRNG";

    /**
     * 锁
     */
    private static final Object LOCKER = new Object();

    /**
     * 类状态加载
     */
    static {
        initialSecurityInstance();
    }

    /**
     * 初始方法
     */
    public static void initialSecurityInstance() {
        try {
            secureInstance = SecureRandom.getInstance(SHA_TAGS);
        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
        }
    }

    /**
     * 生成随机数字
     *
     * @return 返回结果
     */
    public static long generateRandomDigits() {
        try {
            byte[] bytes = new byte[WIND_POS];
            secureInstance.nextBytes(bytes);

            synchronized (LOCKER) {
                byte[] seedNums = secureInstance.generateSeed(NUMBER_BYTES);
                secureInstance.setSeed(seedNums);
            }

        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
        }
        return mergeToBitsDuck(secureInstance.nextLong());
    }

    /**
     * 生成随机数字
     *
     * @return 返回结果
     */
    public static long generateRandomDigits(int bytesNumber, int seed, String algorithm) {
        try {
            SecureRandom customerRandom;

            synchronized (LOCKER) {
                if (StringUtils.isNullAndSpaceOrEmpty(algorithm)) {
                    customerRandom = SecureRandom.getInstance(algorithm);
                } else {
                    customerRandom = secureInstance;
                }
                byte[] bytes = new byte[bytesNumber];

                customerRandom.nextBytes(bytes);
                byte[] seedNums = customerRandom.generateSeed(seed);
                customerRandom.setSeed(seedNums);
            }

        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
        }

        // 返回整数
        return mergeToBitsDuck(secureInstance.nextLong());
    }

    /**
     * Handling fixed sign bits by bit operations
     *
     * @param n 数
     * @return 返回结果
     */
    private static long mergeToBitsDuck(long n) {
        return n & 0x7fffffffffffffffL;
    }
}
