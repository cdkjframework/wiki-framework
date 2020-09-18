package com.cdkjframework.util.tool;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.util.log.LogUtils;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @ProjectName: HT-OMS-Project-OMS
 * @Package: com.cdkjframework.core.util.tool
 * @ClassName: GzipUtil
 * @Description: Gzip 加密
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class GzipUtils {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(GzipUtils.class);

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


    /**
     * 使用gzip压缩字符串
     *
     * @param param 要压缩的字符串
     * @return
     */
    public static String compress(String param) {
        if (StringUtils.isNullAndSpaceOrEmpty(param)) {
            return param;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(param.getBytes());
        } catch (IOException e) {
            logUtils.error(e);
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    logUtils.debug(e.getMessage());
                }
            }
        }
        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * 使用gzip解压缩
     *
     * @param compressedStr 压缩字符串
     * @return 返回解密字符
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream inGzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            inGzip = new GZIPInputStream(in);
            byte[] buffer = new byte[IntegerConsts.BYTE_LENGTH];
            int offset;
            while ((offset = inGzip.read(buffer)) != IntegerConsts.MINUS_ONE) {
                out.write(buffer, IntegerConsts.ZERO, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            logUtils.error(e);
        } finally {
            if (inGzip != null) {
                try {
                    inGzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return decompressed;
    }

}
