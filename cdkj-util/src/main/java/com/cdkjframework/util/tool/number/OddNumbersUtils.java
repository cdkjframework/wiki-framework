package com.cdkjframework.util.tool.number;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.cache.RedisUtils;
import com.cdkjframework.util.date.DateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: common-core
 * @Package: com.cdkjframework.util.tool
 * @ClassName: OddNumbersUtils
 * @Description: 单号生成
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class OddNumbersUtils {


    /**
     * 前缀常量
     */
    private static final String DATE_PATTERN = "yyMMdd";

    /**
     * 单号生成主键 KEY
     */
    private static final String ODD_NUMBER_KEY = "odd_Number_Key";

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(OddNumbersUtils.class);

    /**
     * 生成单据号
     *
     * @param prefix 前缀
     * @param length 后缀长度
     * @return 返回单据结果
     */
    public static String generateDocumentNumber(String prefix, int length) throws GlobalException {
        //字母+日期+5位流水号
        //日期：年最后2位+月+天
        //客户代码：3位数字
        //采购订单：CG00117072500001
        //销售订单：XS00117072500001
        return generate(prefix, length, 0, true);
    }


    /**
     * 生成单据号（无日期）
     *
     * @param prefix 前缀
     * @param length 后缀长度
     * @return 返回单据结果
     * @throws GlobalException 异常信息
     */
    public static String generateNoDateNumber(String prefix, int length) throws GlobalException {
        return generate(prefix, length, 0, false);
    }


    /**
     * 单号生成
     *
     * @param prefix 前缀
     * @param length 长度
     * @param init   初始值
     * @param isDate 是否加时间
     * @return 返回单号
     * @throws GlobalException 异常信息
     */
    private static String generate(String prefix, int length, int init, boolean isDate) throws GlobalException {
        final int error = 5;
        //生成单号
        String number = autoRetryGenerate(prefix, length, init, error, isDate);

        //验证单号
        if (StringUtils.isNullAndSpaceOrEmpty(number)) {
            throw new GlobalException(String.format("获取单号失败[%s]", prefix));
        }

        //返回单号结果
        return number;
    }

    /**
     * 自动生成并重试
     *
     * @param prefix 前缀
     * @param length 长度
     * @param init   初始值
     * @param err    最多生成次数
     * @param isDate 是否加时间
     * @return 返回结果
     */
    private static String autoRetryGenerate(String prefix, int length, int init, int err, boolean isDate) {
        int error = 0;
        while (error < err) {
            try {
                // 生成前缀信息
                StringBuffer buffer = new StringBuffer(prefix);

                String date = DateUtils.format(new Date(), DATE_PATTERN);
                if (isDate) {
                    buffer.append(date);
                }
                String key = ODD_NUMBER_KEY + date;
                //设置过期时间
                RedisUtils.syncExpire(key, 1 * 24 * 60 * 60);

                //获取单号记录
                long target = RedisUtils.syncIncr(key, init);
                if (target > 0) {
                    // 生成单号
                    String number = String.valueOf(target);
                    if (length > number.length()) {
                        for (int i = 0; i < (length - number.length()); i++) {
                            buffer.append("0");
                        }
                    }
                    return buffer.append(target).toString();
                } else {
                    TimeUnit.MILLISECONDS.sleep(200);
                    throw new GlobalException("生成单号失败");
                }
            } catch (Exception ex) {
                error++;
                String errorMessage = String.format("第%s次获取单号失败[%s]，%s", String.valueOf(error), prefix, ex.getMessage());
                logUtils.error(errorMessage);
            }
        }

        // 返回 null 说明生成单号失败
        return null;
    }
}
