package com.cdkjframework.util.files;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.cdkjframework.config.AliCloudOssConfig;
import com.cdkjframework.util.make.GeneratedValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.core.util.files
 * @ClassName: AliCloudOssUtil
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
public class AliCloudOssUtils {

    /**
     * 基础配置信息
     */
    private static AliCloudOssConfig config;

    /**
     * redis 配置信息
     */
    @Autowired
    private AliCloudOssConfig aliCloudOssConfig;

    /**
     * 初始化数据
     */
    @PostConstruct
    public void init() {
        config = aliCloudOssConfig;
    }

    /**
     * 创建空间
     */
    public static void createBucket() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());

        // 创建存储空间。
        ossClient.createBucket(config.getBucketName());

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     */
    public static String publishInputStream(InputStream inputStream, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());

        // 上传文件流。
        ossClient.putObject(config.getBucketName(), fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回访问地址
        return config.getBucketDomain() + fileName;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     */
    public static String publishFile(File file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());

        //获取文件名
        String fileName = file.getName();
        fileName = GeneratedValueUtils.getUuidNotTransverseLine() + FileUtils.getFileSuffix(fileName);

        // 上传文件流。
        ossClient.putObject(config.getBucketName(), fileName, file);

        // 关闭OSSClient。
        ossClient.shutdown();

        //返回访问地址
        return config.getBucketDomain() + fileName;
    }

    /**
     * 删除的文件
     *
     * @param fileName 文件名名称
     */
    public static void deleteFiles(String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());

        // 创建存储空间。
        ossClient.deleteObject(config.getBucketName(), fileName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
