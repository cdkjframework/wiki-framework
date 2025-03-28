package com.cdkjframework.core.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.config.VersionConfig;
import com.cdkjframework.constant.EncodingConsts;
import com.cdkjframework.constant.HttpHeaderConsts;
import com.cdkjframework.constant.IntegerConsts;
import com.cdkjframework.entity.file.FileEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.util.files.FileUtils;
import com.cdkjframework.util.files.ZipUtils;
import com.cdkjframework.util.files.excel.EasyExcelUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.make.GeneratedValueUtils;
import com.cdkjframework.util.network.http.HttpServletUtils;
import com.cdkjframework.util.tool.StringUtils;
import com.cdkjframework.util.tool.number.ConvertUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.cdkjframework.constant.EncodingConsts.CHARSET_UTF8;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.entity
 * @ClassName: AbstractController
 * @Description: 抽象控制器
 * @Author: xiaLin
 * @Version: 1.0
 */

public abstract class AbstractController implements IController {

  /**
   * 日志
   */
  private final LogUtils logUtil = LogUtils.getLogger(AbstractController.class);

  /**
   * 读取基础数据
   */
  @Autowired
  private VersionConfig versionConfig;

  /**
   * 登出登录
   *
   * @param id 主键
   * @return 返回结果
   */
  @Override
  public abstract ResponseBuilder quit(String id);

  /**
   * 获取抽象信息
   *
   * @return 返回用户抽象实体
   */
  @Override
  public abstract UserEntity getCurrentUser();

  /**
   * 获取抽象信息
   *
   * @param clazz 实体
   * @return 返回用户抽象实体
   */
  @Override
  public abstract <T> T getCurrentUser(Class<T> clazz);

  /**
   * 获取抽象信息
   *
   * @param id    主键
   * @param clazz 实体
   * @return 返回用户抽象实体
   */
  @Override
  public abstract <T> T getCurrentUser(String id, Class<T> clazz);

  /**
   * 获取程序版本信息
   *
   * @param request HttpServletRequest
   */
  @Override
  public void version(HttpServletRequest request) {
    String builder = versionConfig.getSpringApplicationName() +
        StringUtils.COLON +
        versionConfig.getServerPort();

    //输出信息
    write(builder);
  }

  /**
   * 获取参数
   *
   * @param keys 主键值
   * @return 返回结果
   */
  @Override
  public Map<String, Object> getRequestParameter(String... keys) {
    Map<String, Object> mapList = new HashMap<String, Object>(keys.length);
    for (String key :
        keys) {
      String value = HttpServletUtils.getRequest().getParameter(key);
      mapList.put(key, value);
    }

    //返回结果
    return mapList;
  }

  /**
   * 获取参数
   *
   * @param key 主键值
   * @return 返回结果
   */
  @Override
  public String getRequestParameter(String key) {
    return HttpServletUtils.getRequest().getParameter(key);
  }

  /**
   * 获取请求头参数
   *
   * @param key 主键值
   * @return 返回结果
   */
  @Override
  public String getRequestHeader(String key) {
    return HttpServletUtils.getRequest().getHeader(key);
  }

  /**
   * 获取请求头参数
   *
   * @param keys 主键值
   * @return 返回结果
   */
  @Override
  public Map<String, Object> getRequestHeader(String... keys) {
    Map<String, Object> mapList = new HashMap<String, Object>(keys.length);
    for (String key :
        keys) {
      String value = HttpServletUtils.getRequest().getHeader(key);
      mapList.put(key, value);
    }

    //返回结果
    return mapList;
  }

  /**
   * 获取文件流
   *
   * @param file 上传文件
   * @return 返回结果
   */
  @Override
  public String saveFile(MultipartFile file) throws IOException, GlobalException {
    if (file == null && file.isEmpty()) {
      throw new GlobalException("没有上传的文件");
    }

    //定义目录
    final String directoryPath = "";

    //返回结果
    return saveFile(file, directoryPath);
  }

  /**
   * 压缩文件
   *
   * @param response   输入文件流
   * @param fileEntity 文件实体
   */
  @Override
  public void zipFile(HttpServletResponse response, FileEntity fileEntity) throws IOException {
    // 清空response
    response.reset();

    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + ";charset=UTF-8");

    //生成文件
    String fileName = zipFile(fileEntity);
    response.setHeader(HttpHeaderConsts.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

    File file = new File(fileEntity.getFilePath() + fileName);
    try {
      // 以流的形式下载文件。
      InputStream inputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
      response.setHeader(HttpHeaderConsts.CONTENT_LENGTH, String.valueOf(inputStream.available()));
      byte[] buffer = new byte[inputStream.available()];
      inputStream.read(buffer);
      inputStream.close();

      OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

      toClient.write(buffer);
      toClient.flush();
      toClient.close();
    } catch (IOException ex) {
      logUtil.error(ex.getMessage());
    }
  }

  /**
   * 压缩文件
   *
   * @param fileEntity 文件实体
   */
  @Override
  public String zipFile(FileEntity fileEntity) throws IOException {
    //生成文件 并返回文件路径
    return ZipUtils.doZip(fileEntity);
  }

  /**
   * 获取文件流并保存为文件
   *
   * @param file          文件流
   * @param directoryPath 指定目录
   * @return 返回结果
   * @throws IOException     IO异常
   * @throws GlobalException 公众异常
   */
  @Override
  public String saveFile(MultipartFile file, String directoryPath) throws IOException, GlobalException {
    if (file == null || file.isEmpty()) {
      throw new GlobalException("没有上传的文件");
    }

    //文件流
    InputStream inputStream = file.getInputStream();

    //文件名称
    String fileName = file.getOriginalFilename();
    //获取后缀
    assert fileName != null;
    String suffix = fileName.substring(fileName.lastIndexOf('.') - 1);
    //重新定义文件名称
    fileName = GeneratedValueUtils.getUuidNotTransverseLine() + suffix;

    //自定义路径
    final String catalog = "/uploadFiles/";

    //保存文件
    if (StringUtils.isNotNullAndEmpty(directoryPath)) {
      FileUtils.saveFile(inputStream, directoryPath, catalog, fileName);
    } else {
      FileUtils.saveFile(inputStream, catalog, fileName);
    }

    //返回文件浏览路径
    return catalog + fileName;
  }

  /**
   * 导入 excel 数据
   *
   * @param inputStream 数据流
   * @param clazz       要转换的类
   * @return 返回结果
   */
  @Override
  public <T> List<T> importExcelToList(InputStream inputStream, Class<T> clazz) {
    List<T> list = new ArrayList<>();

    try {
      return EasyExcelUtils.readExcelToList(inputStream, StringUtils.Empty, clazz);
    } catch (Exception e) {
      logUtil.error(e.getCause(), e.getMessage());
    }

    //返回结果
    return list;
  }

  /**
   * response 输出内容
   *
   * @param content 内容
   */
  @Override
  public void write(String content) {
    try {
      HttpServletResponse response = HttpServletUtils.getResponse();
      assert response != null;
      response.setCharacterEncoding(EncodingConsts.UTF8);
      response.setContentType(TEXT_HTML_VALUE);
      PrintWriter writer = response.getWriter();
      writer.write(content);
      writer.close();
    } catch (IOException ex) {
      logUtil.error(ex.getCause(), ex.getMessage());
    }
  }

  /**
   * 下载输出
   *
   * @param dataList 数据列表
   * @param clazz    数据类型
   * @param fileName 文件名
   */
  @Override
  public <T> void downloadOutput(List<T> dataList, Class<T> clazz, String fileName) throws IOException {
    InputStream inputStream = EasyExcelUtils.listExportInputStream(dataList, clazz);
    outputStream(inputStream, fileName);
  }

  /**
   * 输出流
   *
   * @param inputStream 输出流
   * @param fileName    文件名称
   */
  @Override
  public void outputStream(InputStream inputStream, String fileName) throws IOException {
    if (StringUtils.isNotNullAndEmpty(fileName)) {
      fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
    }
    HttpServletResponse response = HttpServletUtils.getResponse();
    assert response != null;
    response.reset();
    response.setCharacterEncoding(EncodingConsts.UTF8);
    response.addHeader(HttpHeaderConsts.CONTENT_LENGTH, ConvertUtils.convertString(inputStream.available()));
    response.setContentType("application/vnd.ms-excel;" + CHARSET_UTF8);
    response.addHeader(HttpHeaderConsts.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
    response.addHeader("filename", fileName);
    OutputStream outputStream = response.getOutputStream();
    byte[] buf = new byte[4096];
    int readLength;
    while (((readLength = inputStream.read(buf)) != IntegerConsts.MINUS_ONE)) {
      outputStream.write(buf, IntegerConsts.ZERO, readLength);
    }
    outputStream.flush();
    inputStream.close();
  }

  /**
   * 获取请求流
   *
   * @return 返回流
   */
  @Override
  public InputStream getRequestStream() throws IOException {
    return Objects.requireNonNull(HttpServletUtils.getRequest()).getInputStream();
  }

  /**
   * 获取请求数据
   *
   * @return 返回数据
   */
  @Override
  public StringBuilder getRequestStreamToString() {
    StringBuilder builder = new StringBuilder();
    try {
      //获取流数据
      InputStream stream = getRequestStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

      //读取数据
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }

      //返回结果
      return builder;
    } catch (IOException e) {
      logUtil.error(e.getCause(), e.getMessage());
    }
    // 返回结果
    return builder;
  }
}
