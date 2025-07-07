package com.cdkjframework.util.files;

import com.cdkjframework.entity.file.FileEntity;
import com.cdkjframework.exceptions.GlobalRuntimeException;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.tool.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ProjectName: com.cdkjframework.webcode
 * @Package: com.cdkjframework.core.util.files
 * @ClassName: ZipUtil
 * @Description: 压缩工具
 * @Author: xiaLin
 * @Version: 1.0
 */
@Component
public class ZipUtils {

  /**
   * 日志
   */
  private static LogUtils logUtil = LogUtils.getLogger(ZipUtils.class);

  /**
   * 后缀
   */
  private static final String SUFFIX = ".zip";
  /**
   * 目录
   */
  private static final String CATELOG = "/zipFiles/";

  /**
   * 做压缩
   *
   * @param zipFile 压缩文件
   * @throws IOException IO异常信息
   */
  public static String doZip(FileEntity zipFile) throws IOException {
    // 验证是否有文件
    if (CollectionUtils.isEmpty(zipFile.getFileList())) {
      if (StringUtils.isNullAndSpaceOrEmpty(zipFile.getRootPath())) {
        throw new GlobalRuntimeException("压缩文件列表或压缩根目录不能同时为空");
      }
      List<String> fileList = new ArrayList<>();
      File fileRoot = new File(zipFile.getRootPath());
      File[] files = fileRoot.listFiles();
      for (File key :
          files) {
        if (key.isFile()) {
          fileList.add(key.getAbsolutePath());
        } else {
          File[] listFiles = key.listFiles();
          for (File image :
              listFiles) {
            fileList.add(image.getAbsolutePath());
          }
        }
      }
      zipFile.setFileList(fileList);
    }
    //文件名
    String fileName = zipFile.getFileName();
    if (StringUtils.isNullAndSpaceOrEmpty(fileName)) {
      fileName = GeneratedValueUtils.getUuidNotTransverseLine() + SUFFIX;
    } else if (!fileName.contains(SUFFIX)) {
      fileName += SUFFIX;
    }

    //文件完整路径
    String fullPath = zipFile.getFilePath() + CATELOG;
    logUtil.info(fullPath);
    File file = new File(fullPath);
    if (!file.exists()) {
      file.mkdirs();
    }
    fullPath = file.getPath() + File.separator + fileName;
    logUtil.info(fullPath);

    //读取文件流
    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fullPath));
    ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

    //调用压缩
    doZip(zipFile, zipOutputStream);

    //返回路径
    return CATELOG + fileName;
  }

  /**
   * 文件压缩操作
   *
   * @param zipFile 压缩文件
   * @return 返回结果
   * @throws IOException 异常信息
   */
  public static void doZip(FileEntity zipFile, ZipOutputStream zipOutputStream) throws IOException {
    byte[] buf = new byte[8192];
    zipFile.getFileList()
        .stream()
        .forEach(filePath -> {
          File file = new File(filePath);
          if (file.exists()) {
            String fileName = file.getName();
            String path = null;
            if (StringUtils.isNotNullAndEmpty(zipFile.getRootPath())) {
              path = file.getPath()
                  .replace(zipFile.getRootPath(), StringUtils.Empty)
                  .replace(fileName, StringUtils.Empty);
            }
            //读取文件进入压缩
            ZipEntry zipEntry;
            if (StringUtils.isNullAndSpaceOrEmpty(path)) {
              zipEntry = new ZipEntry(fileName);
            } else {
              path += StringUtils.BACKSLASH + fileName;
              zipEntry = new ZipEntry(path);
            }
            try {
              //添加至输出
              zipOutputStream.putNextEntry(zipEntry);
              //将文件读取为流
              FileInputStream fileInputStream = new FileInputStream(file);
              int len;
              while ((len = fileInputStream.read(buf)) > 0) {
                zipOutputStream.write(buf, 0, len);
              }
              fileInputStream.close();
              zipOutputStream.closeEntry();
            } catch (IOException e) {
              logUtil.error(e.getMessage());
            }
          }
        });
    zipOutputStream.close();
  }
}
