package com.cdkjframework.util.make;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.make
 * @ClassName: IdCardUtils
 * @Description: 身份证号生成示例
 * @Author: xiaLin
 * @Date: 2023/6/9 15:16
 * @Version: 1.0
 */
public class IdCardUtils {
    public static void main(String[] args) {
//        String idNumber = generateIdCard();
//        System.out.println("生成的身份证号码：" + idNumber);
    }

    /**
     * 生成身份证号
     *
     * @return 返回结果
     */
    public static String generateIdCard() {
        // 生成身份证前17位
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append("51162219860618");
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int num = random.nextInt(10);
            idBuilder.append(num);
        }

        // 计算身份证最后一位校验码
        String id17 = idBuilder.toString();
        char[] chars = id17.toCharArray();
        int[] weights = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (chars[i] - '0') * weights[i];
        }
        int checkCode = sum % 11;
        char[] checkCodes = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        char lastChar = checkCodes[checkCode];

        // 构建完整的18位身份证号码
        idBuilder.append(lastChar);
        return idBuilder.toString();
    }
}
