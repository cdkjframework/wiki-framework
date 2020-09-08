package com.cdkjframework.util.files;

import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.exceptions.GlobalException;
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

import java.io.*;
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
            }

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;

            outputStream = new FileOutputStream(file.getPath() + File.separator + fileName, true);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
            String newline = System.lineSeparator();
            //写入换行  
            outputStream.write(newline.getBytes());
            return true;
        } catch (Exception ex) {
            throw new GlobalException(ex.getMessage());
        } finally {
            // 完毕，关闭所有链接
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
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
    public static void beforeDeleteSpecifiedTimeFile(Date date, String catalog) throws GlobalException {
        beforeDeleteSpecifiedTimeFile(date, catalog, "", null);
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
    public static void beforeDeleteSpecifiedTimeFile(Date date, String catalog, String directoryPath, List<String> excludeFiles) throws GlobalException {
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
                if (new Date(file.lastModified()).before(date)) {
                    file.delete();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GlobalException(ex.getMessage());
        }
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return 返回结果
     */
    public static String getFileSuffix(String fileName) {
        String suffix = "";
        if (StringUtils.isNullAndSpaceOrEmpty(fileName)) {
            return suffix;
        }
        //获取后缀
        suffix = fileName.substring(fileName.lastIndexOf("."));
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
        String fileName = "";
        if (StringUtils.isNullAndSpaceOrEmpty(filePath)) {
            return fileName;
        }
        //网络地址
        final String splitValue = "/";
        if (filePath.contains(splitValue)) {
            fileName = filePath.substring(filePath.lastIndexOf(splitValue) + 1);
        } else {
            //目录地址
            fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
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
}
