package com.cdkjframework.core.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.entity.file.FileEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.exceptions.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: com.cdkjframework.QRcode
 * @Package: com.cdkjframework.core.controller
 * @ClassName: IController
 * @Description: 接口控制类
 * @Author: xiaLin
 * @Version: 1.0
 */

public interface IController {

  /**
   * 登出登录
   *
   * @param id 主键
   * @return 返回结果
   */
  ResponseBuilder quit(String id);

  /**
   * 获取抽象信息
   *
   * @return 返回用户抽象实体
   */
  UserEntity getCurrentUser();

  /**
   * 获取抽象信息
   *
   * @param clazz 实体
   * @param <T>   泛型
   * @return 返回用户抽象实体
   */
  <T> T getCurrentUser(Class<T> clazz);

  /**
   * 获取抽象信息
   *
   * @param id    主键
   * @param clazz 实体
   * @param <T>   泛型
   * @return 返回用户抽象实体
   */
  <T> T getCurrentUser(String id, Class<T> clazz);

  /**
   * 获取参数
   *
   * @param key 主键值
   * @return 返回结果
   */
  String getRequestParameter(String key);

  /**
   * 获取参数
   *
   * @param keys 主键值
   * @return 返回结果
   */
  Map<String, Object> getRequestParameter(String... keys);

  /**
   * 获取请求头参数
   *
   * @param key 主键值
   * @return 返回结果
   */
  String getRequestHeader(String key);

  /**
   * 获取请求头参数
   *
   * @param keys 主键值
   * @return 返回结果
   */
  Map<String, Object> getRequestHeader(String... keys);

  /**
   * 获取文件流并保存为文件
   *
   * @param file 文件流
   * @return 返回结果
   * @throws IOException     IO异常
   * @throws GlobalException 公众异常
   */
  String saveFile(MultipartFile file) throws IOException, GlobalException;

  /**
   * 获取文件流并保存为文件
   *
   * @param file          文件流
   * @param directoryPath 指定目录
   * @return 返回结果
   * @throws IOException     IO异常
   * @throws GlobalException 公众异常
   */
  String saveFile(MultipartFile file, String directoryPath) throws IOException, GlobalException;

  /**
   * 压缩文件
   *
   * @param response   输入文件流
   * @param fileEntity 文件实体
   * @throws IOException 异常信息
   */
  void zipFile(HttpServletResponse response, FileEntity fileEntity) throws IOException;

  /**
   * 压缩文件
   *
   * @param fileEntity 文件实体
   * @return 返回结果
   * @throws IOException 异常信息
   */
  String zipFile(FileEntity fileEntity) throws IOException;

  /**
   * 导入 excel 数据
   *
   * @param inputStream 数据流
   * @param clazz       要转换的类
   * @param <T>         泛型
   * @return 返回结果
   */
  <T> List<T> importExcelToList(InputStream inputStream, Class<T> clazz);

  /**
   * response 输出内容
   *
   * @param content 内容
   * @throws IOException 异常信息
   */
  void write(String content) throws IOException;

  /**
   * 下载输出
   *
   * @param dataList 数据列表
   * @param clazz    数据类型
   * @param fileName 文件名
   * @param <T>      类型
   * @throws IOException 抛出IO异常
   */
  <T> void downloadOutput(List<T> dataList, Class<T> clazz, String fileName) throws IOException;

  /**
   * 输出流
   *
   * @param inputStream 输出流
   * @param fileName    文件名称
   * @throws IOException 异常信息
   */
  void outputStream(InputStream inputStream, String fileName) throws IOException;

  /**
   * 获取程序版本信息
   *
   * @param request HttpServletRequest
   */
  void version(HttpServletRequest request);

  /**
   * 获取请求流
   *
   * @return 返回流
   * @throws IOException 抛出IO异常
   */
  InputStream getRequestStream() throws IOException;

  /**
   * 获取请求数据
   *
   * @return 返回数据
   */
  StringBuilder getRequestStreamToString();
}
