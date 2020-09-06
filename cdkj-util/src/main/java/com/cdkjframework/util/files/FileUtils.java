package com.cdkjframework.util.files;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.files
 * @ClassName: FileUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class FileUtils {

    /**
     * 单位 KB
     */
    private static final int KB_SIZE = 1024;

    /**
     * 单位 MB
     */
    private static final int MB_SIZE = 1024 * KB_SIZE;

    /**
     * 单位 GB
     */
    private static final int GB_SIZE = 1024 * MB_SIZE;
    /**
     * 加密解密秘钥
     */
    private static final int encAndDecKey = 0x99;
    /**
     * 文件字节内容
     */
    private static int dataOfFile = 0;

    /**
     * 日志
     */
    private static LogUtils logUtil = LogUtils.getLogger(FileUtils.class);

    /**
     * 保存文件
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    public static boolean saveFile(InputStream inputStream, String fileName) throws GlobalException {
        return saveFile(inputStream, StringUtils.Empty, fileName);
    }

    /**
     * 保存文件
     *
     * @param inputStream 文件流
     * @param fileName    文件路径
     * @param catalog     自定义文件路径
     * @param fileName    文件名称
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    public static boolean saveFile(InputStream inputStream, String catalog, String fileName) throws GlobalException {
        return saveFile(inputStream, StringUtils.Empty, catalog, fileName);
    }

    /**
     * 保存文件
     *
     * @param character     文件内容
     * @param directoryPath 文件路径
     * @param catalog       自定义文件路径
     * @param fileName      文件名称
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    public static boolean saveFile(String character, String directoryPath, String catalog, String fileName) throws GlobalException {
        InputStream inputStream = new ByteArrayInputStream(character.getBytes());
        return saveFile(inputStream, directoryPath, catalog, fileName);
    }

    /**
     * 保存文件
     *
     * @param inputStream   文件流
     * @param directoryPath 文件路径
     * @param catalog       自定义文件路径
     * @param fileName      文件名称
     * @return 返回结果
     * @throws GlobalException 异常信息
     */
    public static boolean saveFile(InputStream inputStream, String directoryPath, String catalog, String fileName) throws GlobalException {
        OutputStream outputStream = null;
        try {
            directoryPath = splicingPath(directoryPath, catalog);

            //读取文件路径
            File file = new File(directoryPath);
            if (!file.exists()) {
                file.mkdirs();
                fileUnModified(file);
            }

            // 1K的数据缓冲
            byte[] bytes = new byte[IntegerConsts.BYTE_LENGTH];
            // 读取到的数据长度
            int len;

            String name = file.getPath() + File.separator + fileName;
            outputStream = new FileOutputStream(name, true);
            // 开始读取
            while ((len = inputStream.read(bytes)) != IntegerConsts.MINUS_ONE) {
                outputStream.write(bytes, IntegerConsts.ZERO, len);
            }
            return true;
        } catch (Exception ex) {
            logUtil.error(ex);
            throw new GlobalException(ex.getMessage());
        } finally {
            // 完毕，关闭所有链接
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                logUtil.error(e);
                throw new GlobalException(e.getMessage());
            }
        }
    }

    /**
     * 读取文件
     *
     * @param directoryPath 目录
     * @param catalog       自定义路径
     * @param fileName      文件史
     * @return 返回结果
     * @throws GlobalException 仅供异常信息
     * @throws IOException     异常信息
     */
    public static byte[] readFile(String directoryPath, String catalog, String fileName) throws GlobalException, IOException {

        InputStream inputStream = null;
        byte[] byteList = null;
        try {
            //拼接路径
            directoryPath = splicingPath(directoryPath, catalog) + fileName;

            // 读取文件到流
            inputStream = new FileInputStream(directoryPath);
            //创建文件字段长度
            byteList = new byte[inputStream.available()];
            //将文件流读取为字节
            inputStream.read(byteList);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GlobalException(ex.getMessage());
        } finally {
            //关闭文件流
            inputStream.close();
        }

        //返回结果
        return byteList;
    }

    /**
     * 删除指定目录、时间之前的文件
     *
     * @param date    时间
     * @param catalog 目录
     * @return
     */
    public static void beforeDeleteSpecifiedTimeFile(LocalDateTime date, String catalog) throws GlobalException {
        beforeDeleteSpecifiedTimeFile(date, catalog, StringUtils.Empty, null);
    }

    /**
     * 删除指定目录、时间之前的文件
     *
     * @param date          时间
     * @param catalog       目录
     * @param directoryPath 路径（可为空）
     * @param excludeFiles  不删除文件集合（可为空）
     * @return
     */
    public static void beforeDeleteSpecifiedTimeFile(LocalDateTime date, String catalog, String directoryPath, List<String> excludeFiles) throws GlobalException {
        try {
            //获取完整路径
            String tempFolder = splicingPath(directoryPath, catalog);
            //读取文件路径
            File folder = new File(tempFolder);
            //获取全部文件
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                //验证是否为不删除文件
                if (excludeFiles != null && excludeFiles.size() > IntegerConsts.ZERO && excludeFiles.contains(file.getName())) {
                    continue;
                }
                //判断文件是否在指定日间之前生成
                LocalDateTime dateTime = LocalDateUtils.timestampToLocalDateTime(file.lastModified());
                if (LocalDateUtils.greater(dateTime, date)) {
                    file.delete();
                }
            }
        } catch (Exception ex) {
            logUtil.error(ex);
        }
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return 返回结果
     */
    public static String getFileSuffix(String fileName) {
        String suffix = StringUtils.Empty;
        if (StringUtils.isNullAndSpaceOrEmpty(fileName)) {
            return suffix;
        }
        //获取后缀
        suffix = fileName.substring(fileName.lastIndexOf(StringUtils.POINT));
        //返回结果
        return suffix;
    }

    /**
     * 获取文件名称
     *
     * @param filePath 文件路径
     * @return 返回结果
     */
    public static String getFileName(String filePath) {
        String fileName = StringUtils.Empty;
        if (StringUtils.isNullAndSpaceOrEmpty(filePath)) {
            return fileName;
        }
        //网络地址
        final String splitValue = "/";
        if (filePath.contains(splitValue)) {
            fileName = filePath.substring(filePath.lastIndexOf(splitValue) + IntegerConsts.ONE);
        } else {
            //目录地址
            fileName = filePath.substring(filePath.lastIndexOf("\\") + IntegerConsts.ONE);
        }
        //返回结果
        return fileName;
    }

    /**
     * 拼接路径
     *
     * @param directoryPath 路径
     * @param catalog       目录
     * @return 返回结果
     * @throws FileNotFoundException 异常信息
     */
    private static String splicingPath(String directoryPath, String catalog) throws FileNotFoundException {
        //验证路径是否存在
        if (StringUtils.isNullAndSpaceOrEmpty(directoryPath)) {
            directoryPath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getPath();
        }
        //验证自定义路径是否有
        if (!StringUtils.isNullAndSpaceOrEmpty(catalog)) {
            directoryPath += catalog;
        }
        //返回结果
        return directoryPath;
    }

    /**
     * 获取到目录
     *
     * @return 返回结果
     */
    public static String getPath(String path) {
        final String os = HostUtils.getOs();
        String division = "/";
        final String osKey = "win";
        if (os.startsWith(osKey)) {
            division = "\\";
        }
        String classPath;
        try {
            classPath = ResourceUtils
                    .getFile("classpath:")
                    .getPath()
                    .replace("target" + division + "classes", "src" + division + "main" + division + "java" + division);
            path = path
                    .replace("[", "")
                    .replace("]", "")
                    .replace(".", division);
            classPath += path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            classPath = "";
        }

        //返回结果
        return classPath;
    }

    /**
     * 文件不能修改，执行、只读取
     *
     * @param file 文件
     */
    public static void fileUnModified(File file) {
        if (file != null) {
            file.setWritable(false);
            file.setExecutable(false);
            file.setReadOnly();
        }
    }


    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 返回大小
     */
    public static long getFileSize(File file) {
        long size = IntegerConsts.ZERO;
        if (file.exists()) {
            if (!file.isDirectory()) {
                size = file.length();
            } else {
                size += getDirSize(file);
            }
        }
        return size;
    }

    /**
     * 多聚目录大小
     *
     * @param file 文件
     * @return 返回大小
     */
    public static long getDirSize(File file) {
        long dirSize = IntegerConsts.ZERO;
        if (file != null) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File fl : files) {
                        if (fl.isDirectory()) {
                            dirSize += getDirSize(fl);
                        } else {
                            dirSize += fl.length();
                        }
                    }
                }
            }
        }
        return dirSize;
    }

    /**
     * byte字节转换为字符串
     *
     * @param fileBytes 文件 byte
     * @return 返回字符编码
     */
    public static String byteToString(long fileBytes) {
        StringBuffer byteStr = new StringBuffer();
        BigDecimal fullSize = new BigDecimal(fileBytes);
        //mb
        BigDecimal mbSize = new BigDecimal(MB_SIZE);
        float gbSize = fullSize.divide(new BigDecimal(GB_SIZE), IntegerConsts.TWO, BigDecimal.ROUND_HALF_UP).floatValue();
        if (gbSize > IntegerConsts.ONE) {
            byteStr.append(gbSize).append("GB");
        } else {
            float dvSize = fullSize.divide(mbSize, IntegerConsts.TWO, BigDecimal.ROUND_HALF_UP).floatValue();
            if (dvSize > IntegerConsts.ONE) {
                byteStr.append(dvSize).append("MB");
            } else {
                //kb显示
                BigDecimal kbSize = new BigDecimal(KB_SIZE);
                byteStr.append(fullSize.divide(kbSize, IntegerConsts.TWO, BigDecimal.ROUND_HALF_UP).floatValue()).append("KB");
            }
        }
        return byteStr.toString();
    }

    /**
     * 加密文件
     *
     * @param sourceFile  源文件
     * @param encryptFile 加密后文件
     * @throws Exception 异常
     */
    public static void encrypt(File sourceFile, File encryptFile) throws Exception {
        if (!sourceFile.exists()) {
            return;
        }

        InputStream inputStream = new FileInputStream(sourceFile);
        encrypt(inputStream, encryptFile);
    }

    /**
     * 文件加密
     *
     * @param inputStream 文件流
     * @param encryptFile 加密后文件
     * @throws Exception 异常信息
     */
    public static void encrypt(InputStream inputStream, File encryptFile) throws Exception {
        if (!encryptFile.exists()) {
            encryptFile.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(encryptFile);
        while ((dataOfFile = inputStream.read()) > IntegerConsts.MINUS_ONE) {
            outputStream.write(dataOfFile ^ encAndDecKey);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 解密文件
     *
     * @param sourceFile  源文件
     * @param decryptFile 解密文件
     * @throws Exception 异常信息
     */
    public static void decrypt(File sourceFile, File decryptFile) throws Exception {
        if (!sourceFile.exists()) {
            return;
        }
        if (!decryptFile.exists()) {
            decryptFile.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(decryptFile);
        decrypt(sourceFile, outputStream);
    }

    /**
     * 解密文件
     *
     * @param sourceFile   源文件
     * @param outputStream 解密文件流
     * @throws Exception 异常信息
     */
    private static void decrypt(File sourceFile, OutputStream outputStream) throws Exception {
        InputStream inputStream = new FileInputStream(sourceFile);

        while ((dataOfFile = inputStream.read()) > IntegerConsts.MINUS_ONE) {
            outputStream.write(dataOfFile ^ encAndDecKey);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }
}
