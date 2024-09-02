package com.cdkjframework.minio.annotation;

import com.cdkjframework.minio.config.MinioMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.minio.annotation
 * @ClassName: EnableAutoMinio
 * @Description: java类作用描述
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
