package com.cdkjframework.core.spring.format;

import com.cdkjframework.config.CustomConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.core.spring.format
 * @ClassName: DateFormatConfiguration
 * @Description: 时间格式化
 * @Author: xiaLin
 * @Date: 2023/2/10 9:16
 * @Version: 1.0
 */
@Configuration
@JsonComponent
@RequiredArgsConstructor
public class DateFormatConfiguration {
//    /**
//     * 配置
//     */
//    private final CustomConfig customConfig;
//
//    /**
//     * jackson2对象映射器生成器
//     *
//     * @description date 类型全局时间格式化
//     */
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {
//        return builder -> {
//            TimeZone tz = TimeZone.getTimeZone(customConfig.getTimeZone());
//            DateFormat dateFormat = new SimpleDateFormat(customConfig.getDateFormat());
//            dateFormat.setTimeZone(tz);
//            builder.failOnEmptyBeans(false)
//                    .failOnUnknownProperties(false)
//                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                    .dateFormat(dateFormat);
//        };
//    }
//
//    /**
//     * 反序列化程序
//     *
//     * @description LocalDate 类型全局时间格式化
//     */
//    @Bean
//    public LocalDateTimeSerializer localDateTimeDeserializer() {
//        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(customConfig.getDateFormat()));
//    }
//
//    /**
//     * jackson2对象映射器生成器自定义程序
//     *
//     * @return 返回结果
//     */
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer());
//    }
}
