package com.cdkj.framework.util.tool;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkj.framework.core.util.tool
 * @ClassName: GzipUtil
 * @Description: Gzip 加密
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class GzipUtil {

    /**
     * Gzip 加密
     *
     * @param param   参数
     * @param charset 编码
     * @return 返回结果
     * @throws IOException 异常信息
     */
    public static String gZip(String param, String charset) throws IOException {
        // gzip 加密
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 创建 GZIPOutputStream 对象
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gzipOutputStream.write(param.getBytes(charset));
        //返回结果
        return byteArrayOutputStream.toString(charset);
    }
}
