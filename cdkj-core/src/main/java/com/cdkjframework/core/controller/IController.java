package com.cdkjframework.core.controller;

import com.cdkjframework.builder.ResponseBuilder;
import com.cdkjframework.entity.PageEntity;
import com.cdkjframework.entity.file.FileEntity;
import com.cdkjframework.entity.log.LogRecordEntity;
import com.cdkjframework.entity.user.UserEntity;
import com.cdkjframework.enums.basics.BasicsEnum;
import com.cdkjframework.exceptions.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param clasz 实体
     * @return 返回用户抽象实体
     */
    <T> T getCurrentUser(Class<T> clasz);

    /**
     * 获取抽象信息
     *
     * @param id    主键
     * @param clasz 实体
     * @return 返回用户抽象实体
     */
    <T> T getCurrentUser(String id, Class<T> clasz);

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
     * @return 返回结果
     */
    List importExcelToList(InputStream inputStream, Class clazz);

    /**
     * response 输出内容
     *
     * @param content 内容
     * @throws IOException 异常信息
     */
    void write(String content) throws IOException;

    /**
     * 获取程序版本信息
     *
     * @param request HttpServletRequest
     */
    void version(HttpServletRequest request);

    /**
     * 写入日志
     *
     * @param logRecordEntity 日志记录
     */
    void writeLog(LogRecordEntity logRecordEntity);

    /**
     * 定入日志
     *
     * @param builder      结果
     * @param logId        日志ID
     * @param businessType 业务类型
     * @param modular      模块
     */
    void writeLog(ResponseBuilder builder, String logId, BasicsEnum businessType, BasicsEnum modular);

    /**
     * 定入日志
     *
     * @param pageEntity   结果
     * @param logId        日志ID
     * @param businessType 业务类型
     * @param modular      模块
     */
    void writeLog(PageEntity pageEntity, String logId, BasicsEnum businessType, BasicsEnum modular);
}
