package com.cdkjframework.util.files;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.HostUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private static final int KB_SIZE = IntegerConsts.BYTE_LENGTH;

    /**
     * 单位 MB
     */
    private static final int MB_SIZE = IntegerConsts.BYTE_LENGTH * KB_SIZE;

    /**
     * 单位 GB
     */
    private static final int GB_SIZE = IntegerConsts.BYTE_LENGTH * MB_SIZE;
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
                if (excludeFiles != null && excludeFiles.size() > 0 && excludeFiles.contains(file.getName())) {
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

    /**
     * 获取传输的 multipartFile，将输入流+文件名转成multipartFile文件，去调用feignClient
     *
     * @param inputStream 文件流
     * @param fileName    文件名
     * @return 返回多部分文件
     */
    public static MultipartFile buildMultipartFile(InputStream inputStream, String fileName) {
        FileItem fileItem = createFileItem(inputStream, fileName);
        //CommonsMultipartFile是feign对multipartFile的封装，但是要FileItem类对象
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }

    /**
     * 获取传输的 multipartFile，将输入流+文件名转成multipartFile文件，去调用feignClient
     *
     * @param outputStream 文件流
     * @param fileName     文件名
     * @return 返回多部分文件
     */
    public static MultipartFile buildMultipartFile(OutputStream outputStream, String fileName) {
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        FileItem fileItem = createFileItem(inputStream, fileName);
        //CommonsMultipartFile是feign对multipartFile的封装，但是要FileItem类对象
        return new CommonsMultipartFile(fileItem);
    }

    /**
     * FileItem类对象创建
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @return
     */
    private static FileItem createFileItem(InputStream inputStream, String fileName) {
        FileItemFactory factory = new DiskFileItemFactory(IntegerConsts.SIX, null);
        String textFieldName = "file";
        FileItem item = factory.createItem(textFieldName, "multipart/form-data", true, fileName);
        int bytesRead;
        final int bufferLength = 8192;
        byte[] buffer = new byte[bufferLength];
        //使用输出流输出输入流的字节
        try {
            OutputStream os = item.getOutputStream();
            while ((bytesRead = inputStream.read(buffer, IntegerConsts.ZERO, bufferLength)) != IntegerConsts.MINUS_ONE) {
                os.write(buffer, IntegerConsts.ZERO, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }


    /**
     * 删除日志
     *
     * @param catalog 日志路径
     * @param day     删除几天前的
     */
    public static void deleteCatalogFile(String catalog, int day) {
        day = Math.abs(day) * IntegerConsts.MINUS_ONE;
        LocalDate localDate = LocalDate.now().plusDays(day);
        long deleteTime = localDate.atStartOfDay(ZoneOffset
                .ofHours(IntegerConsts.EIGHT)).toInstant().toEpochMilli();
        try {
            File file = new File(catalog);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    // 若是文件夹且在设定日期之前
                    if (f.isDirectory()) {
                        deleteCatalogFile(f.getPath(), day);
                    } else {
                        deleteFile(f, deleteTime);
                    }
                }
            } else {
                deleteFile(file, deleteTime);
            }
        } catch (IOException e) {
        }
    }

    /**
     * 获取文件创建时间
     *
     * @param filePath 文件路径
     * @return 返回创建文件时间戳
     */
    public static long fileCreationTime(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        BasicFileAttributeView attributeView = Files
                .getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr = attributeView.readAttributes();
        return attr.creationTime().toMillis();
    }

    /**
     * 删除文件
     *
     * @param file       文件
     * @param deleteTime 删除时间
     */
    private static void deleteFile(File file, long deleteTime) throws IOException {
        long toMillis = fileCreationTime(file.getPath());
        if (toMillis > deleteTime) {
            return;
        }
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 改变图片的大小到宽为size，然后高随着宽等比例变化
     *
     * @param inputStream 上传的图片的输入流
     * @param percent     图片倍率
     * @param format      新图片的格式
     * @throws IOException 异常信息
     */
    public static OutputStream resizeImage(InputStream inputStream, int percent, String format) throws IOException {
        // 改变了图片的大小后，把图片的流输出到目标 OutputStream
        OutputStream os = new ByteArrayOutputStream();
        BufferedImage prevImage = ImageIO.read(inputStream);
        double width = prevImage.getWidth();
        double height = prevImage.getHeight();
        int newWidth = (int) (width * percent);
        int newHeight = (int) (height * percent);
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, IntegerConsts.ZERO, IntegerConsts.ZERO, newWidth, newHeight, null);
        ImageIO.write(image, format, os);
        os.flush();
        inputStream.close();
        os.close();
        return os;
    }
}
