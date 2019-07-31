package com.cdkj.framework.util.files;

import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: com.cdkj.framework.QRcode
 * @Package: com.cdkj.framework.core.util.files
 * @ClassName: NotepadUtil
 * @Description: 计事本操作
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class NotepadUtil {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger("NotepadUtil");

    /**
     * 日期格式
     */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 时间格式
     */
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 保存数据到文件
     *
     * @param builder 写入内容
     * @return 返回布尔值
     */
    public static boolean saveFile(StringBuilder builder) {
        return saveFile(builder, true);
    }

    /**
     * 保存数据到文件
     *
     * @param builder 写入内容
     * @param append  是否追加
     * @return 返回布尔值
     */
    public static boolean saveFile(StringBuilder builder, boolean append) {
        return saveFile(builder, append, true);
    }

    /**
     * 保存数据到文件
     *
     * @param fileName 文件名
     * @param builder  写入内容
     * @return 返回布尔值
     */
    public static boolean saveFile(String fileName, StringBuilder builder) {
        return saveFile("", fileName, builder);
    }


    /**
     * 保存数据到文件
     *
     * @param builder 写入内容
     * @param append  是否追加
     * @param newline 是否换行
     * @return 返回布尔值
     */
    public static boolean saveFile(StringBuilder builder, boolean append, boolean newline) {
        return saveFile("", "", "", builder, append, newline);
    }

    /**
     * 保存数据到文件
     *
     * @param directoryPath 文件路径
     * @param fileName      文件名
     * @param builder       写入内容
     * @return 返回布尔值
     */
    public static boolean saveFile(String directoryPath, String fileName, StringBuilder builder) {
        return saveFile(directoryPath, "", fileName, builder);
    }

    /**
     * 保存数据到文件
     *
     * @param directoryPath 文件路径
     * @param catalog       文件目录（自字义路径）
     * @param fileName      文件名
     * @param builder       写入内容
     * @return 返回布尔值
     */
    public static boolean saveFile(String directoryPath, String catalog, String fileName, StringBuilder builder) {
        return saveFile(directoryPath, catalog, fileName, builder, true, true);
    }

    /**
     * 保存数据到文件
     *
     * @param directoryPath 文件路径
     * @param catalog       文件目录（自字义路径）
     * @param fileName      文件名
     * @param builder       写入内容
     * @param append        是否追加
     * @param newline       是否换行
     * @return 返回布尔值
     */
    public static boolean saveFile(String directoryPath, String catalog, String fileName, StringBuilder builder, boolean append, boolean newline) {
        try {
            //验证路径是否存在
            if (StringUtil.isNullAndSpaceOrEmpty(directoryPath)) {
                directoryPath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
            }
            //验证自定义路径是否有
            if (!StringUtil.isNullAndSpaceOrEmpty(catalog)) {
                directoryPath += catalog;
            }
            //读取目录
            File file = new File(directoryPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //文件名
            if (StringUtil.isNullAndSpaceOrEmpty(fileName)) {
                fileName = "Notepad-" + dateFormat.format(new Date());
            }
            if (builder == null) {
                builder = new StringBuilder();
            }

            File logFile = new File(directoryPath, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(logFile, append);
            if (newline) {
                fileOutputStream.write("\r\n".getBytes());
                fileOutputStream.write((timeFormat.format(new Date()) + "  ").getBytes());
            }
            //写入数据
            fileOutputStream.write(builder.toString().getBytes());
            //写数据
            fileOutputStream.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            logUtil.error("写入 Notepad 出错：" + ex.getMessage());
            return false;
        }
    }
}