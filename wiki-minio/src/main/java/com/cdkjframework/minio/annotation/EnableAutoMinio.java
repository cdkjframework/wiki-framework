package com.cdkjframework.minio.annotation;

import com.cdkjframework.minio.config.MinioMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用自动MinIO
 *
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.annotation
 * @ClassName: EnableAutoMinio
 * @Description: 启用自动MinIO
 * @Author: xiaLin
 * @Date: 2024/9/2 11:27
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MinioMarkerConfiguration.class})
public @interface EnableAutoMinio {
}
